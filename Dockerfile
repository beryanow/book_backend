FROM adoptopenjdk/openjdk14

RUN apt update
RUN apt install maven -y
RUN apt install curl -y

RUN mvn -v

WORKDIR /book-library-backend/

COPY src ./src
COPY uploaded ./uploaded
COPY pom.xml .

RUN mvn clean package -Ph2
CMD java -jar --enable-preview -Dspring.profiles.active=h2 target/BookBackend-1.0.0-SNAPSHOT.jar

EXPOSE 8080