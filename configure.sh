#!/bin/bash

cp ./frontend/.env.sample ./frontend/.env

cp docker-compose.prod.yml docker-compose.yml

docker compose up -d --build
