# Revisión de Ibernovia — versión local, sin publicar

## Entrega a GitHub autorizada por el propietario

El propietario ha solicitado guardar y subir esta revisión a GitHub. Las menciones anteriores a «sin commit» documentan etapas previas y quedan sustituidas por esta autorización. La rama de entrega es `mejora/identidad-rendimiento-20261030`; el servidor no se modifica. El crédito enlaza alvaroo.dev y su Instagram verificado en esa web.

Se genera `dist` de producción y un ZIP de archivos estáticos para subir al servidor. El ZIP excluye los scripts PHP existentes para conservar los servicios instalados. Se elimina la directiva incorrecta `ExpiresEngine` del `.htaccess`, conservando `ExpiresActive`, las reglas SPA y la configuración de caché. Los datos y scripts locales de revisión no forman parte de la entrega a GitHub.

## Revisión integral de páginas — estado más reciente

Esta sección sustituye las descripciones anteriores cuando discrepan. Cambios sin nuevo commit ni publicación:

- Retirados del montaje global «Volver arriba» y la franja independiente de WhatsApp. El contacto permanece en pie, contacto y fichas.
- Inicio: diez referencias concretas en la selección, con las cinco familias representadas. Arras utiliza la foto original 1390-COJ de fondo claro. Catálogo completo de 683 artículos conservado.
- Catálogo: cabecera y filtros compactados; búsqueda por foto con etiqueta comprensible y control de archivo accesible por teclado. Filtros móviles existentes conservados.
- Ficha: galería 3:2 sin hueco vertical artificial, referencia junto al nombre, categorías como texto/enlace, menos avisos comerciales y ninguna decoración sobre las fotos. Acceso, alta y consulta contextual conservados.
- Acceso/registro: campos delimitados y legibles, botones sencillos, retirada de eslóganes SSL y aclaración de que crear cuenta no activa tarifas profesionales. Autenticación y permisos intactos.
- Alta: introducción breve seguida del formulario, sin bloque de pasos ni hero comercial. Plazo habitual de revisión conservado.
- Contacto: datos y formulario accesibles antes, teléfono pulsable, tildes corregidas, campos consistentes. El mensaje de éxito no promete un correo que el cliente no puede verificar. No se han enviado formularios reales.
- Navegación: panel lateral móvil sin desenfoque ni tarjetas anidadas. Icono de usuario anónimo oculto en escritorio donde ya existe acceso profesional. Eventos sin lenguaje de acreditación no sustentado, con enlace a contacto.
- Pie y legales: enlaces legales agrupados, ancho de lectura reducido, preferencia de cookies reabrible desde el pie. Se conserva el contenido jurídico; no se ha hecho una revisión jurídica.
- Cuenta y administración: presentación más sobria; cuenta muestra su estado profesional con las guardas existentes. Revisión de código, sin acceso a cuentas reales ni validación visual de datos privados.
- Carrito: lenguaje de solicitud de presupuesto, imágenes sin recorte, adaptación móvil, retirada de condiciones fijas no verificadas de envío/devolución.
- Checkout y confirmación: se mantienen las URLs y las restricciones profesionales, pero ya no simulan una pasarela, un número de pedido, un pago o un correo enviado. Muestran una explicación y acceso al carrito, donde se conserva la solicitud de presupuesto existente.

Validación: compilación Vite correcta, tres pruebas de presentación correctas y renderizado de servidor de ambas rutas heredadas comprobado con `check-quote-routes.mjs`. Revisados visualmente acceso, alta, contacto, ficha, pie y menú; ficha, menú y acceso comprobados en marco móvil de 390 px. Reapertura y cierre del aviso de cookies comprobados. No equivale a probar login, solicitudes o gestión privada con credenciales; quedan pendientes en un entorno autorizado.

## Revisión completa del inicio — ajuste más reciente

