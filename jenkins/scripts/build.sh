#!/bin/bash
set -e

MODULES=(
	realtime-service
)

for i in ${!MODULES[@]}; do
	cd ${MODULES[$i]}

	rm -rf target	
	mvn -U install
	mvn -f pom.xml clean package --settings ../settings.xml deploy
	cd ..
done
