FROM cvcbrasil/java:8u172-jre

RUN mkdir /opt/unit-tests-demo

WORKDIR /opt/unit-tests-demo

COPY ./target/unit-tests-demo*.jar unit-tests-demo.jar

SHELL ["/bin/bash","-c"]

EXPOSE 8080
EXPOSE 5005

ENTRYPOINT java ${ADDITIONAL_OPTS} -jar unit-tests-demo.jar