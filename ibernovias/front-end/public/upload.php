<?php
/**
 * Ibernovia - Script seguro de subida de imágenes al VPS.
 * 
 * Este script recibe imágenes codificadas en base64 y las guarda de forma permanente
 * en la carpeta de imágenes del VPS. Requiere un token secreto de autenticación.
 */

// Permitir CORS para desarrollo local y peticiones cruzadas (ej: desde localhost)
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: Content-Type, Authorization, X-Security-Token");
header("Access-Control-Allow-Methods: POST, OPTIONS");
header("Content-Type: application/json; charset=UTF-8");

// Responder a peticiones preflight OPTIONS de CORS e interrumpir ejecución
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    exit(0);
}

// Token de seguridad secreto para evitar subidas no autorizadas
define('SECURITY_TOKEN', 'IBERNOVIA_SECURE_UPLOAD_TOKEN_2026_AJURPRI');

// Obtener cabeceras HTTP de forma compatible
$headers = array_change_key_case(getallheaders(), CASE_LOWER);
$received_token = isset($headers['x-security-token']) ? $headers['x-security-token'] : '';

// Validar token de seguridad
if ($received_token !== SECURITY_TOKEN) {
    http_response_code(403);
    echo json_encode([
        "success" => false,
        "error" => "Acceso no autorizado. Token de seguridad inválido."
    ]);
    exit();
}

// Obtener datos de la petición POST
$input = file_get_contents('php://input');
$data = json_decode($input, true);

if (!isset($data['image']) || !isset($data['filename'])) {
    http_response_code(400);
    echo json_encode([
        "success" => false,
        "error" => "Datos incompletos. Se requiere 'image' (base64) y 'filename'."
    ]);
    exit();
}

$base64_image = $data['image']; // ej: "data:image/png;base64,iVBORw0KG..."
$filename = $data['filename'];

// Validar que sea un formato de imagen base64
if (strpos($base64_image, 'data:image/') !== 0) {
    http_response_code(400);
    echo json_encode([
        "success" => false,
        "error" => "Formato de imagen inválido. Debe ser una cadena base64 de tipo imagen."
    ]);
    exit();
}

// Limpiar el nombre del archivo para evitar vulnerabilidades de Path Traversal
$filename = basename($filename);
$extension = pathinfo($filename, PATHINFO_EXTENSION);
if (empty($extension)) {
    $extension = 'jpg';
}

// Generar un nombre único para evitar colisiones y sobrescritura de archivos
$new_filename = "producto_" . round(microtime(true) * 1000) . "_" . uniqid() . "." . strtolower($extension);

// Decodificar los datos base64
$parts = explode(',', $base64_image);
if (count($parts) !== 2) {
    http_response_code(400);
    echo json_encode([
        "success" => false,
        "error" => "La cadena de datos base64 está malformada."
    ]);
    exit();
}
$decoded_image = base64_decode($parts[1]);

if ($decoded_image === false) {
    http_response_code(400);
    echo json_encode([
        "success" => false,
        "error" => "Error al decodificar la imagen base64."
    ]);
    exit();
}

// Carpeta de destino (en la misma estructura del frontend)
$target_dir = __DIR__ . "/images/productos/";

// Crear el directorio de forma recursiva si no existe
if (!file_exists($target_dir)) {
    mkdir($target_dir, 0755, true);
}

$target_file = $target_dir . $new_filename;

// Guardar la imagen en el disco del VPS
if (file_put_contents($target_file, $decoded_image) !== false) {
    // Retornar la ruta relativa que se guardará en la base de datos
    $public_url = "/images/productos/" . $new_filename;
    
    echo json_encode([
        "success" => true,
        "url" => $public_url
    ]);
} else {
    http_response_code(500);
    echo json_encode([
        "success" => false,
        "error" => "No se pudo guardar el archivo en el servidor. Revisa los permisos de escritura de la carpeta."
    ]);
}
?>
