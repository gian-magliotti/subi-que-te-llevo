# Subí Que Te Llevo

Versión en inglés: [README.md](README.md)

Simulador académico de un servicio de remises en Java, con interfaz Swing y énfasis en patrones de diseño, concurrencia y persistencia a lo largo del ciclo de viaje.

## Contexto académico y descripción general
- Trabajo Práctico Final (segunda parte) de Programación III, Facultad de Ingeniería (UNMDP), presentado el 09/06/2024.
- Objetivo: extender la primera entrega incorporando hilos, sincronización, MVC y serialización.
- Modela una empresa de remises que administra clientes, choferes y vehículos, resolviendo pedidos de viaje con asignación de recursos y cálculo de costos.
- Incluye simulación multi-hilo (clientes, choferes, sistema) y vistas que reflejan el estado de viajes y recursos en tiempo real.

## Qué hace la aplicación
- Simula el funcionamiento integral de una empresa de remises: registra clientes, choferes y vehículos para armar la flota.
- Los clientes inician sesión en la app Swing, solicitan viajes indicando zona, distancia, mascota/equipaje y cantidad de pasajeros; el sistema valida la solicitud y asigna el vehículo más adecuado según las reglas del modelo.
- Los pedidos y los hilos de chofer corren en paralelo: cada chofer toma, cobra y finaliza viajes mientras las vistas muestran en vivo las asignaciones y estados.
- La simulación mantiene histórico de viajes, choferes y vehículos para seguimiento y reportes internos dentro del escenario académico.

## Funcionalidades Principales
- Altas, listados y reportes de usuarios, choferes y vehículos (`sistema/AdmSubSistema`).
- Validación de pedidos, creación de viajes, asignación de vehículo/chofer, calificación y cálculo de costos con extras (`sistema/ViajesSubSistema`).
- Aplicación Swing para registrarse/iniciar sesión, pedir viajes, pagar y seguir el estado.
- Simulación configurable: cantidad de hilos cliente/chofer, viajes máximos y tamaño de flota.
- Persistencia y reanudación vía XML/serialización (`Empresa.xml`, `Parametros.xml`).

## Diseño y Patrones
- `Singleton` + `Facade`: `sistema.Empresa` expone ambos subsistemas detrás de una única fachada.
- `Factory` y `Template Method`: `vehiculos.VehiculoFactory` y jerarquía `Vehiculo` priorizan la flota; `viajes.ViajeFactory` crea viajes por zona y aplica extras.
- `Decorator`: `viajes.Mascota` y `viajes.Equipaje` suman sobrecostos al viaje.
- `MVC`: `vista` (Swing), `controladores` (listeners), `sistema`/`viajes`/`choferes`/`vehiculos`/`usuarios` (modelo).
- `Observer/Observable`: `simulacion.RecursoCompartido` notifica a observadores `Ojo*` para actualizar `VentanaGeneral` y `VentanaCliente`.
- Concurrencia con monitor (`RecursoCompartido` sincronizado + `wait/notifyAll`) y hilos `ClienteThread`, `ChoferThread`, `SistemaThread`.
- Persistencia con `DTO` + utilidades de mapeo (`Persistencia/UTIL*`) e `IPersistencia` (`PersistenciaXML`, `PersistenciaBIN`).

## Arquitectura y Tecnologías
- Java 8, Maven; empaquetado `jar`.
- UI Swing; `com.toedter:jcalendar` 1.4 para selección de fechas.
- Serialización XML/binaria con DTOs.
- Javadoc generado en `doc/`; diagrama de clases adicional en `Java Class Diagram.rar`.
- Informe completo: [Informe Subi que te llevo - Parte 2 .pdf](Informe%20Subi%20que%20te%20llevo%20-%20Parte%202%20.pdf).

## Requisitos Previos
- JDK 8+ y Maven 3.x.
- Git (opcional) para clonar.
- Entorno gráfico para mostrar ventanas Swing.

## Etapas de desarrollo del proyecto (Parte 1 y Parte 2)