- Portada más contenida: se retiran el antetítulo repetido y la línea ornamental. Menor separación de letras, descripción breve y dos acciones claras. Se mantiene la fotografía real del velo.
- Colecciones: las cinco fotografías tienen proporción 1200:797. Fiesta utiliza otra foto real del catálogo, 3287-MA, sin deformación ni retoque. Nombres y bases alineados; desplazamiento horizontal en móvil.
- Selección: alterna hasta tres artículos activos por cada familia, en vez de los primeros quince de una sola zona del catálogo. El ciclo pasa de 45 a 150 segundos. Pausa con ratón/foco y movimiento reducido conservados, sin control visible.
- Títulos y márgenes de secciones unificados. Enlace al catálogo al lado de la selección.
- Promociones: se eliminan textos genéricos de exclusividad, tarjetas redondeadas, sombras, degradados y etiquetas sobre las fotos. Datos, códigos y guarda de precios profesionales conservados. No había promociones públicas disponibles para comprobar esa sección visualmente.
- Pie de página: fondo liso, menos mayúsculas y espaciado artificial, etiquetas con mayor contraste, distribución en tres columnas desde tableta y menos espacio final en móvil.
- WhatsApp y volver arriba pasan al flujo de la página: ya no cubren fotografías, referencias ni títulos.
- Comprobación visual de portada y colecciones en escritorio y de portada, colecciones y selección en marco de 390 px. Compilación Vite y tres pruebas de presentación correctas; diff sin errores de espacio.

Valoración: la composición es más uniforme y la navegación más directa. La principal limitación visual sigue siendo la fotografía de origen: fondos y encuadres diferentes, algunos con maniquí y otros con el producto aislado. Una sesión fotográfica con un criterio común mejoraría más el resultado que añadir decoración. No se han inventado imágenes, productos, credenciales de marca ni afirmaciones comerciales. Sin nuevo commit ni publicación.

Los tamaños de compilación de secciones anteriores son históricos; esta última compilación tiene aproximadamente 41,61 kB de JavaScript principal, 56,65 kB de CSS global, 7,29 kB de JavaScript de inicio y 6,41 kB de CSS de inicio, sin comprimir.

**Revisión posterior al rechazo visual del propietario:** los cambios actuales quedan SIN COMMIT. Se ha recuperado la portada centrada sobre la fotografía, restaurado el movimiento de 15 productos (pausable y con movimiento reducido), y simplificado catálogo, filtros, tarjetas y tipografía de navegación. El commit local `1bf18c7` corresponde a la primera propuesta rechazada; no identifica la revisión actual. Los tama?os de compilaci?n de abajo se han actualizado para esta revisi?n. No se ha publicado nada.

Rama: `mejora/identidad-rendimiento-20261030`. Estado original: commit `a45a179`, conservado en `backup/antes-mejoras-20261030`. El árbol estaba limpio al iniciar. No se han modificado productos, precios, cuentas, permisos, autenticación, base de datos ni configuración de producción.

## Vista previa

- Versión propuesta: http://127.0.0.1:4174
- Versión inicial: http://127.0.0.1:4173
- Ficha de ejemplo: http://127.0.0.1:4174/producto/639
- Arranque de la propuesta desde la raíz: `python scratch/ibernovia-review/preview.py`.
- Arranque de la inicial: el mismo comando con `--before`.

La vista previa usa una copia de los 683 productos devueltos por la API pública, sin tarifas. Las escrituras están bloqueadas (503) y la política de conexión impide enviar formularios a servicios externos. Las imágenes alojadas en ibernovia.es y las fuentes pueden descargarse al visualizarlas. Cabecera `X-Robots-Tag: noindex, nofollow`; caché local `no-store`. No representa un entorno con cuenta profesional validada. No se ha publicado ni enviado ningún mensaje o solicitud real.

## ?ltimo ajuste visual

