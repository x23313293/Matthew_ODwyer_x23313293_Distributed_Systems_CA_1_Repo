Classroom Automation/Simulation System:

This is my java implementation for my proposed classroom simulation or automation system for this CA 1 - Distributed Systems.

I have decided to use these tools to implement this task: 

Java, gRPC, and JavaFX

---

/*Table of Contents

1. [Overview](#overview)  
2. [Motivation & Scope](#motivation--scope)  
3. [Architecture & Modules](#architecture--modules)  
4. [Prerequisites](#prerequisites)  
5. [Getting Started: Build & Run](#getting-started-build--run)  
   1. [Build the project](#build-the-project)  
   2. [Start services](#start-services)  
   3. [Launch the GUI client](#launch-the-gui-client)  
6. [Using the System](#using-the-system)  
7. [Project Structure Overview](#project-structure-overview)  
8. [Testing](#testing)  
9. [Troubleshooting](#troubleshooting)  
10. [Contributing](#contributing)  
11. [License](#license)  

---

/* Overview

This **multi-module Maven project** simulates a smart classroom environment via three gRPC‑based backend services (SmartBoard, StudentTracker, EnvironmentControl) and a JavaFX GUI controller. All modules are defined in the root `pom.xml` and follow Maven's standard structure for predictable builds and packaging :contentReference[oaicite:2]{index=2}.

---

/* Motivation & Scope

Quality education (SDG 4) is challenged by remote or hybrid learning. This project demonstrates:

- Simulated real-time teaching and lesson control
- Attendance & engagement tracking
- Environmental automation (lighting/temperature)
- Service discovery to bind all components via gRPC

This setup is ideal for educational research, prototype demos, or experiments in microservice/service orchestration.

---

/* Architecture & Modules

- **proto** – `.proto` files for all three services  
- **discovery** – JAR with `ServiceRegistry` for service registration/discovery  
- **services/SmartBoardService**  
- **services/StudentTrackerService**  
- **services/EnvironmentControlService** – each implements a service and launches a gRPC server  
- **central-gui** – JavaFX-based client; discovers active services and allows interactive control  

Built with:

- Java 17
- Maven (multi-module)
- gRPC v\<version\> (via `grpc-bom`)
- JavaFX 21 (via `javafx-maven-plugin`)  
Shared configurations (artifact versions, plugins) are defined in the root `pom.xml` for consistency across modules :contentReference[oaicite:3]{index=3}.

---

/* Prerequisites

- JDK 17 
- Maven 3.6+
- `PATH` configured to run `java`, `javac`, and `mvn`

---

/* Getting Started: Build & Run

/* Build the project

```bash
cd classroom-simulation/
mvn clean install
