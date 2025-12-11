# Subí Que Te Llevo

Spanish version: [README_ES.md](README_ES.md)

Academic Java ride-hailing simulator with Swing UI, design patterns, concurrency, and persistence applied across the travel lifecycle.

## Academic Context
- Final Project (part two) for "Programación III" at the School of Engineering (UNMDP), delivered on 2024-06-09.
- Goal: extend the first delivery by adding threads, synchronization, MVC, and serialization.

## System Overview
- Models a taxi/remise company managing clients, drivers, and vehicles, resolving ride requests with resource assignment and pricing.
- Provides a multi-threaded simulation (clients, drivers, system) and views that show live trip/resource state.

## What the application does
- Simulates a full ride-hailing operation: register clients, onboard drivers and vehicles to build the fleet.
- Clients sign in via the Swing app, request trips by zone, distance, pet/luggage needs, and passengers; the system validates each request and assigns the best vehicle based on model rules.
- Orders and driver threads run in parallel: each driver picks up, charges, and finishes trips while the views show live assignments and statuses.
- The simulation keeps history of trips, drivers, and vehicles for follow-up and reporting within the academic scenario.

## Key Features
- CRUD, listings, and reports for users, drivers, and vehicles (`sistema/AdmSubSistema`).
- Request validation, trip creation, vehicle/driver assignment, rating, and cost calculation with extras (`sistema/ViajesSubSistema`).
- Swing client app to sign up/sign in, request rides, pay, and follow status.
- Configurable simulation: number of client/driver threads, max trips, and fleet size.
- Persistence and resume via XML/serialization (`Empresa.xml`, `Parametros.xml`).

## Design and Patterns
- `Singleton` + `Facade`: `sistema.Empresa` exposes both subsystems behind one gateway.
- `Factory` and `Template Method`: `vehiculos.VehiculoFactory` plus `Vehiculo` hierarchy for fleet priority; `viajes.ViajeFactory` builds trips by zone and applies extras.
- `Decorator`: `viajes.Mascota` and `viajes.Equipaje` add surcharges to trips.
- `MVC`: `vista` (Swing), `controladores` (listeners), `sistema`/`viajes`/`choferes`/`vehiculos`/`usuarios` (model).
- `Observer/Observable`: `simulacion.RecursoCompartido` notifies `Ojo*` observers to refresh `VentanaGeneral` and `VentanaCliente`.
- Concurrency with a monitor (`RecursoCompartido` synchronized + `wait/notifyAll`) and threads `ClienteThread`, `ChoferThread`, `SistemaThread`.
- Persistence with `DTO` + mapping helpers (`Persistencia/UTIL*`) and `IPersistencia` (`PersistenciaXML`, `PersistenciaBIN`).

## Architecture and Technologies
- Java 8, Maven; packaged as `jar`.
- Swing UI; `com.toedter:jcalendar` 1.4 for date picking.
- XML/binary serialization with DTOs.
- Generated Javadoc in `doc/`; extra class diagram in `Java Class Diagram.rar`.

## Prerequisites
- JDK 8+ and Maven 3.x.
- Git (optional) to clone.
- Graphical environment to display Swing windows.

## Installation and Setup
```bash
cd subi-que-te-llevo/subiQueTeLlevo
mvn clean test-compile   # pulls dependencies and compiles main + test (where the launcher lives)
```
- Sample persisted data lives in `Empresa.xml` and `Parametros.xml` in this folder.

## Running
- Maven option (includes test classes containing the launcher):
```bash
mvn -Dexec.mainClass=prueba.Prueba -Dexec.classpathScope=test exec:java
```
- Plain Java option (Windows; swap `;` for `:` on Linux/macOS):
```bash
java -cp "target/subiQueTeLlevo-1.0-SNAPSHOT.jar;target/test-classes;%USERPROFILE%\\.m2\\repository\\com\\toedter\\jcalendar\\1.4\\jcalendar-1.4.jar" prueba.Prueba
```
- In `MenuSimulacion`, choose a fresh simulation (set counts) or load persisted data.

## Basic Usage Flow
- Configure number of clients, driver types, max trips, and fleet in `MenuSimulacion`; start new simulation or load saved state.
- Watch `VentanaGeneral` for thread activity and assignments; use `VentanaCliente` to register/log in.
- From the client app: request a trip (zone, distance, pet/luggage), wait for assignment, pay, and rate.
- Driver threads progress trips until completion; the system thread assigns the best available vehicle.

## Repository Structure
- `subiQueTeLlevo/pom.xml`: Maven setup and `jcalendar` dependency.
- `src/main/java`: domain logic (`sistema`, `viajes`, `choferes`, `vehiculos`, `usuarios`), persistence (`Persistencia`), Swing views (`vista`), controllers, and simulation (`simulacion`).
- `src/main/resources`: UI images and `libs/jcalendar-1.4.jar`.
- `src/test/java/prueba/Prueba.java`: entry point to launch the simulation.
- `doc/`: generated Javadoc; `Empresa.xml` and `Parametros.xml`: sample persisted data.
- `target/`: build artifacts; `MavenApp/`: IDE metadata.

## Future Work
- Move the launcher (`Prueba`) into `src/main` and declare `Main-Class` in the `jar` for smoother runs.
- Add automated tests for business rules and synchronization.
- More UI validation (numeric ranges, concurrency error handling).
- Extend fleet/trip types using the existing factories/decorators; modernize Swing UI or migrate to a newer toolkit.
- Offer database or external service persistence beyond XML/binary.

## Authors
- Ahumada, Iván 
- Magliotti, Gian Franco 
- San Pedro, Gianfranco
