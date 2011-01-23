#!/bin/bash

echo Generating archetype...
mvn clean -DskipTests archetype:create-from-project

echo Installing archetype...
cd target/generated-sources/archetype
mvn clean install


