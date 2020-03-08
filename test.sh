#!/usr/bin/env bash


docker run -it --rm \
-v ~/.m2:/root/.m2 \
-v "$(pwd)":/usr/src/mymaven \
-w /usr/src/mymaven \
maven:ibmjava-alpine \
mvn clean test
