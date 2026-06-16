document.addEventListener('DOMContentLoaded', () => {
  const GITHUB_USERNAME = 'ajurpri';
  const reposContainer = document.getElementById('github-repos');
  const repoCountEl = document.getElementById('repo-count');
  
  // Custom language colors for Github repository badges
  const languageColors = {
    javascript: '#f1e05a',
    html: '#e34c26',
    css: '#563d7c',
    vue: '#41b883',
    java: '#b07219',
    python: '#3572a5',
    typescript: '#3178c6',
    shell: '#89e051',
    sql: '#e38c00',
    php: '#4f5d95'
  };

  // Custom descriptions for Alvaro's projects
  const customDescriptions = {
    'cartel-padel': 'Plataforma web PWA offline-first para la gestión de Torneo Pádel. Permite organizar cuadros clasificatorios (fixtures), registrar parejas y calcular horarios según disponibilidad, exportando agendas en iCal.',
    'ibernovia': 'Plataforma completa de comercio electrónico para complementos de alta costura nupcial, integrando paneles de administración a medida y pasarela de tarifas.',
    'ibernovias': 'E-commerce full-stack (Vue, Spring Boot, MySQL) para catálogo de novias con cálculo de tarifas mayoristas personalizadas y gestión de stock integrada.',
    'mantecadospatriarca': 'Portal Spring Boot integrado con Google Sheets API y Firebase Auth para coordinar y registrar pedidos de dulces navideños de manera automatizada.',
    'tenis-isturgi': 'Plataforma interactiva en React y Node.js para la gestión de clasificaciones, divisiones y reserva de pistas de tenis del club local, integrando el algoritmo Berger.',
    'reaktor_redes-main': 'API REST en Spring Boot para escaneo paralelo de subredes del instituto, utilizando pools de hilos concurrentes para verificar el estado de conexión de hosts locales.'
  };

  // THEME SWITCHER LOGIC
  const colorDots = document.querySelectorAll('.color-dot');
  const themeColors = {
    rose: { primary: '345', secondary: '320' },
    lime: { primary: '85', secondary: '115' },
    sky: { primary: '195', secondary: '220' },
    amber: { primary: '40', secondary: '15' }
  };

  colorDots.forEach(dot => {
    dot.addEventListener('click', () => {
      colorDots.forEach(d => d.classList.remove('active'));
      dot.classList.add('active');
      
      const themeName = dot.getAttribute('data-theme');
      const colors = themeColors[themeName];
      if (colors) {
        document.documentElement.style.setProperty('--primary-hue', colors.primary);
        document.documentElement.style.setProperty('--secondary-hue', colors.secondary);
        document.documentElement.style.setProperty('--primary', `hsl(${colors.primary}, 100%, 65%)`);
        document.documentElement.style.setProperty('--primary-glow', `hsla(${colors.primary}, 100%, 65%, 0.15)`);
        document.documentElement.style.setProperty('--secondary', `hsl(${colors.secondary}, 95%, 60%)`);
        document.documentElement.style.setProperty('--secondary-glow', `hsla(${colors.secondary}, 95%, 60%, 0.15)`);
      }
    });
  });

  // SYNTAX HIGHLIGHTER UTILITY
  function highlightCode(text, lang) {
    if (!text) return '';
    // Escape basic HTML tags to prevent execution
    let html = text.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    
    if (lang === 'sql') {
      // DDL Keywords
      html = html.replace(/\b(CREATE|TABLE|IF|NOT|EXISTS|PRIMARY|KEY|FOREIGN|REFERENCES|VARCHAR|INT|DECIMAL|DATETIME|BIGINT|AUTO_INCREMENT|UNIQUE|DEFAULT|TRUE|FALSE|SERIAL|TIMESTAMP|ON|UPDATE|CURRENT_TIMESTAMP|DELETE|SET|NULL|INSERT|IGNORE|INTO|VALUES|USE|DATABASE|DOUBLE|BOOLEAN|ENGINE|InnoDB|CHARSET|COLLATE|COMMENT)\b/g, '<span class="code-keyword">$1</span>');
      // String Literals
      html = html.replace(/(['`][^'`]*['`])/g, '<span class="code-string">$1</span>');
      // Comments
      html = html.replace(/(--.*)/g, '<span class="code-comment">$1</span>');
      // Numbers
      html = html.replace(/\b(\d+)\b/g, '<span class="code-number">$1</span>');
    } 
    else if (lang === 'java' || lang === 'js' || lang === 'ts' || lang === 'typescript') {
      // General keywords
      html = html.replace(/\b(public|private|protected|class|interface|void|return|int|boolean|double|String|List|new|throws|import|package|static|final|if|else|for|while|try|catch|finally|throw|const|function|let|var|async|await|export|default|extends|implements|true|false|null|@Service|@Autowired|@Value|@Override|@GetMapping|@PostMapping)\b/g, '<span class="code-keyword">$1</span>');
      // Classes / Types (Capitalized terms)
      html = html.replace(/\b([A-Z][a-zA-Z0-9_]*)\b/g, '<span class="code-class">$1</span>');
      // Strings
      html = html.replace(/(["'`][^"'`]*["'`])/g, '<span class="code-string">$1</span>');
      // Single line comments
      html = html.replace(/(\/\/.*)/g, '<span class="code-comment">$1</span>');
      // Numbers
      html = html.replace(/\b(\d+)\b/g, '<span class="code-number">$1</span>');
      // Function execution calls
      html = html.replace(/\b([a-zA-Z0-9_]+)(?=\()/g, '<span class="code-function">$1</span>');
    }
    return html;
  }

  // PROJECT DATA STORE (IDE-STYLE MODAL DETAILS)
  const projectsData = {
    'ibernovia': {
      title: 'Ibernovia Atelier',
      subtitle: 'E-Commerce Real & Gestión de Citas Nupciales',
      image: 'images/ibernovia.png',
      overview: `
        <p><strong>Ibernovia Atelier</strong> es una plataforma web completa de comercio electrónico y catálogo digital desarrollada a medida para una tienda física de vestidos y complementos de novia y ceremonia en Andújar (Jaén).</p>
        <p>El sistema unifica la experiencia de compra y reserva. Resuelve la necesidad del negocio local de mostrar su stock físico de vestidos y complementos organizados en categorías (arras, comunión, novia, novio), automatiza el flujo de reservas de citas presenciales de las clientas en el probador y gestiona un panel multi-rol donde los administradores autorizan a empresas externas a ver tarifas comerciales mayoristas.</p>
        <h4>Características Clave:</h4>
        <ul>
          <li><strong>Catálogo Dinámico:</strong> Organización detallada del stock de complementos con panel administrativo.</li>
          <li><strong>Calendario de Citas:</strong> Reservas automatizadas por tipo de servicio con comprobación de solapamientos horarios.</li>
          <li><strong>Tarifas Multi-Rol:</strong> Oculta o expone precios mayoristas en función del estado de verificación de las cuentas comerciales.</li>
        </ul>
      `,
      architecture: `
        <p>La aplicación está estructurada bajo una <strong>arquitectura de tres capas (Three-Tier Architecture)</strong> robusta:</p>
        <ul>
          <li><strong>Frontend:</strong> Cliente reactivo de tipo Single Page Application (SPA) desarrollado con <strong>Vue 3, Vite, Pinia y Axios</strong>.</li>
          <li><strong>Backend:</strong> API REST desarrollada en <strong>Java 17 con Spring Boot y Maven</strong>, encargada de la seguridad de sesión, controladores de stock y cálculo de tarifas dinámicas.</li>
          <li><strong>Persistencia:</strong> Base de datos relacional <strong>MySQL</strong> gestionada a través de JPA e Hibernate, asegurando consistencia de datos y transacciones seguras de pedidos.</li>
          <li><strong>Autenticación:</strong> Integración con <strong>Firebase Auth</strong> para el login social y registro seguro de usuarios.</li>
        </ul>
      `,
      database: `CREATE TABLE productos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    precio DOUBLE,
    imagen LONGTEXT,
    descripcion VARCHAR(500),
    stock INT DEFAULT 0,
    activo BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    firebase_uid VARCHAR(255) UNIQUE,
    nombre VARCHAR(150),
    email VARCHAR(255) UNIQUE NOT NULL,
    rol VARCHAR(50) DEFAULT 'CLIENTE', -- CLIENTE, EMPRESA, ADMIN
    empresa_verificada BOOLEAN DEFAULT FALSE
);

CREATE TABLE citas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT,
    fecha_hora DATETIME NOT NULL,
    tipo_servicio VARCHAR(100), -- Novia, Novio, Comunión, Arras
    estado VARCHAR(50) DEFAULT 'Pendiente', -- Pendiente, Confirmada, Cancelada
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);`,
      code: `// Spring Boot Service - Control dinámico de tarifas comerciales por rol de cliente
package com.ibernovia.atelier.service;

import com.ibernovia.atelier.model.Producto;
import com.ibernovia.atelier.model.Usuario;
import com.ibernovia.atelier.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository repo;

    public List<ProductoDTO> getCatalogoParaUsuario(Usuario usuario, String categoria) {
        List<Producto> productos = repo.findByCategoriaAndActivoTrue(categoria);
        boolean esMayorista = usuario != null && 
                              "EMPRESA".equals(usuario.getRol()) && 
                              usuario.isEmpresaVerificada();

        return productos.stream().map(p -> {
            ProductoDTO dto = new ProductoDTO(p);
            if (!esMayorista) {
                // Ocultar tarifas comerciales corporativas en el catálogo público
                dto.setPrecioMayorista(null);
                dto.setPrecioPublico(p.getPrecio());
            } else {
                // Aplicar tarifa mayorista especial configurada en base de datos (25% desc)
                dto.setPrecioPublico(p.getPrecio() * 0.75);
            }
            return dto;
        }).collect(Collectors.toList());
    }
}`,
      challenge: `
        <p><strong>Reto Resuelto: Sincronización inmediata de permisos comerciales.</strong></p>
        <p>Cuando un administrador revocaba el acceso comercial corporativo a un cliente, el navegador del usuario seguía mostrando los precios mayoristas debido al almacenamiento local en el frontend (localStorage) o al token almacenado en Firebase.</p>
        <p><strong>Solución:</strong> Se programó un interceptor dinámico en Axios y un hook de verificación de sesión de Vue 3. El backend realiza una validación atómica en base de datos de los roles del usuario en cada solicitud. Al detectar que ya no está verificado, fuerza al frontend a recargar el almacén reactivo (Pinia) y ocultar inmediatamente las tarifas corporativas sin necesidad de cerrar sesión.</p>
      `
    },
    'tenis': {
      title: 'Tenis Isturgi',
      subtitle: 'Plataforma Deportiva de Reservas & Brackets de Ligas',
      image: 'images/TENISISTURGI.png',
      overview: `
        <p><strong>Tenis Isturgi</strong> es una plataforma web completa desarrollada para la gestión administrativa y participación de socios del club de tenis de Andújar. La web centraliza cientos de funcionalidades críticas del club.</p>
        <p>Permite visualizar el calendario de jornadas y clasificaciones de la liga local, registrar resultados de enfrentamientos, controlar las divisiones por niveles de socios, administrar fichas de jugadores y gestionar reservas de pistas en tiempo real con control estricto de disponibilidad horaria.</p>
        <h4>Características Clave:</h4>
        <ul>
          <li><strong>Reserva de Pistas:</strong> Calendarios semanales interactivos para reservar pistas con límite de horas.</li>
          <li><strong>Generación de Ligas:</strong> Sorteo de cruces Round-Robin automatizado para todos los jugadores.</li>
          <li><strong>Panel de Clasificación:</strong> Puntuación calculada al instante según sets y juegos ganados.</li>
        </ul>
      `,
      architecture: `
        <p>La aplicación utiliza un patrón moderno de <strong>Headless CMS</strong>:</p>
        <ul>
          <li><strong>Frontend:</strong> Interfaz SPA modular y reactiva construida en <strong>React y TailwindCSS</strong>.</li>
          <li><strong>Backend:</strong> Servidor backend desacoplado montado sobre <strong>Strapi CMS (Node.js y TypeScript)</strong> que expone endpoints REST seguros autenticados por JWT.</li>
          <li><strong>Base de datos:</strong> Persistencia en base de datos relacional <strong>PostgreSQL</strong> para gestionar relaciones complejas de temporadas, divisiones y partidos.</li>
        </ul>
      `,
      database: `CREATE TABLE temporadas (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE divisiones (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  categoria VARCHAR(100) NOT NULL DEFAULT 'Absoluto',
  nivel VARCHAR(100) NOT NULL DEFAULT 'Medio',
  temporada_id BIGINT,
  FOREIGN KEY (temporada_id) REFERENCES temporadas(id) ON DELETE SET NULL
);

CREATE TABLE jornadas (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  numero INT,
  division_id BIGINT,
  fecha_limite_disponibilidad DATETIME,
  disponibilidad_cerrada BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (division_id) REFERENCES divisiones(id) ON DELETE SET NULL
);

CREATE TABLE jugadores (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  apellidos VARCHAR(255),
  telefono VARCHAR(255),
  email VARCHAR(255),
  firebase_uid VARCHAR(255) UNIQUE,
  division_id BIGINT,
  FOREIGN KEY (division_id) REFERENCES divisiones(id) ON DELETE SET NULL
);`,
      code: `// TypeScript Service (Strapi) - Implementación del Algoritmo Berger Round-Robin
export function generarCalendarioBerger(jugadores: any[]): any[][] {
    const n = jugadores.length;
    const esImpar = n % 2 === 1;
    const numPlayers = esImpar ? n + 1 : n;
    const numJornadas = numPlayers - 1;
    const partidosPorJornada = numPlayers / 2;

    const list = [...jugadores];
    if (esImpar) list.push({ id: -1, nombre: "BYE (Descanso)" });

    const jornadas: any[][] = [];

    for (let j = 0; j < numJornadas; j++) {
        const ronda: any[] = [];
        for (let i = 0; i < partidosPorJornada; i++) {
            const p1 = list[i];
            const p2 = list[numPlayers - 1 - i];
            if (p1.id !== -1 && p2.id !== -1) {
                ronda.push({ jugador1: p1, jugador2: p2 });
            }
        }
        jornadas.push(ronda);

        // Rotación Berger: mantiene fijo el primer elemento, desplaza el resto circularmente
        const primerElemento = list[0];
        const ultimoElemento = list[numPlayers - 1];
        for (let i = numPlayers - 1; i > 1; i--) {
            list[i] = list[i - 1];
        }
        list[1] = ultimoElemento;
    }
    return jornadas;
}`,
      challenge: `
        <p><strong>Reto Resuelto: Generación balanceada y matemática de emparejamientos de ligas.</strong></p>
        <p>El club requería generar calendarios de enfrentamientos de todos contra todos sin repetir rivales, asignando pistas y gestionando descansos de forma justa si el número de socios en la división era impar.</p>
        <p><strong>Solución:</strong> Se codificó la rotación matemática del Algoritmo Berger (Round Robin) en TypeScript en la capa de servicios de Strapi. El sistema calcula las rotaciones y guarda de forma atómica el fixture completo mediante una transacción en PostgreSQL para evitar calendarios corruptos.</p>
      `
    },
    'reaktor': {
      title: 'Reaktor Redes',
      subtitle: 'Dashboard de Conectividad con Vista de Pájaro en Tiempo Real',
      image: 'images/REDES-MAPA.png',
      overview: `
        <p><strong>Reaktor Redes</strong> es una aplicación de supervisión crítica desarrollada para el departamento de informática del instituto. Su objetivo es mantener monitorizada toda la infraestructura de red local del edificio escolar.</p>
        <p>El sistema expone un panel interactivo que renderiza un plano vectorizado o <strong>"vista de pájaro" del instituto</strong>, iluminando en tiempo real en verde (activo) o rojo (caído) cada aula, laboratorio de informática, departamento o servidor según su estado de conexión de red.</p>
      `,
      architecture: `
        <p>La aplicación sigue una arquitectura asíncrona orientada a eventos:</p>
        <ul>
          <li><strong>Backend:</strong> API REST robusta construida con <strong>Spring Boot (Java 17)</strong> y Maven.</li>
          <li><strong>Escaneo Concurrente:</strong> Utiliza pools de hilos concurrentes (<code>ExecutorService</code>) para ejecutar barridos paralelos de pings a través de sockets TCP a los 254 hosts posibles de la subred en segundos.</li>
          <li><strong>Frontend:</strong> Mapa web interactivo que asocia elementos gráficos vectoriales SVG a las IPs devueltas por la API, actualizándolas sin refrescar el navegador.</li>
        </ul>
      `,
      database: `// Estructura de Persistencia en Memoria H2 para almacenamiento de Historial
CREATE TABLE red_dispositivos (
    ip VARCHAR(45) PRIMARY KEY,
    hostname VARCHAR(255) NOT NULL,
    aula VARCHAR(50) NOT NULL,
    reachable BOOLEAN DEFAULT FALSE,
    ultimo_escaneo TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE puertos_escaneados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ip VARCHAR(45) NOT NULL,
    puerto INT NOT NULL,
    estado VARCHAR(20) DEFAULT 'CERRADO', -- ABIERTO, FILTRADO, CERRADO
    FOREIGN KEY (ip) REFERENCES red_dispositivos(ip) ON DELETE CASCADE
);`,
      code: `// Java Subnet Parallel Sweep Service via ExecutorService & Sockets
package Reaktor_redes_main.service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import org.springframework.stereotype.Service;

@Service
public class NetworkScannerService {
    private final int THREADS = 64;

    public List<Device> runParallelSweep(String subnetPrefix) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        List<Future<Device>> tasks = new ArrayList<>();

        for (int i = 1; i <= 254; i++) {
            final String ip = subnetPrefix + i;
            tasks.add(pool.submit(() -> {
                InetAddress inet = InetAddress.getByName(ip);
                // Envía ping ICMP con Timeout de 800ms
                boolean ok = inet.isReachable(800);
                return new Device(ip, inet.getHostName(), ok);
            }));
        }

        List<Device> list = new ArrayList<>();
        for (Future<Device> future : tasks) {
            list.add(future.get());
        }
        pool.shutdown();
        return list;
    }
}`,
      challenge: `
        <p><strong>Reto Resuelto: Latencia de escaneo y mapeo en el plano.</strong></p>
        <p>Ejecutar un escaneo secuencial de pings a 254 IPs tardaría más de 4 minutos, inutilizando la vista en tiempo real del plano. Además, mapear los resultados con el plano SVG requería una asociación limpia.</p>
        <p><strong>Solución:</strong> Se implementó una piscina de 64 hilos concurrentes en Java que redujo el tiempo total de escaneo a solo 3 segundos. El frontend lee el JSON resultante y actualiza dinámicamente las clases CSS de los elementos SVG basándose en los hostnames de las aulas mapeadas, garantizando una respuesta inmediata.</p>
      `
    },
    'padel': {
      title: 'Torneo Pádel',
      subtitle: 'Gestor de Torneos Offline-First, Canvas & Google Webhooks',
      image: 'images/CARTEL-PADEL.png',
      overview: `
        <p><strong>Torneo Pádel</strong> es una aplicación web de gestión deportiva enfocada en la organización, fixtures y cartelería de torneos locales de pádel y tenis.</p>
        <p>La aplicación permite a los organizadores registrar las parejas participantes por categorías, generar de forma automatizada las jornadas y enfrentamientos (brackets) cruzando la disponibilidad horaria específica de cada jugador, y exportar la agenda de partidos en formatos iCal (ICS) y la cartelería publicitaria en PNG de alta definición para redes sociales.</p>
      `,
      architecture: `
        <p>La aplicación está pensada para ser independiente y económica de desplegar:</p>
        <ul>
          <li><strong>Estructura Frontend:</strong> Aplicación web progresiva (PWA) de una sola página en <strong>HTML5, CSS Grid y JavaScript ES6</strong>.</li>
          <li><strong>Persistencia:</strong> 100% offline-first. Los torneos y brackets se guardan localmente en el navegador mediante <strong>LocalStorage</strong>, permitiendo importar/exportar bases de datos en JSON.</li>
          <li><strong>Backend Serverless:</strong> Para el formulario de contacto e interesados (leads) comerciales sin coste de servidor, se implementó un Webhook que envía solicitudes HTTP POST a un script en <strong>Google Apps Script</strong> que escribe datos en Google Sheets.</li>
          <li><strong>Cartelería Canvas:</strong> Utiliza <strong>html2canvas</strong> para renderizar en caliente la tabla de emparejamientos y exportarla en PNG HD de cara a su impresión física o compartición digital.</li>
        </ul>
      `,
      database: `// Representación lógica del esquema JSON almacenado en LocalStorage
CREATE TABLE locales_torneos (
    id VARCHAR(50) PRIMARY KEY, -- UID local
    titulo VARCHAR(255) NOT NULL,
    pistas_disponibles INT DEFAULT 3,
    parejas_inscritas JSON, -- Array de IDs de parejas
    partidos_fixture JSON, -- Cuadrante generado de partidos
    configuracion_horas JSON -- Matriz de franjas horarias
);`,
      code: `// JavaScript PWA - Exportación iCalendar (.ics) para sincronizar Calendarios
function exportarCalendarioICS(partidos) {
    let icsContent = 'BEGIN:VCALENDAR\\nVERSION:2.0\\nPRODID:-//PadelMasterPro//EN\\n';
    
    partidos.forEach((match, index) => {
        const uid = \`padel-\${Date.now()}-\${index}@padelmaster\`;
        const summary = \`Partido: \${match.c1Name} vs \${match.c2Name} (\${match.categoria})\`;
        const dtstamp = new Date().toISOString().replace(/[-:]/g,'').split('.')[0] + 'Z';
        
        // Generar evento iCal con formateo correcto de saltos de línea
        icsContent += 'BEGIN:VEVENT\\n';
        icsContent += \`UID:\${uid}\\n\`;
        icsContent += \`DTSTAMP:\${dtstamp}\\n\`;
        icsContent += \`DTSTART:\${dtstamp}\\n\`; // Fecha y hora del encuentro
        icsContent += \`SUMMARY:\${summary.replace(/,/g, '\\\\,')}\\n\`;
        icsContent += \`DESCRIPTION:Pistas del Club | Horario: \${match.horario}\\n\`;
        icsContent += 'END:VEVENT\\n';
    });
    
    icsContent += 'END:VCALENDAR';
    const blob = new Blob([icsContent], { type: 'text/calendar;charset=utf-8' });
    return URL.createObjectURL(blob); // Descarga directa del archivo ICS
}`,
      challenge: `
        <p><strong>Reto Resuelto: Algoritmo de fixtures y disponibilidad.</strong></p>
        <p>El principal problema del organizador del torneo era coordinar los partidos según las horas en que cada pareja podía jugar, evitando solapamientos y maximizando el uso de las pistas libres del club.</p>
        <p><strong>Solución:</strong> Se programó un despachador en JavaScript que cruza las matrices de disponibilidad horaria de las parejas. El algoritmo asigna los partidos de forma secuencial en las franjas horarias concurrentes y les asigna una de las pistas activas, marcando de forma automática los partidos en conflicto como "Aplazado" si no hay horarios comunes.</p>
      `
    },
    'mantecados': {
      title: 'Mantecados El Patriarca',
      subtitle: 'Portal de Pedidos Escolares Sincronizado en Google Sheets',
      image: 'images/Patriarca.png',
      overview: `
        <p><strong>Mantecados El Patriarca</strong> es un portal web de gestión de pedidos escolares de dulces y polvorones tradicionales navideños desarrollado para simplificar las campañas anuales de captación de fondos en institutos.</p>
        <p>La web permite a los alumnos e integrantes del instituto registrarse e iniciar sesión de forma segura, seleccionar variedades de dulces de repostería desde un catálogo visual, gestionar su carrito y tramitar un pedido. La aplicación calcula los precios y guarda y organiza de forma automática qué pide cada usuario directamente en una hoja centralizada de Google Sheets (actuando como base de datos Excel remota).</p>
      `,
      architecture: `
        <p>La aplicación utiliza una combinación híbrida de bases de datos relacionales y Sheets:</p>
        <ul>
          <li><strong>Vistas Frontend:</strong> Pantallas dinámicas HTML5 y plantillas <strong>Thymeleaf</strong> integradas con JavaScript para el control del carrito.</li>
          <li><strong>Servidor Backend:</strong> Servicio desarrollado en <strong>Spring Boot (Java 17)</strong> que interactúa con la SDK oficial de Google Sheets API en Java.</li>
          <li><strong>Seguridad:</strong> Autenticación y registro de credenciales controlado mediante <strong>Firebase Authentication</strong>.</li>
          <li><strong>Base de datos Excel remota:</strong> Google Sheets API (utilizada para lectura y escritura directa de celdas con fórmulas de suma de pedidos automáticas).</li>
        </ul>
      `,
      database: `CREATE TABLE configuraciones (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sheet_name VARCHAR(255) NOT NULL,
    fila_inicio_alumnos INT DEFAULT 7,
    max_alumnos INT DEFAULT 22,
    columna_alumnos VARCHAR(5) DEFAULT 'A',
    fila_cabecera_productos INT DEFAULT 2,
    columna_inicio_productos VARCHAR(5) DEFAULT 'B'
);

CREATE TABLE pedidos_locales_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    alumno VARCHAR(255) NOT NULL,
    producto VARCHAR(255) NOT NULL,
    cantidad INT NOT NULL,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);`,
      code: `// Spring Boot Service - Conexión y escritura de cantidad en Google Sheets API
package com.sheets.google_sheets.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetsService {
    @Autowired
    private Sheets sheetsService;

    public String escribirEnHoja(String spreadsheetId, String rango, String valor) throws IOException {
        List<List<Object>> values = Collections.singletonList(
            Collections.singletonList(valor)
        );

        ValueRange body = new ValueRange().setValues(values);
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
            .update(spreadsheetId, rango, body)
            .setValueInputOption("USER_ENTERED") // Ejecuta fórmulas automáticamente
            .execute();

        return "Celda " + result.getUpdatedRange() + " actualizada con: " + valor;
    }
}`,
      challenge: `
        <p><strong>Reto Resuelto: Escritura concurrente en Google Sheets sin ralentizar el servidor.</strong></p>
        <p>La API de Google Sheets tiene una latencia de respuesta elevada por petición HTTP (más de 1 segundo), lo que provocaba que cuando múltiples alumnos hacían un pedido a la vez en el instituto, el frontend se congelaba esperando la confirmación de escritura en Excel.</p>
        <p><strong>Solución:</strong> Se implementó una arquitectura asíncrona en Java. Spring Boot procesa el pedido en una cola en memoria en segundo plano, devolviendo una respuesta de éxito inmediata al frontend mientras un hilo asíncrono gestiona las llamadas de escritura con la API de Google Sheets de forma segura.</p>
      `
    }
  };

  // CATEGORIES FILTER DELEGATION
  const filterButtons = document.querySelectorAll('.filter-btn');
  const projectCards = document.querySelectorAll('.project-card');

  filterButtons.forEach(btn => {
    btn.addEventListener('click', () => {
      filterButtons.forEach(b => b.classList.remove('active'));
      btn.classList.add('active');

      const filter = btn.getAttribute('data-filter');

      projectCards.forEach(card => {
        const categories = card.getAttribute('data-category').split(' ');
        if (filter === 'all' || categories.includes(filter)) {
          card.style.display = 'flex';
          setTimeout(() => { card.style.opacity = '1'; }, 50);
        } else {
          card.style.opacity = '0';
          setTimeout(() => { card.style.display = 'none'; }, 200);
        }
      });
    });
  });

  // IDE TAB AND SIDEBAR NAVIGATION CO-ORDINATOR
  const modalOverlay = document.getElementById('project-modal');
  const modalClose = document.getElementById('modal-close');
  
  const sidebarItems = document.querySelectorAll('.sidebar-file-item');
  const editorTabs = document.querySelectorAll('.editor-tab');
  const tabPanes = document.querySelectorAll('.ide-tab-pane');
  const modalTopFilename = document.getElementById('modal-top-filename');

  let activeProjectKey = null;

  function generateLineNumbers(codeText, elementId) {
    const lines = (codeText || '').split('\n').length;
    const container = document.getElementById(elementId);
    if (container) {
      let html = '';
      for (let i = 1; i <= lines; i++) {
        html += `<div>${i}</div>`;
      }
      container.innerHTML = html;
    }
  }

  function switchTab(tabName) {
    // Update sidebar active class
    sidebarItems.forEach(item => {
      if (item.getAttribute('data-tab') === tabName) {
        item.classList.add('active');
      } else {
        item.classList.remove('active');
      }
    });

    // Update tabs active class
    editorTabs.forEach(tab => {
      if (tab.getAttribute('data-tab') === tabName) {
        tab.classList.add('active');
      } else {
        tab.classList.remove('active');
      }
    });

    // Update file tab label
    const filenames = {
      overview: 'Readme.md',
      architecture: 'Arquitectura.json',
      database: 'Esquema.sql',
      code: 'CodigoFuente.java',
      challenge: 'RetoTecnico.md'
    };
    modalTopFilename.textContent = filenames[tabName] || 'Readme.md';

    // Show right pane
    tabPanes.forEach(pane => {
      pane.classList.remove('active');
    });

    const activePane = document.getElementById(`pane-${tabName}`);
    if (activePane) {
      activePane.classList.add('active');
    }

    // Populate tab content dynamically
    if (activeProjectKey && projectsData[activeProjectKey]) {
      const data = projectsData[activeProjectKey];
      
      if (tabName === 'overview') {
        document.getElementById('modal-overview').innerHTML = data.overview;
      } else if (tabName === 'architecture') {
        document.getElementById('modal-architecture').innerHTML = data.architecture;
      } else if (tabName === 'database') {
        const dbCode = document.getElementById('modal-database');
        dbCode.innerHTML = highlightCode(data.database, 'sql');
        generateLineNumbers(data.database, 'line-numbers-database');
      } else if (tabName === 'code') {
        const sourceCode = document.getElementById('modal-code');
        // Check if code language is java/js
        const lang = activeProjectKey === 'padel' ? 'js' : 'java';
        sourceCode.innerHTML = highlightCode(data.code, lang);
        generateLineNumbers(data.code, 'line-numbers-code');
      } else if (tabName === 'challenge') {
        document.getElementById('modal-challenge').innerHTML = data.challenge;
      }
    }
  }

  // Bind click event on both tabs and sidebar items
  sidebarItems.forEach(item => {
    item.addEventListener('click', () => {
      switchTab(item.getAttribute('data-tab'));
    });
  });

  editorTabs.forEach(tab => {
    tab.addEventListener('click', () => {
      switchTab(tab.getAttribute('data-tab'));
    });
  });

  // Modal Open Handlers
  document.body.addEventListener('click', (e) => {
    const detailsBtn = e.target.closest('.btn-read-more');
    if (detailsBtn) {
      const pKey = detailsBtn.getAttribute('data-project');
      const data = projectsData[pKey];

      if (data) {
        activeProjectKey = pKey;
        
        // Fill base modal contents
        document.getElementById('modal-title').textContent = data.title;
        document.getElementById('modal-sidebar-title').textContent = pKey.toUpperCase();
        document.getElementById('modal-banner-title').textContent = data.title;
        document.getElementById('modal-subtitle').textContent = data.subtitle;
        document.getElementById('modal-img').src = data.image;

        // Reset to Overview tab
        switchTab('overview');

        // Open modal
        modalOverlay.classList.add('active');
        document.body.style.overflow = 'hidden';
      }
    }
  });

  function closeModal() {
    modalOverlay.classList.remove('active');
    document.body.style.overflow = '';
    activeProjectKey = null;
  }

  modalClose.addEventListener('click', closeModal);
  modalOverlay.addEventListener('click', (e) => {
    if (e.target === modalOverlay) closeModal();
  });
  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') closeModal();
  });

  // COPY EMAIL TO CLIPBOARD WITH DYNAMIC TOAST
  const copyBtn = document.getElementById('copy-email-btn');
  const toast = document.getElementById('email-copied-toast');
  if (copyBtn && toast) {
    copyBtn.addEventListener('click', () => {
      const email = 'alvarorema2016@gmail.com';
      navigator.clipboard.writeText(email).then(() => {
        toast.classList.add('show');
        setTimeout(() => {
          toast.classList.remove('show');
        }, 2500);
      }).catch(err => {
        console.error('Copy to clipboard failed', err);
      });
    });
  }

  // DYNAMIC GITHUB API LOADER
  function renderGitHubLoading() {
    reposContainer.innerHTML = `
      <div class="loading">
        <div class="spinner"></div>
        <p style="color: var(--text-secondary); font-size: 14px;">Conectando con la API de GitHub...</p>
      </div>
    `;
  }

  function renderGitHubError(errMessage) {
    reposContainer.innerHTML = `
      <div class="loading" style="color: #f87171;">
        <p>⚠️ Error al cargar repositorios de GitHub: ${errMessage}</p>
        <p style="font-size: 13px; margin-top: 8px; color: var(--text-muted);">
          Puedes ver el código de mis proyectos en: <a href="https://github.com/${GITHUB_USERNAME}" target="_blank" class="repo-link">github.com/${GITHUB_USERNAME}</a>
        </p>
      </div>
    `;
  }

  async function fetchGitHubRepos() {
    renderGitHubLoading();
    try {
      const userRes = await fetch(`https://api.github.com/users/${GITHUB_USERNAME}`);
      if (userRes.ok) {
        const userData = await userRes.json();
        const avatarEl = document.getElementById('github-avatar');
        if (avatarEl) {
          avatarEl.src = userData.avatar_url;
        }
      }

      const reposRes = await fetch(`https://api.github.com/users/${GITHUB_USERNAME}/repos?sort=updated&per_page=100`);
      if (!reposRes.ok) {
        throw new Error('API Rate limit o error de conexión.');
      }

      let repos = await reposRes.json();
      reposContainer.innerHTML = '';

      if (repos.length === 0) {
        reposContainer.innerHTML = '<p class="loading" style="color: var(--text-secondary);">No hay repositorios públicos disponibles.</p>';
        return;
      }

      // Sort by activity and star weight
      repos = repos.sort((a, b) => (b.stargazers_count + (b.fork ? 0 : 4)) - (a.stargazers_count + (a.fork ? 0 : 4)));
      
      if (repoCountEl) {
        repoCountEl.textContent = repos.length;
      }

      repos.forEach(repo => {
        const card = document.createElement('div');
        card.className = 'repo-card';

        const lang = repo.language ? repo.language.toLowerCase() : 'other';
        const langColor = languageColors[lang] || '#64748b';
        const langText = repo.language || 'Otros';

        const repoNameKey = repo.name.toLowerCase();
        let desc = repo.description;
        if (customDescriptions[repoNameKey]) {
          desc = customDescriptions[repoNameKey];
        } else if (!desc) {
          desc = 'Proyecto de código abierto desarrollado para aportar soluciones a medida y automatizaciones en la nube.';
        }

        card.innerHTML = `
          <div class="repo-header">
            <h4 class="repo-title">${repo.name}</h4>
            <span class="repo-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M15 22v-4a4.8 4.8 0 0 0-1-3.5c3 0 6-2 6-5.5.08-1.25-.27-2.48-1-3.5.28-1.15.28-2.35 0-3.5 0 0-1 0-3 1.5-2.64-.5-5.36-.5-8 0C6 2 5 2 5 2c-.3 1.15-.3 2.35 0 3.5A5.403 5.403 0 0 0 4 9c0 3.5 3 5.5 6 5.5-.39.49-.68 1.05-.85 1.65-.17.6-.22 1.23-.15 1.85v4"></path><path d="M9 18c-4.51 2-5-2-7-2"></path></svg>
            </span>
          </div>
          <p class="repo-description">${desc}</p>
          <div class="repo-footer">
            <div class="repo-lang">
              <span class="lang-dot" style="background-color: ${langColor}"></span>
              <span>${langText}</span>
            </div>
            <div class="repo-stats">
              ${repo.stargazers_count > 0 ? `
                <span style="display:inline-flex; align-items:center; gap:3px;">
                  <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="currentColor" style="color: #eab308;"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"></polygon></svg>
                  ${repo.stargazers_count}
                </span>
              ` : ''}
              <a href="${repo.html_url}" target="_blank" class="repo-link">Ver Código</a>
            </div>
          </div>
        `;
        reposContainer.appendChild(card);
      });
    } catch(err) {
      console.error(err);
      renderGitHubError(err.message || 'Error de red.');
    }
  }

  // ADVANCED INTERACTIVE TERMINAL CONSOLE
  const terminalBody = document.getElementById('terminal-body');
  const terminalInput = document.getElementById('terminal-input');

  const terminalCommands = {
    'help': 'Comandos de terminal disponibles:<br>' +
            '  - <span class="terminal-prompt-symbol">about</span>     : Conocer quién soy y mi trayectoria.<br>' +
            '  - <span class="terminal-prompt-symbol">projects</span>  : Listar mis 5 desarrollos técnicos destacados.<br>' +
            '  - <span class="terminal-prompt-symbol">tree</span>      : Estructura de archivos simulada del portfolio.<br>' +
            '  - <span class="terminal-prompt-symbol">skills</span>    : Mostrar mis habilidades de ingeniería de software.<br>' +
            '  - <span class="terminal-prompt-symbol">ping [IP]</span>  : Simular un ping de monitorización (ej: ping 192.168.1.1).<br>' +
            '  - <span class="terminal-prompt-symbol">date</span>      : Fecha y hora del sistema.<br>' +
            '  - <span class="terminal-prompt-symbol">contact</span>   : Datos de contacto directo.<br>' +
            '  - <span class="terminal-prompt-symbol">clear</span>     : Limpiar la consola.',
            
    'about': 'Hola, soy Álvaro. Desarrollador de Software y Web Full-Stack residente en Andújar.<br>' +
             'Me apasiona la lógica del backend, el diseño y la optimización de bases de datos relacionales,<br>' +
             'y la creación de integraciones que resuelven problemas del mundo real.',
             
    'projects': 'Mis 5 desarrollos destacados:<br>' +
                '  1. <b>Ibernovia Atelier</b> : E-commerce premium y reservas con Vue 3 y Spring Boot.<br>' +
                '  2. <b>Tenis Isturgi</b> : Portal deportivo completo con reservas y algoritmo de Berger.<br>' +
                '  3. <b>Reaktor Redes</b> : Monitorizador de subredes del instituto sobre mapa SVG.<br>' +
                '  4. <b>Torneo Pádel</b> : PWA offline-first de fixtures de torneos y exportación iCal.<br>' +
                '  5. <b>Mantecados Patriarca</b> : Carrito de pedidos escolar sincronizado en Google Sheets API.',
                
    'tree': '.<br>' +
            '├── portfolio/<br>' +
            '│   ├── index.html<br>' +
            '│   ├── style.css<br>' +
            '│   ├── app.js<br>' +
            '│   └── images/<br>' +
            '│       ├── ibernovia.png<br>' +
            '│       ├── tenis.png<br>' +
            '│       ├── reaktor_terminal.svg<br>' +
            '│       ├── padel.jpg<br>' +
            '│       └── mantecados.png<br>' +
            '└── Proyectos-Álvaro/ (proyectos locales referenciados)',
            
    'skills': 'Habilidades del sistema:<br>' +
              '  - Backend   : Java (Spring Boot), Node.js (Strapi/Express), Python scripts.<br>' +
              '  - Frontend  : Vue 3 (Vite, Pinia), React, JavaScript ES6+, HTML5/CSS3.<br>' +
              '  - Persistencia & APIs: MySQL, PostgreSQL, Google Sheets API, Firebase SDK, LocalStorage.<br>' +
              '  - Infraestructura : Docker Compose, servidores VPS Linux, configuración de Nginx.',
              
    'date': () => { return new Date().toString(); },
    
    'contact': 'Medios de contacto:<br>' +
               '  - Email : <a href="mailto:alvarorema2016@gmail.com" style="color: var(--primary);">alvarorema2016@gmail.com</a><br>' +
               '  - GitHub: <a href="https://github.com/ajurpri" target="_blank" style="color: var(--primary);">github.com/ajurpri</a><br>' +
               '  - Localización: Andújar (Jaén), España.',
  };

  function simulatePing(ip, lineElement) {
    let count = 0;
    lineElement.innerHTML = `Haciendo ping a ${ip} con 32 bytes de datos:`;
    
    const interval = setInterval(() => {
      count++;
      const time = Math.floor(Math.random() * 25) + 5;
      const newLine = document.createElement('div');
      newLine.className = 'terminal-line';
      newLine.innerHTML = `Respuesta desde ${ip}: bytes=32 tiempo=${time}ms TTL=64`;
      terminalBody.appendChild(newLine);
      terminalBody.scrollTop = terminalBody.scrollHeight;
      
      if (count >= 4) {
        clearInterval(interval);
        const summary = document.createElement('div');
        summary.className = 'terminal-line';
        summary.style.color = 'var(--text-secondary)';
        summary.innerHTML = `<br>Estadísticas de ping para ${ip}:<br>  Paquetes: enviados = 4, recibidos = 4, perdidos = 0 (0% perdidos)<br>Tiempos aproximados de ida y vuelta en milisegundos:<br>  Mínimo = 5ms, Máximo = 29ms, Media = 15ms`;
        terminalBody.appendChild(summary);
        terminalBody.scrollTop = terminalBody.scrollHeight;
      }
    }, 600);
  }

  function executeCommand(cmdText) {
    const rawCmd = cmdText.trim();
    if (rawCmd === '') return;

    // Echo user command
    const echoLine = document.createElement('div');
    echoLine.className = 'terminal-line';
    echoLine.innerHTML = `<span class="terminal-prompt-symbol">alvaro@shell:~$</span> ${rawCmd}`;
    terminalBody.appendChild(echoLine);

    const parts = rawCmd.split(' ');
    const command = parts[0].toLowerCase();
    const arg = parts.slice(1).join(' ');

    if (command === 'clear') {
      terminalBody.innerHTML = '';
      terminalInput.value = '';
      return;
    }

    const responseLine = document.createElement('div');
    responseLine.className = 'terminal-line';

    if (command === 'ping') {
      if (!arg) {
        responseLine.innerHTML = '<span style="color: #f87171;">Uso: ping [dirección_ip_o_host]</span>';
        terminalBody.appendChild(responseLine);
      } else {
        terminalBody.appendChild(responseLine);
        simulatePing(arg, responseLine);
      }
    } 
    else if (terminalCommands[command]) {
      const output = terminalCommands[command];
      responseLine.innerHTML = typeof output === 'function' ? output() : output;
      terminalBody.appendChild(responseLine);
    } 
    else {
      responseLine.innerHTML = `<span style="color: #f87171;">Comando no reconocido: "${command}". Escribe <span style="color: #fff; font-weight: 600;">help</span> para ver la lista de comandos.</span>`;
      terminalBody.appendChild(responseLine);
    }

    terminalInput.value = '';
    terminalBody.scrollTop = terminalBody.scrollHeight;
  }

  // Keyboard and focus listener for terminal
  terminalInput.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') {
      executeCommand(terminalInput.value);
    }
  });

  terminalBody.addEventListener('click', () => {
    terminalInput.focus();
  });

  // Terminal welcome greeting loading
  function initTerminal() {
    setTimeout(() => {
      const lines = [
        'Welcome to Alvaro.Shell v1.0.0 (Linux x86_64)',
        'Connecting socket endpoints... OK.',
        'Initialising git submodules... OK.',
        'All systems active. Escribe <span class="terminal-prompt-symbol" style="text-decoration:underline; font-weight:600;">help</span> para consultar las opciones del portfolio.'
      ];
      
      lines.forEach((txt, idx) => {
        setTimeout(() => {
          const l = document.createElement('div');
          l.className = 'terminal-line';
          l.innerHTML = txt;
          terminalBody.appendChild(l);
          terminalBody.scrollTop = terminalBody.scrollHeight;
        }, idx * 250);
      });
    }, 400);
  }

  // RUN PAGE INITIALIZATION
  initTerminal();
  fetchGitHubRepos();
});
