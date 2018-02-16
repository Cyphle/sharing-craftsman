#!/usr/bin/env bash

echo "Copying deployable files in app folder"
rm -rf $1
mkdir $1
cp scripts/docker-compose.yml $1/docker-compose.yml
cp scripts/Dockerfile $1/Dockerfile
cp target/knowledge-library-1.0.jar $1/knowledge-library.jar
cp scripts/update_docker_files.py $1/update_docker_files.py
cp scripts/README.md $1/README.md
echo "End copying deployable files in app folder"