# EcoReport – proyecto final de clase

Aplicación Android nativa con Kotlin + Jetpack Compose que integra los contenidos trabajados en el curso hasta el 06/11/2026.

## Qué incluye

### Flujo local real
- Jetpack Compose y Navigation Compose.
- Room / SQLite.
- CRUD local: insertar, consultar, actualizar y eliminar.
- `Flow<List<ReporteEntity>>` desde Room.
- `StateFlow` en ViewModel.
- `collectAsStateWithLifecycle()` en Compose.
- Repository + ViewModel + Factory.
- Cambio de estado: `PENDIENTE -> EN_PROCESO -> RESUELTO`.
- Filtro dinámico por categoría desde SQL/Room:
  - TODOS
  - LUMINARIA
  - AGUAS
  - SUELOS
- Estadísticas generales.

### Demo remota real con Retrofit
Usa `https://jsonplaceholder.typicode.com/` para que pueda probarse sin crear cuentas ni configurar un backend.

- GET `/posts?_limit=10`
- POST `/posts`
- PUT `/posts/{id}`
- DELETE `/posts/{id}`
- Gson para JSON.
- DTOs separados del modelo Room.
- Estados de carga/error/mensaje con StateFlow.

**Importante:** JSONPlaceholder simula POST/PUT/DELETE, pero no persiste esas escrituras. Esta pantalla es didáctica para demostrar el protocolo HTTP y Retrofit. El CRUD local con Room sí es persistente.

## Versiones configuradas
- AGP: 9.3.3
- Gradle: 9.5.0 (wrapper incluido)
- Kotlin Compose plugin: 2.2.10
- KSP: 2.3.10
- Room: 2.8.5
- Navigation Compose: 2.10.2
- Lifecycle: 2.11.0
- Retrofit + Gson converter: 3.0.0
- Java/JDK: 17
- minSdk: 24
- target/compile SDK: 37

## Cómo abrir
1. Descomprime el ZIP en una ruta sin tildes ni caracteres especiales, por ejemplo `C:\ProyectosAndroid\EcoReportFinal`.
2. Android Studio -> File -> Open -> selecciona la carpeta `EcoReportFinal`.
3. Verifica que Gradle JDK sea 17 o superior.
4. Ejecuta `Sync Project with Gradle Files`.
5. Espera que termine la descarga de dependencias.
6. Inicia un emulador completamente y ejecuta `app`.

No se incluye `local.properties`, porque contiene la ruta específica al SDK de cada PC y Android Studio debe generarlo localmente.

## Dónde cambiar la API
Archivo:
`app/src/main/java/com/example/ecoreport/data/remote/RetrofitClient.kt`

Actualmente:
`https://jsonplaceholder.typicode.com/`

Si posteriormente usa MockAPI o una API propia, reemplace `BASE_URL` y adapte `ReporteApiService`/DTOs a la estructura real de su JSON.

## Arquitectura

```
Compose UI
   ↓
AppNavigation
   ↓
ReporteViewModel
   ↓
ReporteRepository
   ├── ReporteDao -> Room -> SQLite -> Flow
   └── ReporteApiService -> Retrofit -> HTTP/JSON
```
