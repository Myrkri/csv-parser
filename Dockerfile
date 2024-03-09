FROM amazoncorretto:19.0.2 as build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
COPY manifests manifests

RUN ./mvnw package -DskipTests

FROM amazoncorretto:19.0.2

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