Este documento describe cómo se construyó “Subí que te llevo” en dos etapas (dos repositorios) y cómo se organizaron las responsabilidades para completar el sistema de punta a punta. Cada etapa se enfocó en componentes distintos y, en conjunto, muestran el alcance completo del trabajo realizado.

## 1) Estructura / Etapas del proyecto (2 repos)
- **Parte 1:** https://github.com/i-ahumada/subi-que-te-llevo  
  Base del dominio: choferes, vehículos, viajes, empresa y excepciones. Primeras pruebas y documentación Javadoc.
- **Parte 2:** https://github.com/gian-magliotti/subi-que-te-llevo  
  Evolución: interfaz Swing completa, simulación concurrente, capa de persistencia con DTO y serialización BIN/XML, más documentación y entregables.

## 2) Participación durante el desarrollo

### Gian (alias `sttormzyy` en Parte 2)
- **Rol general:** Construyó el núcleo funcional y la interfaz/simulación que hacen usable el producto.
- **Aportes clave Parte 1:**  
  - Creó el modelo de viajes, vehículos y choferes (factories, decorators, zonas, pedidos).  
  - Completó la lógica de empresa y subsistemas con validaciones y flujos de negocio.  
  - Primeras pruebas de flujo para verificar operaciones principales.
- **Aportes clave Parte 2:**  
  - Implementó la UI Swing end-to-end (login, registro, panel de cliente, ventanas de simulación).  
  - Armó la simulación concurrente (hilos de choferes/clientes, recurso compartido, observadores).  
  - Integró controladores con la lógica previa y ajustó vistas/estilos para la entrega final.
- **Impacto global:** Aseguró que el dominio inicial cobre vida en una aplicación completa y navegable, conectando UI, simulación y lógica de negocio.

### Gianfranco
- **Rol general:** Lideró la capa de datos y la solidez del dominio: subsistemas, clonación profunda, persistencia/DTO y documentación.
- **Aportes clave Parte 1:**  
  - Estructuró y afinó los subsistemas (AdmSubSistema, Empresa, ViajesSubSistema) para que el modelo fuera consistente y extensible.  
  - Añadió clonación y correcciones de negocio en choferes/usuarios para evitar referencias compartidas.  
  - Generó Javadoc y documentación base, clarificando contratos y pre/postcondiciones.
- **Aportes clave Parte 2:**  
  - Diseñó la capa de persistencia con patrón DTO y serialización BIN/XML (IPPersistencia, DTO de choferes/vehículos/usuarios/sistema).  
  - Implementó utilidades de conversión y pruebas de lectura/escritura, habilitando guardar/cargar estado sin romper el dominio.  
  - Extendió la clonación profunda y corrigió cálculos clave (ej. sueldos de choferes) para asegurar consistencia.  
  - Elaboró README/README_ES y Javadoc completo, dejando el proyecto documentado para uso y mantenimiento.
- **Impacto global:** Hizo posible que la app sea persistible y confiable: los datos se clonan bien, se serializan en BIN/XML y las reglas de negocio (sueldos, relaciones) se mantienen coherentes. La documentación resultante facilita adopción y soporte.

### i-ahumada
- **Rol general:** Aportó flujos de viaje y contratos de métodos, y ayudó a integrar persistencia y ramas grandes.
- **Aportes clave Parte 1:**  
  - Implementó solicitudes/inicio/pago de viaje sobre Empresa y Viaje, sumando validaciones.  
  - Añadió pre/postcondiciones y asserts en choferes y subsistemas para reforzar reglas.  
  - Contribuyó a la documentación Javadoc de módulos principales.
- **Aportes clave Parte 2:**  
  - Apoyó la capa de persistencia con renombrado/orden de DTO y utilidades de fecha/tiempo.  
  - Ajustó clonación y sincronización en simulación, y realizó merges para alinear UI, concurrencia y persistencia.  
  - Incorporó material de entrega (informes/presentaciones).
