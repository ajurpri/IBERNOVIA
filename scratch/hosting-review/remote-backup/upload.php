<?php
/**
 * Ibernovia - subida permanente y no destructiva de imágenes.
 *
 * Acepta multipart/form-data (preferido, más rápido) y conserva soporte
 * para el JSON Base64 usado por versiones anteriores del panel.
 */

header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Headers: Content-Type, Authorization, X-Security-Token');
header('Access-Control-Allow-Methods: POST, OPTIONS');
header('Content-Type: application/json; charset=UTF-8');
header('Cache-Control: no-store');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(204);
    exit();
}

const FALLBACK_SECURITY_TOKEN = 'IBERNOVIA_SECURE_UPLOAD_TOKEN_2026_AJURPRI';
const MAX_IMAGE_BYTES = 8 * 1024 * 1024;

function respond(int $status, array $payload): void
{
    http_response_code($status);
    echo json_encode($payload, JSON_UNESCAPED_UNICODE | JSON_UNESCAPED_SLASHES);
    exit();
}

function request_headers_lowercase(): array
{
    $headers = function_exists('getallheaders') ? getallheaders() : [];
    return array_change_key_case(is_array($headers) ? $headers : [], CASE_LOWER);
}

function detect_image_extension(string $path): ?string
{
    $finfo = new finfo(FILEINFO_MIME_TYPE);
    $mime = $finfo->file($path);
    $allowed = [
        'image/jpeg' => 'jpg',
        'image/png' => 'png',
        'image/webp' => 'webp',
        'image/gif' => 'gif',
    ];
    return $allowed[$mime] ?? null;
}

function unique_image_name(string $extension): string
{
    return 'producto_' . (int) round(microtime(true) * 1000) . '_' . bin2hex(random_bytes(8)) . '.' . $extension;
}

$headers = request_headers_lowercase();
$receivedToken = $headers['x-security-token'] ?? '';
$configuredToken = getenv('IBERNOVIA_UPLOAD_TOKEN') ?: FALLBACK_SECURITY_TOKEN;

if (!hash_equals($configuredToken, $receivedToken)) {
    respond(403, ['success' => false, 'error' => 'Acceso no autorizado.']);
}

$targetDir = __DIR__ . '/images/productos/';
if (!is_dir($targetDir) && !mkdir($targetDir, 0755, true) && !is_dir($targetDir)) {
    respond(500, ['success' => false, 'error' => 'No se pudo preparar la carpeta de imágenes.']);
}

$sourcePath = null;
$temporaryPath = null;
$isUploadedFile = false;

if (isset($_FILES['image'])) {
    $upload = $_FILES['image'];
    if (($upload['error'] ?? UPLOAD_ERR_NO_FILE) !== UPLOAD_ERR_OK) {
        respond(400, ['success' => false, 'error' => 'La subida del archivo no se completó.']);
    }
    if (($upload['size'] ?? 0) <= 0 || $upload['size'] > MAX_IMAGE_BYTES) {
        respond(413, ['success' => false, 'error' => 'La imagen supera el límite permitido de 8 MB.']);
    }
    $sourcePath = $upload['tmp_name'];
    $isUploadedFile = true;
} else {
    $data = json_decode(file_get_contents('php://input'), true);
    $base64Image = is_array($data) ? ($data['image'] ?? '') : '';
    if (!is_string($base64Image) || !preg_match('#^data:image/[^;]+;base64,#', $base64Image)) {
        respond(400, ['success' => false, 'error' => 'No se recibió una imagen válida.']);
    }

    $encoded = substr($base64Image, strpos($base64Image, ',') + 1);
    $decoded = base64_decode($encoded, true);
    if ($decoded === false || strlen($decoded) === 0) {
        respond(400, ['success' => false, 'error' => 'No se pudo decodificar la imagen.']);
    }
    if (strlen($decoded) > MAX_IMAGE_BYTES) {
        respond(413, ['success' => false, 'error' => 'La imagen supera el límite permitido de 8 MB.']);
    }

    $temporaryPath = tempnam(sys_get_temp_dir(), 'ibernovia_upload_');
    if ($temporaryPath === false || file_put_contents($temporaryPath, $decoded, LOCK_EX) === false) {
        respond(500, ['success' => false, 'error' => 'No se pudo preparar la imagen para guardarla.']);
    }
    $sourcePath = $temporaryPath;
}

$extension = detect_image_extension($sourcePath);
if ($extension === null) {
    if ($temporaryPath !== null) @unlink($temporaryPath);
    respond(415, ['success' => false, 'error' => 'El archivo recibido no es una imagen admitida.']);
}

$newFilename = unique_image_name($extension);
$targetFile = $targetDir . $newFilename;
$stored = $isUploadedFile
    ? move_uploaded_file($sourcePath, $targetFile)
    : rename($sourcePath, $targetFile);

if (!$stored) {
    if ($temporaryPath !== null && is_file($temporaryPath)) @unlink($temporaryPath);
    respond(500, ['success' => false, 'error' => 'No se pudo guardar la imagen en el servidor.']);
}

@chmod($targetFile, 0644);
respond(201, [
    'success' => true,
    'url' => '/images/productos/' . $newFilename,
]);
