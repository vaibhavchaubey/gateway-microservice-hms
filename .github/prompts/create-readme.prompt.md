---
agent: 'agent'
description: 'Create a professional, GitHub-standard README.md file for the project'
---

# 📘 README Generation Prompt (GitHub Standard + Professional)

## 🧑‍💻 Role

You are a **senior software engineer and experienced open-source contributor**.

You specialize in writing **clean, professional, and GitHub-standard README.md files** that are:
- Easy to scan
- Technically accurate
- Visually appealing
- Useful for both users and contributors

---

## 🎯 Task

1. Analyze the provided project details carefully
2. Generate a **complete, production-quality README.md**

---

## 📌 Project Details

- **Project Name:** <PROJECT_NAME>
- **Description:** <SHORT_DESCRIPTION>
- **Tech Stack:** <TECH_STACK (include ALL technologies used)>
- **Type:** <WEB APP / API / MOBILE APP / CLI TOOL / LIBRARY>
- **Deployment URL:** <LIVE_LINK>
- **Repository URL:** <REPO_LINK>

---

## 🧾 Required Sections

### 1. 🏷️ Title + Tech Badges

- Display project name as main heading
- Add **shields.io badges for ALL technologies**
- Include:
  - Language
  - Framework
  - Database
  - Tools (Docker, AWS, etc.)

✅ Example:

[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.java.net/)  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)  
[![MySQL](https://img.shields.io/badge/MySQL-8-blue)](https://www.mysql.com/)  
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)  

👉 Dynamically generate badges from tech stack.

---

### 2. 🚀 What the Project Does

- Clear explanation (4–6 lines)
- Cover:
  - Purpose
  - Problem solved
  - Real-world usage
  - If relevant: scalability/performance

---

### 3. 💡 Why the Project is Useful

- Key benefits
- Practical use cases
- What makes it valuable

---

### 4. ✨ Key Features

- Strong bullet points using action verbs:
  - Designed
  - Built
  - Implemented
  - Optimized

---

### 5. 🛠️ Tech Stack

Categorize:

- **Frontend**
- **Backend**
- **Database**
- **DevOps / Tools**

---

### 6. ⚙️ Getting Started (Installation & Setup)

```bash id="setup-snippet"
git clone <repo_url>
cd <project_name>