# Use uma imagem base oficial do OpenJDK 17 minimalista como base
FROM openjdk:21-jdk-slim

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo .jar da aplicação para o diretório de trabalho dentro do contêiner
COPY target/euvatarapi-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponha a porta que a aplicação vai utilizar
EXPOSE 8080

# Defina as variáveis de ambiente padrão (essas podem ser sobrescritas durante o runtime)
ENV SPRING_APPLICATION_NAME=euvatarapi
ENV SPRING_DATASOURCE_URL="jdbc:postgresql://dpg-cr5ohhjtq21c73b5km3g-a.oregon-postgres.render.com/euvatardb"
ENV SPRING_DATASOURCE_USERNAME=euvatardb_user
ENV SPRING_DATASOURCE_PASSWORD=6e5bwNCIfGsLfz2y8cXXYcwRHpKRMnvO
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
ENV SPRING_JPA_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_MAIN_ALLOW_CIRCULAR_REFERENCES=true
ENV SPRING_JPA_SHOW_SQL=true
ENV MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE=*
ENV MANAGEMENT_ENDPOINT_HEALTH_SHOW_DETAILS=always
ENV MANAGEMENT_ENDPOINTS_WEB_BASE_PATH=/actuator
ENV SPRINGDOC_API_DOCS_ENABLED=true
ENV SPRINGDOC_SWAGGER_UI_ENABLED=true
ENV CONVAI_API_BASE_URL=https://api.convai.com
ENV CONVAI_API_KEY=cdcf6a41413a5cd4abc3929bfc18173d
ENV JWT_SECRET="nRgO/DGl6hYBNni7R8hHfSTOWUgQj1kBpEOTi5UoIns="
ENV JWT_ISSUER=euvatar-api
ENV JWT_EXPIRATION=36000

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]