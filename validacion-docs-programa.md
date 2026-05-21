## Endpoint 1: Obtener Cohortes por Programa

* **Método:** `GET`
* **Ruta:** `/api/application/case/cohortes`
* **Descripción:** Obtiene las cohortes asociadas al programa del usuario autenticado (el backend obtiene el `programaId` a través del token/sesión).
* **Query Params (Opcional):** Ninguno obligatorio.

### Respuesta (`200 OK`)

```json
[
  {
    "id": 1,
    "nombre": "Cohorte-3 2025-1",
    "activa": true,
    "semestre": "2025-1",
    "cupos": 45,
    "fechaLimiteDocs": "2026-05-15",
    "fechaLimiteInscripcion": "2025-02-10",
    "totalInscritos": 45,
    "totalValidados": 32,
    "totalAdmitidos": null
  },
  {
    "id": 2,
    "nombre": "Cohorte-2 2024-2",
    "activa": false,
    "semestre": "2024-2",
    "cupos": 40,
    "fechaLimiteDocs": "2024-07-10",
    "fechaLimiteInscripcion": "2024-06-15",
    "totalInscritos": 38,
    "totalValidados": 38,
    "totalAdmitidos": 32
  }
]

```

---

## Endpoint 2: Obtener Aspirantes por Cohorte

* **Método:** `GET`
* **Ruta:** `/api/application/case/cohortes/{idCohorte}/aspirantes`
* **Descripción:** Retorna la lista de aspirantes inscritos a una cohorte específica con el estado resumido de sus documentos para los contadores y filtros de la interfaz.

### Respuesta (`200 OK`)

```json
[
  {
    "id": 1,
    "nombre": "Carlos Andrés Rodríguez Martínez",
    "cedula": "1087654321",
    "correo": "carlos.rodriguez@email.com",
    "documentosValidados": 5,
    "totalDocumentos": 7,
    "estadoGeneral": "en progreso" 
  },
  {
    "id": 2,
    "nombre": "María Fernanda Pérez González",
    "cedula": "1098765432",
    "correo": "maria.perez@email.com",
    "documentosValidados": 7,
    "totalDocumentos": 7,
    "estadoGeneral": "validados"
  }
]

```

---

## Endpoint 3: Obtener Documentos Detallados de un Aspirante

* **Método:** `GET`
* **Ruta:** `/api/application/case/aspirantes/{idAspirante}/documentos`
* **Descripción:** Carga los documentos y metadatos específicos de un aspirante cuando el administrador lo selecciona para iniciar la revisión individual.

### Respuesta (`200 OK`)

```json
{
  "idAspirante": 123,
  "nombreAspirante": "Carlos Andrés Rodríguez Martínez",
  "cedula": "1087654321",
  "estadoGeneral": "en progreso",
  "documentos": [
    {
      "id": 101,
      "nombre": "Cédula de ciudadanía",
      "estado": "APROBADO",
      "motivoRechazo": null,
      "linkArchivo": "https://storage.ufps.edu.co/docs/cedula_1087654321.pdf?token=xyz"
    },
    {
      "id": 102,
      "nombre": "Diploma de pregrado",
      "estado": "RECHAZADO",
      "motivoRechazo": "El documento es ilegible y borroso en las firmas.",
      "linkArchivo": "https://storage.ufps.edu.co/docs/diploma_1087654321.pdf?token=xyz"
    },
    {
      "id": 103,
      "nombre": "Acta de grado",
      "estado": "PENDIENTE",
      "motivoRechazo": null,
      "linkArchivo": "https://storage.ufps.edu.co/docs/acta_1087654321.pdf?token=xyz"
    }
  ]
}

```

---

## Endpoint 4: Actualizar Estado de un Documento (Aprobar/Rechazar)

* **Método:** `PATCH` (o `PUT`)
* **Ruta:** `/api/application/case/documentos/{idDoc}/estado`
* **Descripción:** Cambia el estado de validación de un documento individual y registra el motivo si es denegado.

### Cuerpo de la Petición (`Request Body`)

```json
{
  "estado": "RECHAZADO", 
  "motivoRechazo": "El archivo adjunto no corresponde al acta de grado solicitada."
}

```

*(Nota: Si el `estado` es `"APROBADO"`, el campo `"motivoRechazo"` se envía como `null` o se omite).*

### Respuesta (`200 OK`)

```json
{
  "id": 23,
  "nombre": "Acta de grado",
  "estado": "RECHAZADO",
  "motivoRechazo": "El archivo adjunto no corresponde al acta de grado solicitada."
}

```