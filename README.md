# 🧾 Sistema de Denuncias contra la Violencia hacia la Mujer
Aplicación desarrollada en Java + Spring Boot bajo el enfoque de Domain-Driven Design (DDD), para registrar, gestionar y dar seguimiento a denuncias de violencia, garantizando trazabilidad y eficiencia en la atención de casos.

## 🧠 Descripción General
Este proyecto busca digitalizar el proceso de atención a denuncias de violencia contra la mujer.
Permite registrar denuncias, asignarlas a centros de apoyo, y realizar un seguimiento de su estado.
El sistema está dividido en distintos bounded contexts, aplicando los principios de DDD (Domain-Driven Design) y arquitectura hexagonal.

## ✨ Características principales

- Registro de usuarios (víctimas y administradores)
- Creación y seguimiento de denuncias.
- Adjuntar evidencias (archivos, imágenes, etc.)
- Gestión del estado de las denuncias.
- Registro de agresores y relación con la víctima.
- Centro de apoyo con su información de contacto.
- Estadísticas de denuncias por estado y rango de fechas.

## 🧱 Arquitectura
El proyecto sigue un enfoque basado en **Domain-Driven Design (DDD)**:
- **Domain Layer:** Modelos del dominio (Aggregate Roots, Entities, Value Objects)
- **Application Layer:** Casos de uso (Use Cases)
- **Infrastructure Layer:** Repositorios, configuración de persistencia y servicios externos
- **Interface Layer:** Controladores REST

## 🛠️ Tecnologías utilizadas
- **Java 17**
- **Spring Boot 3**
- **Maven**
- **JPA / Hibernate**
- **MySQL**
- **Spring Security - JWT**
- **MapStruct** (para mapeos DTO)
- **Jakarta Validation** (para validaciones)
- **Brevo API (Sendinblue)** para envío de correos

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

## 🚀 Instalación y configuración
1. Clonar el repositorio:
```bash
git clone https://github.com/usuario/complaint-system.git
```
2. Clonar el repositorio:
```sql
CREATE DATABASE complaint_system;
```
3. Configurar las credenciales en `application.yml`:
```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/complaint_system
    username: tu_username
    password: tu_contraseña
```
4. (Opcional) Configurar tu API Key de Brevo:
```yml
spring:
brevo:
  api-key: tu_api_key
  sender: noreply@dominio.com
```
5. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```
## 📡 Endpoints principales

| Módulo | Método | Endpoint | Descripción |
|---------|---------|-----------|---------------|
| AUTH | POST | /auth/register | Registrar nuevo usuario |
| AUTH | POST | /auth/login  | Iniciar sesión  |
| USER | GET | /users/profile | Ver mis datos del perfil  |
| USER | PUT | /users/{id} | Cambiar mis datos de perfil|
| USER | POST | /users/admin  | Registrar nuevo administrador |
| COMPLAINT | POST | /complaints  | Crear una denuncia |
| COMPLAINT| GET | /complaints/my-complaints | Ver mis denuncias |
| COMPLAINT | GET | /complaints/{id}  | Ver mi denuncia por id (victima - admin) |
| COMPLAINT | PATCH | /complaints/{id}/status | Actualizar el estado de una denuncia (admin) |
| COMPLAINT | POST | /complaints/{id}/evidence | Adjuntar una evidencia |
| SUPPORT | GET |/support-centers | Ver todos los Centros de Apoyo|
| SUPPORT | GET | /support-centers/recommendations/{district} | Ver centros de apoyo por Distrito |
| ANALYTICS | GET |/analytics/complaints-by-date | Metríca de denuncias entre un rango de fecha (admin)|
| ANALYTICS | GET |/analytics/complaints-by-type | Metríca de denuncias por tipo de violencia (admin) |
| ANALYTICS | GET |/analytics/complaints-by-status | Metríca de denuncias por estado (admin)|
