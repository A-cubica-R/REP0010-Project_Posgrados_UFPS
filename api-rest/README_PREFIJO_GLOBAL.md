# Configuración de API REST - Prefijo Global

## Resumen

Todos los endpoints de la API REST ahora funcionan bajo un prefijo centralizado: `/app-api/v1`

Esto significa que un endpoint definido como:
```java
@RestController
@RequestMapping("/municipio")
public class MunicipioRestController { ... }
```

Es accesible en: `http://localhost:8080/app-api/v1/municipio`

## Cómo funciona

### 1. Configuración centralizada
Los controllers usan rutas relativas (ej: `/municipio`, `/aspirante`) sin prefijo.

### 2. Prefijo global aplicado por Spring Boot
El archivo `application.properties` contiene:
```properties
server.servlet.context-path=/app-api/v1
```

Esto le indica a Spring Boot que agregue `/app-api/v1` como prefijo a TODAS las rutas HTTP.

### 3. Clase de configuración (referencia)
`ApiRestConfiguration.java` documenta la configuración y permite inyectar el prefijo en código si es necesario.

## Cambiar el prefijo

Para cambiar el prefijo global:

1. Edita `api-rest/src/main/resources/application.properties`
2. Modifica el valor de `server.servlet.context-path`
3. Actualiza también `api.rest.prefix` para consistencia documentada

Ejemplo para cambiar a `/api/v2`:
```properties
server.servlet.context-path=/api/v2
api.rest.prefix=/api/v2
```

## Rutas de ejemplo

| Recurso | Definición en Controller | URL Real |
|---------|-------------------------|----------|
| Municipio | `/municipio` | `/app-api/v1/municipio` |
| Aspirante | `/aspirante` | `/app-api/v1/aspirante` |
| Usuario | `/usuario` | `/app-api/v1/usuario` |

## Agregar un nuevo endpoint

1. Crea un nuevo `RestController` en `ufps.edu.co.controllers`
2. Define `@RequestMapping("/mi-recurso")`  ← **sin el prefijo `/app-api/v1`**
3. Spring Boot automáticamente lo mapea a `/app-api/v1/mi-recurso`

## Notas arquitectónicas

- Esta configuración es específica de **api-rest** y se define en su `application.properties`
- El prefijo es un problema de presentación HTTP, no de lógica de negocio
- La lógica de negocio en **api-core** no conoce nada del prefijo
- **api-base** tampoco está afectada por esta configuración
- Solo el módulo ejecutable (`spring-app`) lee estas propiedades en tiempo de ejecución
