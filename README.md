## Objetivo
El siguiente proyecto tiene como finalidad mostrar una opción a la implementación en capas que brinda la arquitectura hexagonal

Hay tres aplicaciones que se pueden levantar

- PeopleApiApp (dentro del proyecto people): Brinda un ejemplo de microservicio de busqueda de personas por uri.
- EduactionApp (dentro del proyecto education): Da un ejemplo de busqueda de estudiantes y docentes dado el nombre de un aula.
- HealtApp (dentro del proyecto healt): Da un ejemplo de busqueda de pacientes y doctores dado el nombre de un consultorio.

Con el ejemplo brindado es posible levantar casos de uso compartidos por el proyecto people (En este caso solo un caso de uso GetPersonByUri) en los proyectos que lo requieren (educacion o healt).

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


## Aclaraciones

- Los ejemplos responden ante cualquier valor dado como nombre o uri para simplificar la demostración.

- Ahora esta todo en el mismo proyecto git. Pero la idea es que haya tres proyectos diferentes people, education y healt. Y que se vinculen con maven a traves de miltiproject (como se puede ver en este pom.xml principal) y con git submodule para tener todo en el mismo lugar.
