#!/bin/bash

# Definir la variable WILDFLY_HOME (ruta de instalación de WildFly)
# WILDFLY_HOME=/Users/kevin/wildfly

# Limpiar y empaquetar el proyecto con Maven
mvn clean package

# Renombrar el archivo WAR generado
mv target/gsmkev-java-ecommerce-backend-1.0-SNAPSHOT.war target/ROOT.war

# Copiar el archivo WAR generado al directorio de despliegue de WildFly
cp target/ROOT.war $WILDFLY_HOME/standalone/deployments

# Iniciar el servidor WildFly
$WILDFLY_HOME/bin/standalone.sh