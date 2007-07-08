#!/bin/sh

# Carma unix shell startup script. Should not be modified by end-users.

echo "Starting Carma."

java -jar libs/JavaApplication-0.1-SNAPSHOT.jar

echo "Carma execution finished."
echo "See results in the report file you configured."
