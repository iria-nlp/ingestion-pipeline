#!/bin/bash

docker-killall && rm -rf data && make docker-build && docker-compose up -d
