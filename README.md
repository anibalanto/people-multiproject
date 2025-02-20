## Objetivo
El siguiente proyecto tiene como finalidad mostrar una opción a la implementación en capas que brinda la arquitectura hexagonal

Hay tres aplicaciones que se pueden levantar

- PeopleApiApp (dentro del proyecto people): Brinda un ejemplo de microservicio de busqueda de personas por uri.
- EduactionApp (dentro del proyecto education): Da un ejemplo de busqueda de estudiantes y docentes dado el nombre de un aula.
- HealthApp (dentro del proyecto health): Da un ejemplo de busqueda de pacientes y doctores dado el nombre de un consultorio.

Con el ejemplo brindado es posible levantar casos de uso compartidos por el proyecto people (En este caso solo un caso de uso GetPersonByUri) en los proyectos que lo requieren (educacion o health).

La ventaja es que se puede acceder a dos implementaciones de los casos de uso (en este caso solo GetPersonByUri) con solo modificar las propiedades:

- si requerimos que esta funcionalidad este de forma monolitica se deben levantar los casos de uso del paqueete core de people.

En el archiov properites setear:

```properties
people.service-impl=core
```


- si requerimos que esta funcionalidad este de forma distribuida en forma de microservicioes se deben levantar los casos de uso del paquete api-access de people.

```properties
people.service-impl=api-access
people.api.base-url=http://localhost:8080
```
## Ejemplos instanciados

### 1. Monolítico independiente

- Monolítico para `health` y `education`.
- Se accede a la implementación de `people` a través del módulo `people.core`.
- Cada instancia tiene su propia base de datos `people`.

```properties
people.service-impl=core
```

```mermaid
graph TB
%% Definición de subgráficos para organizar los componentes
subgraph education
e1(education-class)
e2(core)
end

subgraph eeducation-db
edb[(e-people)]
end

subgraph health
h1(health-consultory)
h2(core)
end

subgraph health-db
hdb[(h-people)]
end

%% Relaciones entre componentes
e1 -.GetPersonaByUri.-> e2
e2 --> edb

h1 -.GetPersonaByUri.-> h2
h2 --> hdb


```
### 2. Monolítico con datos compartidos

- Monolítico para `health` y `education`.
- Se accede a la implementación de `people` a través del  `people.core`.
- Ambas instancias comparten la base de datos `people`.



```mermaid
graph TB
%% Definición de subgráficos para organizar los componentes
subgraph education
e1(education-class)
e2(core)
end


subgraph health
h1(healt-consultory)
h2(core)
end

subgraph general-db
db[(people)]
end

%% Relaciones entre componentes
e1 -.GetPersonaByUri.-> e2
e2 --> db

h1 -.GetPersonaByUri.-> h2
h2 --> db


```

### 3. Distribuido con acceso a única instancia de people

- Distribuido para `healt` y `education`.
- Se accede a la implementación de `people` a través del módulo `people.api-access` (una `api rest`).
- La instancia de `people` acceden a una base de datos única de `people`.

```properties
people.service-impl=api-access
```

```mermaid
graph TB
%% Definición de subgráficos para organizar los componentes
subgraph education
e1(education-class)
e2(api-access)
end

subgraph health
h1(health-consultory)
h2(api-access)
end

subgraph people
p1(api)
p2(core)
end

subgraph general-db
db[(people)]
end

%% Relaciones entre componentes
e1 -.GetPersonaByUri.-> e2
e2 --/person/{uri}--> p1

h1 -.GetPersonaByUri.-> h2
h2 --"/person/{uri}"--> p1
    
p1 -.GetPersonaByUri.-> p2
p2 --> db
```

### 4. Distribuido con acceso a multiples instancias de people

- Distribuido para `health` y `education`.
- Se accede a la implementación de `people` a través del modulo `people.api-access` (una `api rest`).
- se configura para que `api-access` se comunique con un proxy que deriva las peticiones a tres instancias de `people`.
- Las instancias de `people` acceden a una base de datos única de `people`.

```properties
people.service-impl=api-access
```

```mermaid
graph TB
%% Definición de subgráficos para organizar los componentes
subgraph education
e1(education-class)
e2(api-access)
end

subgraph health
h1(health-consultory)
h2(api-access)
end

subgraph people-proxy
pr[(proxy)]
end

subgraph people1
p11(api)
p12(core)
end

subgraph people2
p21(api)
p22(core)
end

subgraph people3
p31(api)
p32(core)
end

subgraph general-db
db[(people)]
end

%% Relaciones entre componentes
e1 -.GetPersonaByUri.-> e2
e2 --/person/{uri}--> pr

h1 -.GetPersonaByUri.-> h2
h2 --"/person/{uri}"--> pr

pr --> p11
pr --> p21
pr --> p31
    
p11 -.GetPersonaByUri.-> p12
p12 --> db

p21 -.GetPersonaByUri.-> p22
p22 --> db

p31 -.GetPersonaByUri.-> p32
p32 --> db
```

## Aclaraciones

- Los ejemplos responden ante cualquier valor dado de uri para simplificar la demostración.

- Ahora esta todo en el mismo proyecto git. Pero la idea es que haya tres proyectos diferentes people, education y health. Y que se vinculen con maven a traves de miltiproject (como se puede ver en este pom.xml principal) y con git submodule para tener todo en el mismo lugar.