- **Impacto global:** Fortaleció los flujos de negocio y la claridad contractual, y colaboró en que las piezas de datos y simulación quedaran alineadas al cierre.

## 3) Integración de las dos etapas (Parte 1 + Parte 2)
Ambos repos son complementarios: Parte 1 construye el dominio (viajes, vehículos, choferes, empresa, reglas) y Parte 2 lo lleva a producción con UI, simulación y persistencia. Evaluar el aporte total requiere ver los dos: Gianfranco trabajó más en bases y datos (Parte 1 y persistencia en Parte 2), Gian expandió hacia UI/simulación, e i-ahumada ayudó a cerrar flujos y merges. Juntos, cubren extremo a extremo: modelo sólido, datos persistibles y experiencia de usuario funcional.

## Instalación y Configuración
```bash
cd subi-que-te-llevo/subiQueTeLlevo
mvn clean test-compile   # descarga dependencias y compila main + test (donde está el lanzador)
```
- Ejemplos de datos persistidos en `Empresa.xml` y `Parametros.xml` en este directorio.

## Ejecución
- Opción Maven (incluye las clases de prueba con el main):
```bash
mvn -Dexec.mainClass=prueba.Prueba -Dexec.classpathScope=test exec:java
```
- Opción Java directa (Windows; usar `:` en vez de `;` en Linux/macOS):
```bash
java -cp "target/subiQueTeLlevo-1.0-SNAPSHOT.jar;target/test-classes;%USERPROFILE%\\.m2\\repository\\com\\toedter\\jcalendar\\1.4\\jcalendar-1.4.jar" prueba.Prueba
```
- En `MenuSimulacion`, elige iniciar simulación nueva (configurando cantidades) o cargar datos persistidos.

## Flujo Básico de Uso
- Configurar cantidad de clientes, tipos de chofer, viajes máximos y flota en `MenuSimulacion`; iniciar simulación nueva o cargar estado guardado.
- Observar en `VentanaGeneral` la actividad de hilos y asignaciones; usar `VentanaCliente` para registrarse/iniciar sesión.
- Desde la app de cliente: solicitar viaje (zona, distancia, mascota/equipaje), esperar asignación, pagar y calificar.
- Los hilos de chofer avanzan viajes hasta finalizarlos; el hilo de sistema asigna el mejor vehículo disponible.

## Estructura del Repositorio
- `subiQueTeLlevo/pom.xml`: configuración Maven y dependencia `jcalendar`.
- `src/main/java`: lógica de dominio (`sistema`, `viajes`, `choferes`, `vehiculos`, `usuarios`), persistencia (`Persistencia`), vistas Swing (`vista`), controladores y simulación (`simulacion`).
- `src/main/resources`: imágenes de la UI y `libs/jcalendar-1.4.jar`.
- `src/test/java/prueba/Prueba.java`: punto de entrada para lanzar la simulación.
- `doc/`: Javadoc generado; `Empresa.xml` y `Parametros.xml`: datos de ejemplo persistidos.
- `target/`: artefactos de build; `MavenApp/`: metadatos de IDE.
- PDFs en la raíz: informe detallado ([Informe Subi que te llevo - Parte 2 .pdf](Informe%20Subi%20que%20te%20llevo%20-%20Parte%202%20.pdf)) y presentación ([Presentacion Subi que te llevo - Parte 2.pdf](Presentacion%20Subi%20que%20te%20llevo%20-%20Parte%202.pdf)).

## Trabajo Futuro
- Mover el lanzador (`Prueba`) a `src/main` y declarar `Main-Class` en el `jar` para simplificar la ejecución.
- Agregar pruebas automatizadas para reglas de negocio y sincronización.
- Más validación en la UI (rangos numéricos, manejo de errores de concurrencia).
- Extender tipos de flota/viajes aprovechando fábricas y decoradores; modernizar la UI Swing o migrar a un toolkit actual.
- Ofrecer persistencia en base de datos o servicio externo además de XML/binario.

## Autores
- Ahumada, Iván 
- Magliotti, Gian Franco 
- San Pedro, Gianfranco 
