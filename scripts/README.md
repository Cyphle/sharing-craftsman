# How to install
## Clean up
- Destroy docker containers
- Destroy docker images
- Delete unused volumes: docker volume rm $(docker volume ls -qf dangling=true)

## Setup parameters
Update docker-compose.yml file to setup:
- mapped volumes
- ports
- ip

## Launch script
Launch: python<3> update_docker_files.py knowledge-library-infos.json

## Informations
To install jenkins:

docker run -d -u root -p 8088:8080 -v jenkins-data:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock -v /Users/cyrilpham-le/Documents/Projets/Docker/jenkins/apps:/home/apps --name jenkins jenkinsci/blueocean

Update mapped volumes:
 - to correspond to system host
 - to correspond to Jenkins folder

Update Jenkinsfile of project to setup TARGET_PATH variable to correspond to Jenkins mapped volume

# LAUNCH DOCKER
docker-compose up -d