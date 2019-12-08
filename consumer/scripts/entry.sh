#!/bin/sh

# run producer
echo "wait for kafka"
sleep 15

echo "starting consumer"
cd $APP_HOME
java -jar consumer.jar