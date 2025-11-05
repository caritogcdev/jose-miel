# Nicho Nativo Romeral

Aplicación **Spring Boot**. Este README explica cómo preparar el entorno local, configurar la base de datos **MySQL**, ejecutar la app y las convenciones de repositorio (ignore y EOL).

---

## Requisitos

* **JDK 17+** (o la versión que defina `pom.xml`)
* **MySQL 8.x**
* **Git**
* **Maven Wrapper** incluido (`mvnw` / `mvnw.cmd`), no necesitas instalar Maven globalmente

---

## Configuración rápida

1. **Clona el repositorio**

   ```bash
   git clone <URL_DEL_REPO>
   cd <CARPETA_DEL_REPO>
   ```

2. **Crea la base de datos y el usuario en MySQL** (ajusta nombres/credenciales a tu entorno):

   ```sql
   CREATE DATABASE mi_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER 'usuario'@'localhost' IDENTIFIED BY 'changeme';
   GRANT ALL PRIVILEGES ON mi_db.* TO 'usuario'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Copia la plantilla de configuración**

   * macOS/Linux:

     ```bash
     cp src/main/resources/application-example.properties src/main/resources/application.properties
     ```
   * Windows (PowerShell):

     ```powershell
     Copy-Item src/main/resources/application-example.properties src/main/resources/application.properties
     ```
   * Windows (CMD):

     ```cmd
     copy src\main\resources\application-example.properties src\main\resources\application.properties
     ```

4. **Edita `src/main/resources/application.properties`** con tus datos locales. Ejemplo:

   ```properties
   server.port=8080
   spring.application.name=nicho-nativo-romeral

   # MySQL local
   spring.datasource.url=jdbc:mysql://localhost:3306/mi_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username=usuario
   spring.datasource.password=changeme
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

   # JPA/Hibernate
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   # Opcional:
   # spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
   ```

5. **Ejecuta la aplicación**

   * macOS/Linux:

     ```bash
     ./mvnw spring-boot:run
     ```
   * Windows:

     ```cmd
     mvnw.cmd spring-boot:run
     ```

   La app levantará (por defecto) en: [http://localhost:8080](http://localhost:8080)

---

## Empaquetar y ejecutar el JAR

```bash
./mvnw clean package
java -jar target/*.jar
```

---

## Estructura del proyecto (resumen)

```
src/
  main/
    java/               # Código fuente Java
    resources/
      application.properties           # TU config local (ignorado por Git)
      application-example.properties   # Plantilla (versionada)
  test/                 # Pruebas
```

---

## Convenciones del repositorio

### `.gitignore`

Asegúrate de ignorar las configs reales y versionar solo la plantilla:

```gitignore
# Config local (ignorar)
src/main/resources/application.properties
src/main/resources/application-*.properties

# PERO sí versionar la plantilla
!src/main/resources/application-example.properties
```

### `.gitattributes` (EOL coherente)

```gitattributes
*                 text=auto

# Código y configs -> LF
*.java            text eol=lf
*.properties      text eol=lf
*.yml             text eol=lf
*.yaml            text eol=lf
*.md              text eol=lf
*.sh              text eol=lf

# Maven wrapper
/mvnw             text eol=lf
/mvnw.cmd         text eol=crlf

# Windows scripts -> CRLF
*.cmd             text eol=crlf
*.bat             text eol=crlf
```

Para aplicar normalización tras editar `.gitattributes`:

```bash
git add .gitattributes
git add --renormalize .
git commit -m "Normalize line endings via .gitattributes"
```

---

## Comandos útiles

* **Pruebas**: `./mvnw test`
* **Formateo/estilo** (si usas Spotless/Checkstyle, ajusta aquí)
* **Perfil activo**: puedes usar `spring.profiles.active=dev` en `application.properties` si el proyecto define perfiles.

---

## Resolución de problemas (FAQ)

* **`^M` rojo en GitHub**: son EOL Windows (CRLF). Cambia a LF en VS Code (barra de estado `CRLF` → `LF`) y commitea.
* **`Access denied for user`**: verifica `username`/`password` y privilegios del usuario MySQL.
* **`Communications link failure`**: MySQL apagado o puerto incorrecto (`3306`).
* **`Table doesn't exist`**: revisa `spring.jpa.hibernate.ddl-auto` (por ejemplo `update`) y el nombre de la DB en la URL.

---

## Contribución

1. Crea una rama: `git checkout -b feature/mi-cambio`
2. Commit con mensaje claro: `git commit -m "feat: descripción breve"`
3. Push y abre un Pull Request.

---

## Licencia

Definir aquí la licencia del proyecto (por ejemplo, MIT) si aplica.
