FROM openjdk:15-jdk-slim-buster

ENV NMMJ2_HOME /usr/local/nmmj2
ENV PARAMS DefaultParams.txt
RUN mkdir -p "NMMJ2_HOME"
RUN mkdir -p "NMMJ2_HOME/target"
RUN mkdir -p "NMMJ2_HOME/marcos"
RUN mkdir -p "NMMJ2_HOME/database"
RUN mkdir -p "NMMJ2_HOME/work"
WORKDIR $NMMJ2_HOME

COPY ./target/nmmj2.jar $NMMJ2_HOME/target

ENTRYPOINT ["java", "-jar", "$NMMJ2_HOME/target/nmmj2.jar", "$PARAMS"]
