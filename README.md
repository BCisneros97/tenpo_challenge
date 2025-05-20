# FullStack Challenge 2025

## Descripción del proyecto

Este proyecto es una solución al FullStack Challenge 2025, que consiste en el diseño e implementación de una aplicación web completa utilizando React para el frontend y Spring Boot para el backend. La aplicación permite gestionar transacciones asociadas a cuentas de usuarios denominados "Tenpistas", cumpliendo con restricciones y validaciones específicas.

El objetivo principal del proyecto es demostrar habilidades técnicas para construir una solución escalable, bien documentada y mantenible, siguiendo las buenas prácticas del desarrollo de software moderno.

### Funcionalidades Principales

- Registrar nuevas transacciones con los siguientes datos:
  - ID de transacción
  - Monto en pesos
  - Giro o comercio
  - Nombre del Tenpista
  - Fecha de la transacción

- Editar y eliminar transacciones existentes

### Restricciones

- Cada cliente puede tener un máximo de **100 transacciones**
- Las transacciones **no pueden tener montos negativos**
- La fecha de transacción **no puede ser posterior a la fecha y hora actual**
- Límite de 3 requests por minuto por cliente (Rate Limiting)

---

## Instrucciones de uso

1.  Clonar el repositorio.

```bash
git clone https://github.com/BCisneros97/tenpo_challenge.git
cd tenpo_challenge
```

2. Ejecutar el script de configuración. Esto levantará: la base de datos PostgreSQL, el backend en Spring Boot y el frontend en React.

```bash
./configure.sh
```

3. Acceder a la aplicación mediante los endpoints predeterminados:
- Frontend: http://localhost:8081
- Backend: http://localhost:8080
- Swagger UI (API Docs): http://localhost:8080/swagger-ui

## Interacción con la API

Antes de registrar una transacción es necesario consultar la lista de clientes registrados para obtener los nombres válidos a enviar en el campo `clientName` de los endpoints de transacciones. 

Considerar también que el campo `transactionDate` espera fechas con la zona horaria UTC.

### Endpoints principales

| Método | Endpoint            | Descripción                      |
| ------ | ------------------- | -------------------------------- |
| GET    | `/clients`      | Obtener listado de clientes |
| GET    | `/transactions`      | Obtener listado de transacciones |
| POST   | `/transactions`      | Crear una nueva transacción      |
| PUT    | `/transactions/{id}` | Editar una transacción existente |
| DELETE | `/transactions/{id}` | Eliminar una transacción         |

### Formato JSON para registrar o editar una transacción

```json
{
  "amount": 5000,
  "businessSector": "Supermercado",
  "clientName": "Juan Perez",
  "transactionDate": "2025-05-20T14:30:00Z"
}
```

## Docker Hub

Las imágenes del backend y frontend se encuentran publicadas en Docker Hub:

- Backend: https://hub.docker.com/r/bcisneros7/tenpo-challenge-backend
- Frontend: https://hub.docker.com/r/bcisneros7/tenpo-challenge-backend

## Pruebas

Se incluyen pruebas unitarias para:

- Servicios
- Repositorios
- Controladores

También se utilizan mocks para pruebas más aisladas.
