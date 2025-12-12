# Subí Que Te Llevo

Versión en inglés: [README.md](README.md)

Simulador académico de un servicio de remises en Java, con interfaz Swing y énfasis en patrones de diseño, concurrencia y persistencia a lo largo del ciclo de viaje.

## Contexto Académico
- Trabajo Práctico Final (segunda parte) de Programación III, Facultad de Ingeniería (UNMDP), presentado el 09/06/2024.
- Objetivo: extender la primera entrega incorporando hilos, sincronización, MVC y serialización.

## Descripción General
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
