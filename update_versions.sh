#!/bin/bash

# Declarar el arreglo de proyectos internos
projects=("alert" "people" "dynamic-validation")

# Directorio donde buscar los pom.xml de los proyectos que consumen estas propiedades.
# Ajusta este valor según tu estructura de directorios.
SEARCH_DIR="."  # Aquí se busca en el directorio actual y sus subdirectorios

# Recorrer cada proyecto del arreglo
for proj in "${projects[@]}"; do
  # Asumimos que cada proyecto tiene su pom.xml en su directorio raíz
  POM="$proj/pom.xml"

  if [ ! -f "$POM" ]; then
    echo "No se encontró pom.xml para el proyecto '$proj'"
    continue
  fi

  # Extraer el groupId del pom.xml (se toma la primera ocurrencia)
  groupId=$(grep -oPm1 '(?<=<groupId>)[^<]+' "$POM")

  # Extraer el artifactId
  artifactId=$(grep -oPm1 '(?<=<artifactId>)[^<]+' "$POM")

  # Se asume que la propiedad local que define la versión es: artifactId.version
  # Por ejemplo, para alert se espera <alert.version>0.0.2-SNAPSHOT</alert.version>
  localProp="${artifactId}.version"

  # Extraer la versión definida en esa propiedad del pom.xml
  version=$(grep -oPm1 "(?<=<${localProp}>)[^<]+" "$POM")

  if [ -z "$groupId" ] || [ -z "$artifactId" ] || [ -z "$version" ]; then
    echo "No se pudo extraer groupId, artifactId o versión de $POM"
    continue
  fi

  # La propiedad que se usan en los poms consumidores se asume que es:
  # <groupId>.version. Por ejemplo: <ar.lamansys.alert.version>
  consumerProp="${groupId}.version"

  echo "Proyecto: $proj"
  echo "  groupId: $groupId"
  echo "  artifactId: $artifactId"
  echo "  Propiedad local ($localProp): $version"
  echo "  Propiedad en consumidores a actualizar: $consumerProp"

  # Buscar en el directorio de consumidores (SEARCH_DIR) todos los pom.xml que declaren consumerProp
  # y actualizar su valor a la versión detectada.
  for consumerPom in $(find "$SEARCH_DIR" -name pom.xml); do
    if grep -q "<${consumerProp}>" "$consumerPom"; then
      echo "Actualizando $consumerPom: estableciendo <$consumerProp> a $version"
      # Se usa sed para reemplazar el contenido de la etiqueta.
      # Se crea un backup con extensión .bak (puedes quitar -i.bak si no deseas backup)
      sed -i "s|<${consumerProp}>[^<]*</${consumerProp}>|<${consumerProp}>${version}</${consumerProp}>|g" "$consumerPom"
    fi
  done

done
