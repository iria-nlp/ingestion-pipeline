#!/bin/bash

docker-killall && rm -rf data && docker-compose up -d
