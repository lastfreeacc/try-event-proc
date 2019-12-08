#!/bin/sh

# run producer
echo "wait for kafka"
sleep 10

echo "starting producer"
java -jar $APP_HOME/producer.jar