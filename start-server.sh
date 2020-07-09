#!/bin/sh
cd /home/ubuntu/fapesq
mvn spring-boot:run -l /home/ubuntu/fapesq/log/server-output-$(date "+%Y.%m.%d-%H.%M.%S").log &