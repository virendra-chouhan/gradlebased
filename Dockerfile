FROM gradle:7-jdk11

WORKDIR /tmp
ADD . /tmp

RUN gradle build

CMD ["gradle", "clean", "bootRun"]
EXPOSE 3010
