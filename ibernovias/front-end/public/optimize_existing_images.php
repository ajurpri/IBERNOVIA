<?php
/**
 * Ibernovia - Script de optimización de imágenes existentes.
 * Este script escanea la carpeta images/productos/ y comprime todas las imágenes grandes.
 */
header("Content-Type: text/plain; charset=UTF-8");

// Clave de seguridad simple para evitar ejecuciones maliciosas
$key = isset($_GET['key']) ? $_GET['key'] : '';
if ($key !== 'IBERNOVIA_OPTIMIZE_2026') {
    die("Acceso denegado. Especifica la clave correcta: ?key=IBERNOVIA_OPTIMIZE_2026");
}

$dir = __DIR__ . "/images/productos/";
if (!file_exists($dir)) {
    die("La carpeta de productos no existe: $dir");
}

$files = scandir($dir);
$count = 0;
$optimized = 0;

echo "Escaneando carpeta: $dir\n\n";

foreach ($files as $file) {
    if ($file === '.' || $file === '..') continue;
    
    $path = $dir . $file;
    if (!is_file($path)) continue;
    
    $ext = strtolower(pathinfo($path, PATHINFO_EXTENSION));
    if (!in_array($ext, ['jpg', 'jpeg', 'png'])) continue;
    
    $size = filesize($path);
    $count++;
    
    // Si la imagen pesa más de 300 KB, la optimizamos
    if ($size > 300 * 1024) {
        echo "Optimizando: $file (" . round($size / 1024, 2) . " KB)... ";
        
        // Crear imagen desde archivo
        if ($ext === 'jpg' || $ext === 'jpeg') {
            $src = @imagecreatefromjpeg($path);
        } else if ($ext === 'png') {
            $src = @imagecreatefrompng($path);
        } else {
            continue;
        }
        
        if (!$src) {
            echo "ERROR al leer la imagen (puede estar corrupta).\n";
            continue;
        }
        
        $width = imagesx($src);
        $height = imagesy($src);
        $max_dim = 1200;
        
        // Redimensionar si es muy grande
        if ($width > $max_dim || $height > $max_dim) {
            if ($width > $height) {
                $new_width = $max_dim;
                $new_height = round(($height * $max_dim) / $width);
            } else {
                $new_height = $max_dim;
                $new_width = round(($width * $max_dim) / $height);
            }
            
            $dst = imagecreatetruecolor($new_width, $new_height);
            
            // Preservar transparencia en caso de PNG
            if ($ext === 'png') {
                imagealphablending($dst, false);
                imagesavealpha($dst, true);
            }
            
            imagecopyresampled($dst, $src, 0, 0, 0, 0, $new_width, $new_height, $width, $height);
            imagedestroy($src);
            $src = $dst;
        }
        
        // Guardar con compresión alta (reemplazando el archivo original)
        $success = false;
        if ($ext === 'jpg' || $ext === 'jpeg') {
            $success = imagejpeg($src, $path, 80); // Calidad 80
        } else if ($ext === 'png') {
            // Comprimir PNG
            $success = imagepng($src, $path, 6); // Nivel de compresión 6
        }
        
        imagedestroy($src);
        
        if ($success) {
            clearstatcache();
            $new_size = filesize($path);
            echo "OK! Nuevo tamaño: " . round($new_size / 1024, 2) . " KB (Ahorro del " . round((1 - ($new_size / $size)) * 100, 2) . "%)\n";
            $optimized++;
        } else {
            echo "ERROR al guardar.\n";
        }
    }
}

echo "\nProceso finalizado.\n";
echo "Total imágenes analizadas: $count\n";
echo "Total imágenes optimizadas: $optimized\n";
?>
