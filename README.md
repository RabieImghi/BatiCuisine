[<img src="https://img.icons8.com/?size=512&id=55494&format=png" align="right" width="25%" style="padding-right: 350px">]()

# **BatiCuisine**

<p align="left">
	<em>Kitchen Renovation Cost Estimation Application</em>
</p>

<p align="center">
	<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white" alt="java">
	<img src="https://img.shields.io/badge/postgresql-%23316192.svg?style=flat&logo=postgresql&logoColor=white" alt="postgresql">
	<img src="https://img.shields.io/badge/jdbc-%23000000.svg?style=flat&logoColor=white" alt="jdbc">
</p>

---

## **Table of Contents**

- [Overview](#overview)
- [Features](#features)
- [Repository Structure](#repository-structure)
- [Modules](#modules)
- [How to Run](#how-to-run)
- [Future Enhancements](#future-enhancements)

---

## **Overview**

BatiCuisine is a Java-based application designed to provide accurate cost estimations for kitchen renovation projects. It streamlines project management by accounting for materials, labor, and other associated expenses, while also offering client management and customizable quote generation.

Key features include:
- Managing clients and projects.
- Tracking material and labor costs.
- Generating detailed quotes.
- Accounting for taxes, discounts, and profit margins.

---

## **Features**

### **1. Project Management**
- Track kitchen renovation projects with a focus on material and labor costs.
- Key attributes:
  - `projectName`: Project's name.
  - `profitMargin`: Applied profit margin.
  - `totalCost`: Overall project cost.
  - `projectStatus`: Project status (Ongoing, Completed, etc.).

### **2. Component Management**
- **Materials:**
  - Track unit cost, quantity, transportation cost, and tax rate.
- **Labor:**
  - Calculate labor costs based on hourly rate, hours worked, and worker productivity.
  
### **3. Client Management**
- Handle professional and individual clients, applying appropriate discounts.
- Attributes include `name`, `address`, `phone`, and `isProfessional`.

### **4. Quote Creation**
- Generate estimates for materials and labor costs with an acceptance feature.
- Quote attributes:
  - `estimatedAmount`: Total estimated amount.
  - `issueDate`: Quote's issue date.
  - `validityDate`: Date until the quote is valid.
  - `isAccepted`: Acceptance status of the quote.

### **5. Cost Calculation**
- Integrate component costs, apply taxes and discounts, and adjust for material quality or worker productivity.

### **6. Display Details and Results**
- Present detailed project summaries, including client information, costs, taxes, and profit margins.

---

## **Repository Structure**

```sh
└── BatiCuisine/
    ├── BatiCuisine.iml
    └── src
        ├── MANIFEST.MF
        ├── Main.java
        ├── config
        ├── controller
        ├── domain
        ├── repository
        ├── service
        └── utils
```

##  Modules

<details closed><summary>src</summary>

| File | Summary |
| --- | --- |
| [Main.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/Main.java) | <code>❯ Entry point for the application, initializes and runs the main program.</code> |
| [MANIFEST.MF](https://github.com/RabieImghi/BatiCuisine/blob/main/src/MANIFEST.MF) | <code>❯ Manifest file containing metadata about the project, like the main class and classpath.</code> |

</details>

<details closed><summary>src.repository</summary>

| File | Summary |
| --- | --- |
| [ProjectRepository.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/ProjectRepository.java) | <code>❯ Interface for project data operations such as saving, updating, and fetching project details.</code> |
| [MaterialRepository.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/MaterialRepository.java) | <code>❯ Interface for managing material-related data for kitchen renovation projects.</code> |
| [ClientRepository.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/ClientRepository.java) | <code>❯ Interface for handling client data, including saving and retrieving client details.</code> |
| [QuoteRepository.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/QuoteRepository.java) | <code>❯ Interface for managing quotes related to projects and clients.</code> |
| [LaborRepository.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/LaborRepository.java) | <code>❯ Interface for tracking labor data, including cost calculations and worker hours.</code> |

</details>

<details closed><summary>src.repository.impl</summary>

| File | Summary |
| --- | --- |
| [ClientRepositoryImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/impl/ClientRepositoryImpl.java) | <code>❯ Implementation of client repository for saving and retrieving client information.</code> |
| [ProjectRepositoryImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/impl/ProjectRepositoryImpl.java) | <code>❯ Implementation of project repository for performing CRUD operations on project data.</code> |
| [QuoteRepositoryImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/impl/QuoteRepositoryImpl.java) | <code>❯ Implementation for managing quote data related to kitchen renovation projects.</code> |
| [LaborRepositoryImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/impl/LaborRepositoryImpl.java) | <code>❯ Implementation of labor repository for storing and retrieving labor cost and productivity information.</code> |
| [MaterialRepositoryImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/impl/MaterialRepositoryImpl.java) | <code>❯ Implementation of material repository for handling material costs, quantities, and taxes.</code> |

</details>

<details closed><summary>src.controller</summary>

| File | Summary |
| --- | --- |
| [ClientController.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/controller/ClientController.java) | <code>❯ Controller for managing client-related HTTP requests and operations.</code> |
| [ProjectController.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/controller/ProjectController.java) | <code>❯ Controller for handling project management operations and requests.</code> |
| [QuoteController.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/controller/QuoteController.java) | <code>❯ Controller for creating, updating, and retrieving project quotes.</code> |
| [MaterialController.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/controller/MaterialController.java) | <code>❯ Controller for managing material data in projects such as costs and availability.</code> |
| [LaborController.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/controller/LaborController.java) | <code>❯ Controller for managing labor operations, including hours worked and cost tracking.</code> |

</details>

<details closed><summary>src.domain</summary>

| File | Summary |
| --- | --- |
| [Component.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/domain/Component.java) | <code>❯ Abstract base class representing a project component, extended by Material and Labor classes.</code> |
| [Material.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/domain/Material.java) | <code>❯ Domain model for representing materials in kitchen renovation projects, including cost and quantity.</code> |
| [Client.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/domain/Client.java) | <code>❯ Domain model representing a client, including attributes like name, address, and contact details.</code> |
| [Labor.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/domain/Labor.java) | <code>❯ Domain model representing labor costs, hours worked, and worker efficiency in projects.</code> |
| [Project.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/domain/Project.java) | <code>❯ Domain model for managing kitchen renovation projects, tracking costs and progress.</code> |
| [Quote.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/domain/Quote.java) | <code>❯ Domain model for creating and managing quotes for projects, including cost breakdowns.</code> |

</details>

<details closed><summary>src.utils</summary>

| File | Summary |
| --- | --- |
| [ProjectStatus.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/utils/ProjectStatus.java) | <code>❯ Enum representing various statuses a project can have (e.g., Ongoing, Completed).</code> |
| [Menu.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/utils/Menu.java) | <code>❯ Utility class for displaying CLI-based menus and options to users.</code> |
| [ComponentType.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/utils/ComponentType.java) | <code>❯ Enum differentiating between types of project components, such as Material or Labor.</code> |

</details>

<details closed><summary>src.config</summary>

| File | Summary |
| --- | --- |
| [DatabaseConnection.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/config/DatabaseConnection.java) | <code>❯ Handles establishing and closing database connections using JDBC.</code> |

</details>

<details closed><summary>src.service</summary>

| File | Summary |
| --- | --- |
| [ProjectService.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/ProjectService.java) | <code>❯ Interface for project-related business logic such as cost estimation and status updates.</code> |
| [QuoteService.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/QuoteService.java) | <code>❯ Interface for managing the business logic behind creating and updating project quotes.</code> |
| [LaborService.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/LaborService.java) | <code>❯ Interface for managing labor-related logic, such as calculating labor costs and productivity.</code> |
| [MaterialService.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/MaterialService.java) | <code>❯ Interface for managing material-related logic, including cost and quality adjustments.</code> |
| [ClientService.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/ClientService.java) | <code>❯ Interface for handling client-related operations such as discounts and information management.</code> |

</details>

<details closed><summary>src.service.impl</summary>

| File | Summary |
| --- | --- |
| [MaterialServiceImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/impl/MaterialServiceImpl.java) | <code>❯ Implementation of material service for managing materials, costs, and inventory tracking.</code> |
| [ProjectServiceImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/impl/ProjectServiceImpl.java) | <code>❯ Implementation of project service, managing overall project flow and cost calculations.</code> |
| [LaborServiceImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/impl/LaborServiceImpl.java) | <code>❯ Implementation of labor service for managing labor costs and productivity metrics.</code> |
| [ClientServiceImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/impl/ClientServiceImpl.java) | <code>❯ Implementation of client service for handling client data and applying discounts.</code> |
| [QuoteServiceImpl.java](https://github.com/RabieImghi/BatiCuisine

