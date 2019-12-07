#!/bin/sh

# run zookeeper
$ZK_HOME/bin/zkServer.sh start

until $ZK_HOME/bin/zkServer.sh status; do
    sleep 1
done

echo "zookeeper started"

# run Kafka
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties

echo "kafka started"
