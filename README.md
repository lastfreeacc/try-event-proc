# Simple app to explore event processing

## How to use
### Requirements
- Internet connection
- Installed docker and docker-compose
### How run
- Copy repo
- Run docker-compose up --build --scale=consumer=2
- You can see results in volume/db.txt file

## Project structure:
it consists of some parts:
- Kafka container as event middleware
- Producer container with simple producer app
- Consumer container with scalable java-app, it will consume and process events
- Volume with single file; it represents db

## Goals
- Producer works with pick load: 100 messages in 10 sec
- Consumer process messages in parallel (so u need -scale=consumer=2)
- All messages must be process
- It is possible to duplicate messages
- Order is not required. You need only to store messages
