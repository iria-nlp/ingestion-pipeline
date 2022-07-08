FROM magiplatform/spark-parent:latest

LABEL maintainer="reynoldsm88@gmail.com"

ENV SCALA_VERSION 2.12

COPY scripts/* /opt/app/bin
COPY ./target/scala-$SCALA_VERSION/*assembly*.jar /opt/app/pkg

ENTRYPOINT nohup tail -f /dev/null