- Eliminados marcos, bordes, fondos decorativos y sombras de fotograf?as de colecciones, selecci?n, cat?logo y ficha. Se conservan los fondos que forman parte de las fotos originales, sin retoque ni im?genes generadas.
- Selecci?n con fotograf?as mayores y carga al aproximarse a la secci?n, evitando im?genes ocultas por la combinaci?n de desplazamiento y carga diferida. Corregido el tipo MIME WebP del servidor de revisi?n.
- Tipograf?a m?s contenida, referencias sin duplicar, favoritos debajo de la imagen y etiquetas de oferta fuera de la fotograf?a.
- El enlace de Comuni?n reconoce el nombre con y sin tilde. Comprobado visualmente que devuelve 188 productos.
- Verificadas en el navegador integrado las cinco fotos de colecciones y el cat?logo sin marcos; revisados inicio, cat?logo y selecci?n en un marco m?vil de 390 px. Esto no sustituye una prueba en un tel?fono f?sico.

## Cambios

- Chatbot retirado del montaje y de las importaciones globales. Sus archivos, servicios y datos se conservan. No hay referencias a `FloatingChat` ni `/api/chat` en el resultado compilado.
- Portada estática con la fotografía real del velo existente, sin capa blanca ni carrusel. Ibernovia conserva Fraunces, Manrope y su paleta clara. Dos acciones: catálogo y acceso profesional. Variantes móviles WebP de 640 y 960 px obtenidas por reducción de tamaño, sin IA ni retoque del producto.
- Selección de inicio con 15 productos y movimiento recuperado por indicación posterior del propietario. Botón de pausa, pausa con foco/ratón y alternativa sin animación para movimiento reducido. Se mantienen las familias.
- Tarjetas y ficha con `object-contain`, espacio alrededor de la pieza, referencias legibles y nombres descriptivos solo cuando una descripción breve identifica el producto. Los datos originales no se sobrescriben.
- Referencia comercial tomada del campo original `nombre`, que contiene referencias como `1239-AB`; el ID interno ya no aparece como referencia. La URL `/producto/639` no cambia.
- Descripciones en escritura normal conservando saltos de línea. Las medidas y características siguen en la descripción; no se deducen materiales o colores ni se crean campos inexistentes.
- Fotografía ampliada mediante enlace a la imagen original. WhatsApp de ficha con nombre, referencia y URL pública, sin precios ni datos del cliente. Relacionados de la misma categoría primero dentro de la familia.
- Favoritos fuera del enlace de producto, con estado accesible y tamaño táctil mayor. Se conservan al navegar mediante almacenamiento local de identificadores, sin objetos de producto ni tarifas.
- Acceso profesional lleva a `/auth`; solicitar alta mantiene `/acceso-empresarial`. Se conserva el proceso de validación y el plazo de 24/48 h laborables.
- Correcciones de textos, tildes, etiquetas de formularios, foco visible y contraste. El botón de menú móvil se oculta correctamente en escritorio.
- WhatsApp discreto, sin animaciones, dentro del flujo en móvil para no cubrir productos o controles. En escritorio se oculta mientras aparece el aviso de cookies; el contacto del pie sigue disponible.
- Instagram y Facebook ocultos: solo había enlaces a las portadas generales, sin perfiles oficiales verificables en el proyecto. El pie describe la actividad real.
- Eventos y buscador cargan sus datos cuando se abren. Se mantiene la división de código por rutas y la carga del catálogo en grupos de 24. No se añaden dependencias.
- Metadatos sin afirmaciones no contrastadas de exclusividad o alta costura. Se conserva la estructura de rutas.

## Mapa funcional revisado

| Función | Implementación y comportamiento |
|---|---|
| Catálogo | `TiendaView.vue`, GET `/api/productos`, caché existente por ámbito público/profesional |
| Buscador | Cabecera por nombre/referencia; catálogo con normalización y puntuación existentes |
| Filtros | Familia, categoría, ofertas y orden; sincronización con query string conservada |
| Carga progresiva | 24 tarjetas inicialmente y 24 adicionales por acción; imágenes fuera de pantalla diferidas |
| Fichas | GET `/api/productos/:id`; relacionados por familia/categoría; URLs conservadas |
| Favoritos | Antes estado temporal por tarjeta; ahora IDs persistentes locales |
| PDF | Ventana de impresión con productos filtrados; el usuario guarda como PDF desde el navegador. No era un PDF descargado del servidor |
| Acceso | `/auth`, almacén de autenticación y guardas existentes sin cambios |
| Alta/contacto | Formularios existentes; POST `/api/contacto`. Contacto también contiene envío PHP posterior; nunca se ejecuta contra producción durante la prueba |

