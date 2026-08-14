<?php
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Headers: Content-Type, X-Security-Token');
header('Access-Control-Allow-Methods: POST, OPTIONS');
header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    exit(0);
}

// 1. Verificar el token de seguridad para evitar que terceros usen el script
$token = $_SERVER['HTTP_X_SECURITY_TOKEN'] ?? '';
if ($token !== 'IBERNOVIA_SECURE_UPLOAD_TOKEN_2026_AJURPRI') {
    http_response_code(403);
    echo json_encode(['error' => 'No autorizado']);
    exit;
}

$input = json_decode(file_get_contents('php://input'), true);
$nombre = $input['nombre'] ?? '';
$email = $input['email'] ?? '';
$asunto = $input['asunto'] ?? '';
$mensaje = $input['mensaje'] ?? '';

if (empty($nombre) || empty($email) || empty($asunto) || empty($mensaje)) {
    http_response_code(400);
    echo json_encode(['error' => 'Datos incompletos']);
    exit;
}

// Correo para la empresa
$to = 'info@ibernovia.es';
$subject = "Contacto: $asunto";
$body = "Nuevo mensaje desde el formulario de contacto\n\n";
$body .= "Nombre: $nombre\n";
$body .= "Email: $email\n";
$body .= "Asunto: $asunto\n\n";
$body .= "Mensaje:\n$mensaje\n";

$headers = "From: no-reply@ibernovia.es\r\n";
$headers .= "Reply-To: $email\r\n";
$headers .= "Content-Type: text/plain; charset=UTF-8\r\n";

$mailSent = mail($to, $subject, $body, $headers);

// Correo de confirmación para el cliente
$customerSubject = "Hemos recibido tu consulta en IBERNOVIA";
$customerBody = "Hola $nombre,\n\nHemos recibido correctamente tu consulta en IBERNOVIA.\n";
$customerBody .= "Nuestro equipo revisará tu mensaje y te responderá lo antes posible.\n\n";
$customerBody .= "Resumen de tu consulta:\n";
$customerBody .= "Asunto: $asunto\n";
$customerBody .= "Mensaje: $mensaje\n\n";
$customerBody .= "Gracias por contactar con IBERNOVIA.";

$customerHeaders = "From: info@ibernovia.es\r\n";
$customerHeaders .= "Reply-To: info@ibernovia.es\r\n";
$customerHeaders .= "Content-Type: text/plain; charset=UTF-8\r\n";

$customerMailSent = mail($email, $customerSubject, $customerBody, $customerHeaders);

echo json_encode([
    'success' => $mailSent,
    'customerSuccess' => $customerMailSent
]);
