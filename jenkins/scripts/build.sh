#!/bin/bash
set -e

MODULES=(
	realtime-service
)

for i in ${!MODULES[@]}; do
	cd ${MODULES[$i]}

	rm -rf target	
	mvn -U install
	mvn -X -Dmaven.test.skip=true --settings ../settings.xml deploy
	cd ..
done