## Comprobaciones realizadas

- Compilación Vite satisfactoria. Aviso no bloqueante: base de Browserslist antigua; no se han actualizado dependencias.
- Tres pruebas Node correctas: conservación de referencia, ausencia de nombres inventados a partir de medidas y WhatsApp sin datos privados.
- `git diff --check` sin errores.
- Inicio, catálogo, ficha, acceso, alta y contacto devuelven HTTP 200 en la vista previa; un POST vacío local a contacto se rechaza con 503.
- Revisadas 746 rutas de imágenes principales/secundarias: 253 archivos locales y 493 respuestas remotas HEAD de tipo imagen; cero fallos. Esto comprueba disponibilidad, no sustituye la revisión visual de cada fotografía.
- Respuestas reales públicas, sin credenciales: listado (683), ficha 639 (1), familia Novia (118), categoría Abanicos (47). Ninguna contiene valores en `precio`, `precioOferta` o `precioOriginal`; todas llevan `no-cache, no-store, max-age=0, must-revalidate`.
- `/api/promociones` devuelve 403 sin credenciales. No se ha cambiado su permiso. La presentación de importes de promociones queda además sujeta a la guarda profesional existente.
- `robots.txt` permite indexación; sitemap XML válido con seis páginas estáticas. No incluye fichas. La aplicación es una SPA: HTML inicial sin catálogo renderizado. Prerrenderizado/SSR y sitemap dinámico de productos quedan como fase separada para evitar una migración de arquitectura.

## Rendimiento y límites de la medición

Comparación de compilaciones de producción con Vite 7.3.1, en el mismo equipo Windows, mismos paquetes y configuración. Tamaños sin comprimir (kB decimales):

| Recurso | Inicial | Propuesta aproximada |
|---|---:|---:|
| JavaScript principal | 51,27 | 41,65 |
| CSS global | 71,88 | 59,04 |
| JavaScript de inicio | 12,76 | 9,55 |
| CSS de inicio | 4,00 | 5,42 |
| Ayuda compartida de presentación, nueva | 0 | 0,80 |
| Fotografía de portada original | 225,26 | Conservada |
| Variante de portada 640 px | — | 67,05 |
| Variante de portada 960 px | — | 189,79 |

También se efectuaron tres lecturas HTTP del HTML inicial en localhost para cada versión. Son comprobaciones de disponibilidad/transporte local, **no mediciones de renderizado, móvil ni Core Web Vitals**; sus tiempos no permiten afirmar una mejora de experiencia. Datos en `scratch/ibernovia-review/comparison.json`. Los archivos compilados y las imágenes sí muestran una reducción objetiva de recursos; no se promete una puntuación.

## Validación pendiente antes de publicar

La revisión visual automatizada quedó bloqueada anteriormente por el control de Chrome, que no pudo identificar su URL con confianza. No se consideran completadas todavía las capturas de inicio/catálogo/ficha, revisión móvil/escritorio, navegación por teclado completa, consola, mediciones repetidas LCP/CLS en navegador, recorrido interactivo de búsqueda/filtros/favoritos ni guardado efectivo de PDF.

El inicio de sesión y la aceptación de altas requieren un entorno de pruebas con cuentas autorizadas; no se han usado credenciales reales ni se ha creado ninguna solicitud. El servidor local permite revisar las pantallas y bloquear envíos, pero no demostrar un login profesional correcto.

Además, conviene revisar por separado el token fijo que ya existe en el código cliente de contacto y el ámbito de caché profesional ante cambios de sesión con peticiones pendientes. No se ha alterado esa infraestructura o lógica dentro de este trabajo.

No se debe publicar hasta completar estas comprobaciones y recibir la revisión del propietario. Los resultados detallados de API e imágenes están en `scratch/ibernovia-review/public-api-audit.json` e `image-audit.json`.
