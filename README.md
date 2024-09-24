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
| [Main.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/Main.java) | <code>❯ REPLACE-ME</code> |
| [MANIFEST.MF](https://github.com/RabieImghi/BatiCuisine/blob/main/src/MANIFEST.MF) | <code>❯ REPLACE-ME</code> |

</details>

<details closed><summary>src.repository</summary>

| File | Summary |
| --- | --- |
| [ProjectRepository.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/ProjectRepository.java) | <code>❯ REPLACE-ME</code> |
| [MaterialRepository.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/MaterialRepository.java) | <code>❯ REPLACE-ME</code> |
| [ClientRepository.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/ClientRepository.java) | <code>❯ REPLACE-ME</code> |
| [QuoteRepository.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/QuoteRepository.java) | <code>❯ REPLACE-ME</code> |
| [LaborRepository.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/LaborRepository.java) | <code>❯ REPLACE-ME</code> |

</details>

<details closed><summary>src.repository.impl</summary>

| File | Summary |
| --- | --- |
| [ClientRepositoryImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/impl/ClientRepositoryImpl.java) | <code>❯ REPLACE-ME</code> |
| [ProjectRepositoryImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/impl/ProjectRepositoryImpl.java) | <code>❯ REPLACE-ME</code> |
| [QuoteRepositoryImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/impl/QuoteRepositoryImpl.java) | <code>❯ REPLACE-ME</code> |
| [LaborRepositoryImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/impl/LaborRepositoryImpl.java) | <code>❯ REPLACE-ME</code> |
| [MaterialRepositoryImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/repository/impl/MaterialRepositoryImpl.java) | <code>❯ REPLACE-ME</code> |

</details>

<details closed><summary>src.controller</summary>

| File | Summary |
| --- | --- |
| [ClientController.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/controller/ClientController.java) | <code>❯ REPLACE-ME</code> |
| [ProjectController.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/controller/ProjectController.java) | <code>❯ REPLACE-ME</code> |
| [QuoteController.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/controller/QuoteController.java) | <code>❯ REPLACE-ME</code> |
| [MaterialController.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/controller/MaterialController.java) | <code>❯ REPLACE-ME</code> |
| [LaborController.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/controller/LaborController.java) | <code>❯ REPLACE-ME</code> |

</details>

<details closed><summary>src.domain</summary>

| File | Summary |
| --- | --- |
| [Component.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/domain/Component.java) | <code>❯ REPLACE-ME</code> |
| [Material.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/domain/Material.java) | <code>❯ REPLACE-ME</code> |
| [Client.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/domain/Client.java) | <code>❯ REPLACE-ME</code> |
| [Labor.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/domain/Labor.java) | <code>❯ REPLACE-ME</code> |
| [Project.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/domain/Project.java) | <code>❯ REPLACE-ME</code> |
| [Quote.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/domain/Quote.java) | <code>❯ REPLACE-ME</code> |

</details>

<details closed><summary>src.utils</summary>

| File | Summary |
| --- | --- |
| [ProjectStatus.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/utils/ProjectStatus.java) | <code>❯ REPLACE-ME</code> |
| [Menu.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/utils/Menu.java) | <code>❯ REPLACE-ME</code> |
| [ComponentType.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/utils/ComponentType.java) | <code>❯ REPLACE-ME</code> |

</details>

<details closed><summary>src.config</summary>

| File | Summary |
| --- | --- |
| [DatabaseConnection.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/config/DatabaseConnection.java) | <code>❯ REPLACE-ME</code> |

</details>

<details closed><summary>src.service</summary>

| File | Summary |
| --- | --- |
| [ProjectService.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/ProjectService.java) | <code>❯ REPLACE-ME</code> |
| [QuoteService.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/QuoteService.java) | <code>❯ REPLACE-ME</code> |
| [LaborService.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/LaborService.java) | <code>❯ REPLACE-ME</code> |
| [MaterialService.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/MaterialService.java) | <code>❯ REPLACE-ME</code> |
| [ClientService.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/ClientService.java) | <code>❯ REPLACE-ME</code> |

</details>

<details closed><summary>src.service.impl</summary>

| File | Summary |
| --- | --- |
| [MaterialServiceImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/impl/MaterialServiceImpl.java) | <code>❯ REPLACE-ME</code> |
| [ProjectServiceImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/impl/ProjectServiceImpl.java) | <code>❯ REPLACE-ME</code> |
| [LaborServiceImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/impl/LaborServiceImpl.java) | <code>❯ REPLACE-ME</code> |
| [ClientServiceImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/impl/ClientServiceImpl.java) | <code>❯ REPLACE-ME</code> |
| [QuoteServiceImpl.java](https://github.com/RabieImghi/BatiCuisine/blob/main/src/service/impl/QuoteServiceImpl.java) | <code>❯ REPLACE-ME</code> |

</details>

