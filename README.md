# Flickr Image Viewer

Aplicación web full-stack para buscar, visualizar y descargar imágenes de Flickr. Desarrollada con Angular 21 en el frontend y Spring Boot en el backend.

## Características

- Búsqueda de imágenes por término
- Visualización en grid responsive
- Modal de detalles con información completa de la imagen
- Descarga de imágenes en alta resolución
- Eliminación local de imágenes (no persiste en el servidor)
- Scroll infinito para cargar más resultados
- Gestión de estado reactiva con RxJS

## Arquitectura

### Frontend
- **Framework**: Angular 21 con Standalone Components
- **Lenguaje**: TypeScript 5.9
- **Estado**: RxJS BehaviorSubjects
- **HTTP Client**: Angular HttpClient
- **SSR**: Angular Universal (opcional)
- **Build**: Vite

### Backend
- **Framework**: Spring Boot 3.2.0
- **Lenguaje**: Java 17
- **API Externa**: Flickr REST API
- **Build Tool**: Maven
- **CORS**: Habilitado para desarrollo

## Estructura del Proyecto

```
webAppFlickr/
├── flickr-Front/          # Aplicación Angular
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/
│   │   │   │   ├── button/
│   │   │   │   ├── header/
│   │   │   │   ├── photo-card/
│   │   │   │   ├── photo-grid/
│   │   │   │   ├── photo-modal/
│   │   │   │   └── search-bar/
│   │   │   ├── pages/
│   │   │   │   └── home/
│   │   │   ├── services/
│   │   │   │   └── flickr.service.ts
│   │   │   ├── models/
│   │   │   │   └── Photo.ts
│   │   │   └── app.ts
│   │   └── environments/
│   │       └── environment.ts
│   └── package.json
│
└── flickr-api/           # API Spring Boot
    ├── src/main/java/com/example/flickr_api/
    │   ├── Controllers/
    │   │   └── FlickrController.java
    │   ├── Services/
    │   │   └── FlickrService.java
    │   ├── Models/
    │   │   ├── FlickrPhoto.java
    │   │   └── FlickrPhotoInfo.java
    │   ├── dto/
    │   └── FlickrApiApplication.java
    ├── src/main/resources/
    │   └── application.properties  (no incluido en git)
    └── pom.xml
```

## Requisitos Previos

- **Node.js** 20.x o superior
- **Java** 17 o superior
- **Maven** 3.6 o superior
- **Cuenta de Flickr API** (para obtener API key)

## Configuración

### 1. Variables de Entorno - Backend

El archivo `application.properties` contiene información sensible y **NO está incluido en el repositorio**.

Crea el archivo `flickr-api/src/main/resources/application.properties` con el siguiente contenido:

```properties
spring.application.name=flickr-api

# Puerto del servidor
server.port=8080

# Credenciales de Flickr API
flickr.api.key=TU_FLICKR_API_KEY
flickr.api.secret=TU_FLICKR_API_SECRET
flickr.api.url=https://www.flickr.com/services/rest/
```

