## Endpoint 1: Obtener entrevistas del aspirante
como esta ahorita: /api/application/case/director-programa/aspirants/criteriosById
como debe quedar:
- **Método:** `GET`
- **Ruta:** `/api/application/case/director-programa/{idAspirante}/criterios`
- **Descripción:** Obtiene los criterios de cada paso del proceso del aspirante

### Cuerpo de la Petición (`Request Body`) --> pide el id del aspirante supongo
```json
{
  "id": 0
}

```

### Respuesta (`200 OK`)

```json
{
  "criterios": [
    {
      "id": 0,
      "nombreCriterio": "string",
      "peso": 0,
      "puntajeObtenido": 0
    }
  ],
  "puntajeTotal": 0
}
```
## Endpoint 2: Calificar criterios de aspirante
Como está ahorita: /api/application/case/director-programa/calificacion/criterio/create
como debe quedar:
- **Método:** `POST o PATCH`
- **Ruta:** `/api/application/case/director-programa/{idAspirante}/criterios/calificar`
- **Descripción:** Califica un criterio especifico con respecto a un aspirante, actualizando el puntaje total del aspirante en backend, esto afecta también el estado actual del aspirante correspondiente por su id, si calificó solo uno y faltan por calificar entonces el estado del aspirante va a ser VALIDADO_EN_PROGRESO, si se calificaron todos entonces va a ser VALIDADO_CALIFICADO.

### Cuerpo de la Petición (`Request Body`) --> C
```json
{
  "idAspirante": 0,
  "idCriterio": 0,
  "puntuacion": 100,
}
```
### Respuesta (`200 OK`)

```json
{
  "idAspirante": 0,
  "idCriterio": 0,
  "nombreCriterio": "string",
  "puntuacion": 100,
}
```
ESTE ULTIMO ENDPOINT PUEDE REPLANTEARSE POR LO DE LA COSA DE VALIDACIÓN