#!/bin/sh

# run producer
echo "wait for kafka"
sleep 15

echo "starting producer"
java -jar $APP_HOME/producer.jar