**Cómo obtener las credenciales de Flickr:**
1. Visita [Flickr App Garden](https://www.flickr.com/services/apps/create/)
2. Crea una nueva app (selecciona "Apply for a Non-Commercial Key")
3. Copia tu `API Key` y `API Secret`
4. Reemplaza `TU_FLICKR_API_KEY` y `TU_FLICKR_API_SECRET` en el archivo

### 2. Variables de Entorno - Frontend

El frontend se conecta al backend a través de la URL configurada en `flickr-Front/src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api/images'
};
```

Para producción, actualiza `environment.prod.ts` con la URL de tu servidor.

## Instalación y Ejecución

### Backend (Spring Boot)

```bash
# Navegar al directorio del backend
cd flickr-api

# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`

### Frontend (Angular)

```bash
# Navegar al directorio del frontend
cd flickr-Front

# Instalar dependencias
npm install

# Ejecutar en modo desarrollo
npm start
```

La aplicación estará disponible en `http://localhost:4200`

## API Endpoints

### Backend REST API

| Método | Endpoint | Descripción | Parámetros |
|--------|----------|-------------|------------|
| GET | `/api/images/search` | Busca fotos por término | `q` (requerido), `page` (default: 1), `perPage` (default: 10) |
| GET | `/api/images/{id}` | Obtiene información detallada de una foto | `id` en path |
| GET | `/api/images/{id}/download` | Descarga una foto en alta resolución | `id` en path |

**Ejemplo de uso:**
```bash
# Buscar imágenes de gatos
GET http://localhost:8080/api/images/search?q=cats&page=1&perPage=20

# Obtener detalles de una imagen
GET http://localhost:8080/api/images/53493873412

# Descargar imagen
GET http://localhost:8080/api/images/53493873412/download
```

## Modelos de Datos

### FlickrPhoto (Búsqueda)
```java
{
  "id": "53493873412",
  "title": "Mountain Landscape",
  "thumbnailUrl": "https://...",
  "imageUrl": "https://...",
  "author": "John Doe"
}
```

### FlickrPhotoInfo (Detalles)
```java
{
  "id": "53493873412",
  "title": "Mountain Landscape",
  "description": "Beautiful mountain view...",
  "author": "John Doe",
  "tags": ["mountain", "nature", "landscape"],
  "imageUrl": "https://...",
  "downloadUrl": "https://...",
  "width": 4032,
  "height": 3024
}
```

## Funcionalidades Principales

### Frontend

1. **Búsqueda de Imágenes**
   - Barra de búsqueda con debounce
   - Resultados paginados
   - Scroll infinito

2. **Visualización**
   - Grid responsive adaptable
   - Tarjetas de imágenes con información básica
   - Modal con detalles completos

3. **Gestión de Imágenes**
   - Eliminación local (no persiste)
   - Filtrado de imágenes eliminadas
   - Restauración opcional

### Backend

1. **Integración con Flickr API**
   - Búsqueda de fotos con filtros
   - Obtención de información detallada
   - Manejo de diferentes tamaños de imagen

2. **Proxy de Descarga**
   - Descarga de imágenes en máxima resolución
   - Headers configurados para descarga directa

## Seguridad

- Las credenciales de API están excluidas del control de versiones (`.gitignore`)
- CORS habilitado para desarrollo (configurar restrictivamente en producción)
- Validación de parámetros en endpoints

## Scripts Disponibles

### Frontend
```bash
npm start          # Inicia servidor de desarrollo
npm run build      # Compila para producción
npm test           # Ejecuta tests con Vitest
npm run watch      # Compila en modo watch
```

### Backend
```bash
mvn spring-boot:run    # Inicia la aplicación
mvn clean install      # Compila el proyecto
mvn test               # Ejecuta tests
```

## Tecnologías Utilizadas

### Frontend
- Angular 21
- TypeScript 5.9
- RxJS 7.8
- Angular SSR
- Vite
- Vitest

### Backend
- Spring Boot 3.2.0
- Java 17
- Maven
- Jackson (JSON)
- Lombok
- RestTemplate

## Configuración de Producción

1. **Frontend**
   - Ejecutar `npm run build`
   - Los archivos compilados estarán en `dist/`
   - Servir con un servidor web (Nginx, Apache, etc.)

2. **Backend**
   - Crear un `application.properties` para producción
   - Empaquetar con `mvn clean package`
   - Ejecutar el JAR: `java -jar target/flickr-api-0.0.1-SNAPSHOT.jar`

3. **CORS**
   - Actualizar `@CrossOrigin` en `FlickrController.java` con dominios específicos

## Notas de Desarrollo

- El backend incluye logs detallados para debugging (`System.out.println`)
- Las imágenes eliminadas se guardan en memoria (se pierden al recargar)
- La paginación se maneja tanto en frontend como backend
- El modal muestra la imagen en su máxima resolución disponible

## Troubleshooting

**Error: "API key not found"**
- Verifica que `application.properties` existe y contiene las credenciales correctas

**Error: CORS**
- Asegúrate de que el backend esté corriendo en el puerto 8080
- Verifica la configuración de `@CrossOrigin` en el controlador

**Imágenes no se cargan**
- Verifica que la API key de Flickr sea válida
- Comprueba la consola del navegador para errores de red
- Revisa los logs del backend

## Licencia

Este proyecto es una prueba técnica para fines educativos.

## Autor

Desarrollado como prueba técnica - 2026
