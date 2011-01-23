#!/bin/bash

echo Generating archetype...
mvn clean archetype:create-from-project

echo Installing archetype...
cd target/generated-sources/archetype
mvn clean install


