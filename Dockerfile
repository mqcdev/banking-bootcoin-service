FROM openjdk:11
VOLUME /tmp
EXPOSE 8091
ADD ./target/ms-bootCoin-0.0.1-SNAPSHOT.jar ms-bootCoin.jar
ENTRYPOINT ["java","-jar","/ms-bootCoin.jar"]