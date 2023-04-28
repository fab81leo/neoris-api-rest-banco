## API REST - BANCO 
### PRUEBA TÉCNICA NEORIS 
Autor: Fabian Torres
<br>

Este proyecto fue desarrollado con el IDE de Spring Tool Suite 4, utilizando las siguientes tecnologías:


## Funcionalidad

Este proyecto se encarga de realizar diferentes transacciones (débito y crédito), sobre las diferentes cuentas bancarias (ahorro y corriente) vinculadas a un cliente. Se manejan y contralan diferentes excepciones cuando el saldo del cliente queda en cero (0) y se intenta retirar más dinero, también cuando se intenta retirar más de un monto permitido, entre otras validaciones.

La aplicación permite registrar un control de todos los movimientos que se han efectuado (débito y crédito). Para
posteriormente realizar fitros de búsqueda por fecha e identificador del cliente. 

<br>

- **Java Spring Boot**

> Versión 3.0.6 (estable)

- **IDE**

> SpringToolSuite4

- **Base de Datos Relacional**

> MySQL Community Server - GPL, MySQL Workbench 8.0.30

- **Postman**

> v10.13.4

- **Java**

> v17.0.3.1

- **Apache Maven**

> v3.8.4

## API REST para desplegar en DOCKER
### [Contenedores e Imágenes]

- **1. Descargamos la imagen de MySQL**

> docker pull mysql

- **2. Una vez terminada la descarga revisamos las imagenes**

> docker images

- **3. Creamos el CONTENEDOR de MySQL**

> docker run -d -p 13306:3306 --name mysql_container -e MYSQL_ROOT_PASSWORD=secret mysql:latest --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

- **4. Revisar contenedores creados**

> docker ps -a

- **Puertos a tener en cuenta**

> "13306->3306" 3306 => Puerto de MYSQL 13306 => Puerto expuesto hacia fuera del "CONTENEDOR"

- **5. Vamos acceder a MySQL**

> docker exec -it mysql_container mysql -uroot -p (Genera error TTY)
> // Se corrige con winpty
> winpty docker exec -it mysql_container mysql -uroot -p secret

- **6. Creamos el usuario de MySQL**

> CREATE USER 'fabian' IDENTIFIED BY 'secret';

- **6.1 Otorgamos todos los permisos**

> GRANT ALL PRIVILEGES ON *.* TO 'fabian'@'%';

- **7. Validar el usuario que creamos**

> Lo podemos hacer desde MySQL Workbench

- **7.1. Ejecutamos Script BaseDatos.sql en el contenedor de MySQL**

> Creación de Base de Datos

- **8. Conectar nuestra API REST hacia este CONTENEDOR**

> jdbc:mysql://localhost:13306/banco?serverTimezone=America/Bogota&useSSL=false

- **9. Crear un CONTENEDOR con el API REST**

> Para tener un CONTENEDOR con MYSQL y otro CONTENEDOR con el API REST y que los dos se puedan comunicar.
> Lo primero es asegurarnos que los 2 CONTENEDORES van a QUEDAR DENTRO DE LA MISMA RED DE DOCKER para que puedan comunicarse
> docker network inspect bridge
> Validamos y en la RED de bridge se encuentra el CONTENEDOR DE mysql_container

- **9.1 Redes que tenemos actualmente**

> docker network ls

- **10. Agregar una RED nueva donde vamos a colocar nuestros CONTENEDORES**

> docker network create --driver bridge my-net
> Inspeccionando nuestra nueva red creada:
> docker network inspect my-net
> (Podemos ver que no existe ningún contenedor)

- **11. Cambiar la RED del CONTENEDOR de mysql_container [bridge] a la nueva RED CREADA [my-net]**

> docker network disconnect bridge mysql_container
> Conectamos a la nueva RED
> docker network connect my-net mysql_container

- **12. COMPILAR EL API para GENERAR el *.jar y PODERLO METER A NUESTRO CONTENEDOR**

> docker ps -a
> (Antes => localhost:13306 | Ahora => mysql_container)
> Ubicados en nuestro proyecto ejecutamos el siguiente comando para limpiar el proyecto
> mvn clean package -DskipTests (Para omitir test y no generar conflictos con DOCKER)

- **13. Verificar que este nuestro archivo de "Dockerfile" en nuestro proyecto**

> FROM openjdk:17-jdk-alpine
> RUN addgroup -S spring && adduser -S spring -G spring
> USER spring:spring
> ARG JAR_FILE=target/*.jar
> COPY ${JAR_FILE} app.jar
> ENTRYPOINT ["java","-jar","/app.jar"]

- **14. Vamos a ejecutar este Dockerfile**

> docker build -t fabian/users_service:V1 .
> En este punto ya "se creo nuestra imagen" que contiene "nuestra API"
> docker images

- **15. Ahora "creamos un contenedor sobre esta imagen" para que inicie nuestro "API REST"**

> Puerto 8033 => El configurado en el archivo application.properties
> users_container => Un nombre para nuestro contenedor
> b185ddb41800 => imagen vinculada al contenedor en este caso
> docker rm users_container (remover contenedor si existe anteriormente)
> docker run -d --network my-net  -p 18082:8033 --name users_container b185ddb41800
> Verificamos los contenedores que tenemos
> docker ps -a

- **16. En este punto ya tenemos los 2 CONTENEDORES**

> Vamos a verifica el LOG de CONTENEDOR DE USUARIOS para verificar que se halla iniciado correctamente
> docker logs -f users_container
> Actualmente ya tenemos la "IMAGEN" del "API REST" en un "CONTENEDOR" la "IMAGEN de MySQL" en otro "CONTENEDOR" comunicados entre sí

- **17. Consumo del endpoint en el puerto 18082**

> http://localhost:18082/banco/api/movimientos

- **Fin**