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
Launch update-dockerfile.sh script:
- Command: <b>./update-dockerfile.sh param1 param2</b>
- param1 is the source file to fetch infos
- param2 two is the Dockerfile target
- Example: ./update-dockerfile.sh user-infos.yml ../../Dockerfile

Example file containing infos: <b>user-infos.yml</b>

### Note
<b>Script also launch a docker-compose up -d</b>

## Informations
To install jenkins:

docker run -d -u root -p 8088:8080 -v jenkins-data:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock -v /Users/cyrilpham-le/Documents/Projets/Docker/jenkins/apps:/home/apps --name jenkins jenkinsci/blueocean

Update mapped volumes:
 - to correspond to system host
 - to correspond to Jenkins folder

Update Jenkinsfile of project to setup TARGET_PATH variable to correspond to Jenkins mapped volume