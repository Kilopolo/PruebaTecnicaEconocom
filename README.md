## 🚀 Prueba Técnica Full-Stack - Econocom 2025
Esta prueba tiene como objetivo evaluar las habilidades técnicas de un candidato en el desarrollo full-stack utilizando Spring Boot para el backend y Angular para el frontend. La prueba se centrará en la capacidad de diseñar, implementar y conectar ambas partes para generar una interfaz de inicio de sesión funcional y visualmente similar al diseño proporcionado.

### 🧪 Descripción

Este proyecto es una prueba técnica full-stack que demuestra la integración entre un backend en **Spring Boot** y un frontend en **Angular** con **Material Design**. Implementa un sistema de **autenticación por JWT** y una simulación de **inicio de sesión con SSO** (Single Sign-On), siguiendo el diseño proporcionado.

---

## 📁 Estructura del Proyecto

```
technical-challenge/
├── backendPruebaTecnica/           # Proyecto Spring Boot 
└── frontend/          # Proyecto Angular 
```

---

## 🛠️ Requisitos Técnicos

### ✅ Backend (Spring Boot)

* Java 1.8+
* Spring Boot 3+
* Spring Web
* Spring Security (con JWT)
* Simulación de SSO (redirección + callback)

### ✅ Frontend (Angular)

* Angular 17+
* Angular Material
* Formularios con validación reactiva
* Interfaz de login + flujo SSO
* Estilos con BEM + librería CSS proporcionada

---

## 🧑‍💻 Instalación y Ejecución

### 🔙 Backend

```bash
cd backend
./mvnw spring-boot:run
```

El backend se ejecutará por defecto en:
📍 `http://localhost:8080`

---

### 🔜 Frontend

```bash
cd frontend
npm install
ng serve
```

El frontend se ejecutará por defecto en:
📍 `http://localhost:4200`

---

## 🔐 Funcionalidades

### Login Tradicional

* Validación de formulario (campos requeridos, formato email)
* Petición POST a `/api/auth/login`
* Manejo de token JWT
* Almacenamiento en `localStorage`
* Redirección al dashboard

### Login con SSO

* Simulación de proveedor SSO
* Botón "Iniciar sesión con SSO"
* Redirección a endpoint SSO simulado
* Callback `/sso-callback?code=...`
* Recepción y validación del código en backend
* Respuesta con token simulado

---

## 📸 Diseño Visual

* Interfaz basada en el diseño de Figma (ver carpeta assets)
* Componentes Material UI (`MatFormField`, `MatButton`, `MatInput`, `MatError`)
* Layout responsivo y estilizado con la librería CSS proporcionada

---

## 🧪 Endpoints Backend

| Método | Ruta                     | Descripción                       |
| ------ | ------------------------ | --------------------------------- |
| POST   | `/api/auth/login`        | Login con usuario y contraseña    |
| GET    | `/api/auth/sso`          | Redirige a proveedor SSO simulado |
| GET    | `/api/auth/sso/callback` | Valida el código recibido de SSO  |

---

## 🌐 CORS

El backend está configurado para permitir peticiones desde el frontend local (`http://localhost:4200`).

---

## 📦 Librerías destacadas

### Backend

* `spring-boot-starter-security`
* `jjwt`
* `spring-boot-starter-web`

### Frontend

* `@angular/material`
* `@angular/forms`
* `HttpClientModule`

---

## 📝 Consideraciones

* No se utiliza base de datos real. Usuarios y códigos de autorización están simulados.
* El flujo de SSO es **totalmente simulado**.
* Se siguen buenas prácticas de estructura: controladores, servicios, rutas, modularización.

---

## 🧾 Entrega

Este repositorio contiene ambos proyectos:

* ✅ Código backend Spring Boot (en `backendPruebaTecnica/`)
* ✅ Código frontend Angular + Material (en `frontend/`)
* ✅ Instrucciones claras de ejecución
* ✅ Assets proporcionados en `frontend/assets/`

---

## 📫 Contacto

Para dudas o acceso al diseño en Figma, contactar con el responsable de RRHH o técnico.
