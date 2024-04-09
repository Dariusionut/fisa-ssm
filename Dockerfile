FROM openjdk:17-jdk-alpine

RUN apk add --no-cache bash curl

WORKDIR /app

COPY application/target/fisa-ssm.war .

CMD ["java", "-jar", "fisa-ssm.war"]
