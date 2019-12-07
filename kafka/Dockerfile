FROM alpine:3.10

LABEL author "last.free.acc@gmail.com"

# kafka args
ARG KAFKA_VERSION=2.3.1
ARG SCALA_VERSION=2.12
ARG KAFKA_DL_URL="http://apache-mirror.rbc.ru/pub/apache/kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz"
# zoo args
ARG ZK_VERSION=3.5.6
ARG ZK_PKG_NAME="zookeeper-${ZK_VERSION}"
ARG ZK_HOME="/opt/zookeeper"
ARG ZK_DL_URL="http://www.apache.org/dist/zookeeper/${ZK_PKG_NAME}/apache-${ZK_PKG_NAME}-bin.tar.gz"
#ARG ZK_CLIENT_PORT=2181

ENV PATH=$PATH:$ZK_HOME/bin \
    ZOO_LOG4J_PROP=INFO,CONSOLE \
    ZK_USER=zookeeper \
    ZK_HOME=${ZK_HOME}
###

# install all deps
RUN apk add --no-cache openjdk8-jre bash docker coreutils su-exec curl
# create user and dirs
RUN mkdir -p /opt /tmp && \
    adduser -D -u 1000 -H -h ${ZK_HOME} ${ZK_USER} && \
    addgroup ${ZK_USER} root && \
    adduser -DH -s /sbin/nologin kafka && \
    mkdir -p /opt/kafka && \
    chown -R kafka: /opt/kafka && \
    mkdir -p /tmp/kafka-logs && \
    chown -R kafka: /tmp/kafka-logs 
# install zookeeper
RUN cd /tmp && \
    curl -SL "${ZK_DL_URL}" -o ${PKG_NAME}.tar.gz && \
    tar -zxf ${PKG_NAME}.tar.gz && \
    mkdir -m 0775 -p ${ZK_HOME} && \
    mv "apache-${ZK_PKG_NAME}-bin/"* ${ZK_HOME} && \
    rm -rf "apache-${ZK_PKG_NAME}-bin" "apache-${ZK_PKG_NAME}-bin/".tar.gz
# install kafka
RUN cd /tmp && \
    curl -SL "${KAFKA_DL_URL}" -o kafka.tar.gz && \
    tar -zxf kafka.tar.gz  && \
    mv "/tmp/kafka_${SCALA_VERSION}-${KAFKA_VERSION}/"* /opt/kafka
    
# adding entrypoint and startup scripts
# COPY *.sh $ZK_HOME/bin/
# VOLUME ["${ZK_HOME}/data", "${ZK_HOME}/logs"]

# ENTRYPOINT ["entrypoint.sh"]
# CMD ["bootstrap.sh", "zkServer.sh", "start-foreground"]
###
ENV PATH /sbin:/opt/kafka/bin/:$PATH
ENV KAFKA_HOME /opt/kafka

### add some settings
COPY config/zookeeper/* $ZK_HOME/conf/
COPY config/kafka/* $KAFKA_HOME/config/
COPY scripts/entry.sh /
RUN chmod +x entry.sh
ENTRYPOINT ["/entry.sh"]

# 2181 is zookeeper, 9092 is kafka
EXPOSE 2181 9092