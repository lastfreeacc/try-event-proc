#!/bin/sh

# run zookeeper
echo "starting zookeeper"
$ZK_HOME/bin/zkServer.sh start

until $ZK_HOME/bin/zkServer.sh status; do
    sleep 1
done

echo "zookeeper started"

# run Kafka as demon
echo "starting kafka"
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties >/dev/null 2>&1 < /dev/null &
KAFKA_PID=$!

echo "kafka will start soon"

while ! nc -z localhost 9092; do   
  sleep 1
done

echo "ready for messaging"
echo "creating new kafka topic"
$KAFKA_HOME/bin/kafka-topics.sh --create \
    --zookeeper localhost:2181 \
    --replication-factor 1 \
    --partitions 2 \
    --topic logs

echo "topic created"
$KAFKA_HOME/bin/kafka-topics.sh --list \
    --zookeeper localhost:2181

echo "ready for business..."

wait "${KAFKA_PID}"