FROM alpine:3.10

LABEL author "last.free.acc@gmail.com"

# prepare args
ARG JAR_NAME=producer.jar
ARG APP_HOME=/opt/app
ENV APP_HOME=${APP_HOME}
# install all deps
RUN apk add --no-cache openjdk8 bash coreutils su-exec maven
# create user and dirs
RUN adduser -DH -s /sbin/nologin app && \
    mkdir -p ${APP_HOME} && \
    chown -R app: ${APP_HOME} && \
    mkdir -p /tmp/app && \
    chown -R app: /tmp/app
# copy scr
COPY . /tmp/app/

# build app
RUN cd /tmp/app && \
    mvn --version && \
    mvn clean package && \
    mv ./target/${JAR_NAME} /opt/app && \
    rm -rf ./*

COPY scripts/entry.sh /
RUN chmod +x entry.sh

ENTRYPOINT ["/entry.sh"]