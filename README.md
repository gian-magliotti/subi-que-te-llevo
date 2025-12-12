# Subí Que Te Llevo

Spanish version: [README_ES.md](README_ES.md)

Academic Java ride-hailing simulator with Swing UI, design patterns, concurrency, and persistence applied across the travel lifecycle.

## Academic context and system overview
- Final Project (part two) for "Programación III" at the School of Engineering (UNMDP), delivered on 2024-06-09.
- Goal: extend the first delivery by adding threads, synchronization, MVC, and serialization.
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
- Full project report: [Informe Subi que te llevo - Parte 2 .pdf](Informe%20Subi%20que%20te%20llevo%20-%20Parte%202%20.pdf).

## Prerequisites
- JDK 8+ and Maven 3.x.
- Git (optional) to clone.
- Graphical environment to display Swing windows.

## Project development stages (Part 1 and Part 2)

This document describes how "Subí que te llevo" was built in two stages (two repositories) and how responsibilities were organized to complete the end-to-end system. Each stage focused on different components and, together, they show the full scope of the work delivered.

## 1) Structure / Project stages (2 repos)
- **Part 1:** https://github.com/i-ahumada/subi-que-te-llevo  
  Domain foundation: drivers, vehicles, rides, company, and exceptions. Early tests and Javadoc documentation.
- **Part 2:** https://github.com/gian-magliotti/subi-que-te-llevo  
  Evolution: full Swing interface, concurrent simulation, persistence layer with DTO and BIN/XML serialization, more documentation and deliverables.

## 2) Participation during development

### Gian (aka `sttormzyy` in Part 2)
- **General role:** Built the functional core and the UI/simulation that make the product usable.
- **Key contributions Part 1:**  
  - Built the ride, vehicle, and driver model (factories, decorators, zones, requests).  
  - Completed company and subsystem logic with validations and business flows.  
  - Initial flow tests to verify main operations.
- **Key contributions Part 2:**  
  - Implemented the Swing UI end-to-end (login, signup, client panel, simulation windows).  
  - Built the concurrent simulation (driver/client threads, shared resource, observers).  
  - Integrated controllers with the existing logic and fine-tuned views/styles for the final delivery.
- **Overall impact:** Ensured the initial domain comes to life in a complete, navigable app, connecting UI, simulation, and business logic.

### Gianfranco
- **General role:** Led the data layer and domain robustness: subsystems, deep cloning, persistence/DTO, and documentation.
- **Key contributions Part 1:**  
  - Structured and refined the subsystems (AdmSubSistema, Empresa, ViajesSubSistema) so the model is consistent and extensible.  
  - Added cloning and business fixes in drivers/users to avoid shared references.  
  - Produced Javadoc and base documentation, clarifying contracts and pre/postconditions.
- **Key contributions Part 2:**  
  - Designed the persistence layer with the DTO pattern and BIN/XML serialization (IPPersistencia, DTOs for drivers/vehicles/users/system).  
  - Implemented conversion utilities and read/write tests, enabling save/load without breaking the domain.  
  - Extended deep cloning and corrected key calculations (e.g., driver salaries) to ensure consistency.  
  - Prepared README/README_ES and full Javadoc, leaving the project documented for use and maintenance.
- **Overall impact:** Made the app persistable and reliable: data clones correctly, serializes to BIN/XML, and business rules (salaries, relationships) stay coherent. The resulting documentation eases adoption and support.

### i-ahumada
- **General role:** Contributed ride flows and method contracts, and helped integrate persistence and large branches.
- **Key contributions Part 1:**  
  - Implemented ride request/start/payment on Empresa and Viaje, adding validations.  
  - Added pre/postconditions and asserts in drivers and subsystems to reinforce rules.  
  - Contributed to Javadoc documentation for the main modules.
- **Key contributions Part 2:**  
  - Supported the persistence layer with DTO renaming/ordering and date/time utilities.  
  - Adjusted cloning and synchronization in the simulation, and performed merges to align UI, concurrency, and persistence.  
  - Added delivery materials (reports/presentations).
- **Overall impact:** Strengthened business flows and contractual clarity, and helped keep the data and simulation pieces aligned at closure.

## 3) Integration of the two stages (Part 1 + Part 2)
Both repos are complementary: Part 1 builds the domain (rides, vehicles, drivers, company, rules) and Part 2 takes it to production with UI, simulation, and persistence. Assessing the total contribution requires looking at both: Gianfranco worked more on foundations and data (Part 1 and persistence in Part 2), Gian expanded into UI/simulation, and i-ahumada helped close flows and merges. Together, they cover end-to-end: solid model, persistable data, and a functional user experience.

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
- Root PDFs: detailed report ([Informe Subi que te llevo - Parte 2 .pdf](Informe%20Subi%20que%20te%20llevo%20-%20Parte%202%20.pdf)) and slide deck ([Presentacion Subi que te llevo - Parte 2.pdf](Presentacion%20Subi%20que%20te%20llevo%20-%20Parte%202.pdf)).

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
