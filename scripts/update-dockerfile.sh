#!/usr/bin/env bash

parse_yaml() {
    local prefix=$2
    local s
    local w
    local fs
    s='[[:space:]]*'
    w='[a-zA-Z0-9_]*'
    fs="$(echo @|tr @ '\034')"
    sed -ne "s|^\($s\)\($w\)$s:$s\"\(.*\)\"$s\$|\1$fs\2$fs\3|p" \
        -e "s|^\($s\)\($w\)$s[:-]$s\(.*\)$s\$|\1$fs\2$fs\3|p" "$1" |
    awk -F"$fs" '{
    indent = length($1)/2;
    vname[indent] = $2;
    for (i in vname) {if (i > indent) {delete vname[i]}}
        if (length($3) > 0) {
            vn=""; for (i=0; i<indent; i++) {vn=(vn)(vname[i])("_")}
            printf("%s%s%s=(\"%s\")\n", "'"$prefix"'",vn, $2, $3);
        }
    }' | sed 's/_=/+=/g'
}

# Example ./update-dockerfile.sh knowledge-library-infos.yml Dockerfile docker-compose.yaml
source=$1
target=$2
composeTarget=$3

ymldata="$(parse_yaml $source)"

infos=(${ymldata// / })

# DATABASE
hostEntry='library_database_host'
libraryEntry='library_database_library'
passwordEntry='library_database_password'
portEntry='library_database_port'
databaseNameEntry='library_database_name'

hostLength=$((${#infos[0]} - ${#hostEntry} - 5))
libraryLength=$((${#infos[1]} - ${#libraryEntry} - 5))
passwordLength=$((${#infos[2]} - ${#passwordEntry} - 5))
portLength=$((${#infos[3]} - ${#portEntry} - 5))
databaseNameLength=$((${#infos[4]} - ${#databaseNameEntry} - 5))

host=${infos[0]:${#hostEntry} + 3:hostLength}
library=${infos[1]:${#libraryEntry} + 3:libraryLength}
password=${infos[2]:${#passwordEntry} + 3:passwordLength}
port=${infos[3]:${#portEntry} + 3:portLength}
database=${infos[4]:${#databaseNameEntry} + 3:databaseNameLength}

sed -i -e "s/<DB_HOST>/${host}/g" $target
sed -i -e "s/<DB_library>/${library}/g" $target
sed -i -e "s/<DB_PASSWORD>/${password}/g" $target
sed -i -e "s/<DB_PORT>/${port}/g" $target
sed -i -e "s/<DB_NAME>/${database}/g" $target

# APPLICATION
appProfileEntry='library_app_profile'
appUploadPathEntry='library_app_upload_path'
appLogPathEntry='library_app_logs_path'
appToInitializeEntry='library_app_toinitialize'

appProfileLength=$((${#infos[5]} - ${#appProfileEntry} - 5))
appUploadPathLength=$((${#infos[6]} - ${#appUploadPathEntry} - 5))
appLogsPathLength=$((${#infos[7]} - ${#appLogPathEntry} - 5))
appToInitializeLength=$((${#infos[8]} - ${#appToInitializeEntry} - 5))

appProfile=${infos[5]:${#appProfileEntry} + 3:appProfileLength}
appUploadPath=${infos[6]:${#appUploadPathEntry} + 3:appUploadPathLength}
appLogsPath=${infos[7]:${#appLogPathEntry} + 3:appLogsPathLength}
appToInitialize=${infos[8]:${#appToInitializeEntry} + 3:appToInitializeLength}

sed -i -e "s/<PROFILE>/${appProfile}/g" $target
sed -i -e 's|<UPLOAD_PATH>|'$appUploadPath'|g' $target
sed -i -e 's|<LOGS_PATH>|'$appLogsPath'|g' $target
sed -i -e "s/<TO_INITIALIZE>/${appToInitialize}/g" $target

# DOCKER
dockerPortEntry='library_docker_port'
dockerUploadPathEntry='library_docker_upload_path'
dockerLogPathEntry='library_docker_logs_path'
dockerNetworkEntry='library_docker_network'

dockerPortLength=$((${#infos[9]} - ${#dockerPortEntry} - 5))
dockerUploadPathLength=$((${#infos[10]} - ${#dockerUploadPathEntry} - 5))
dockerLogsPathLength=$((${#infos[11]} - ${#dockerLogPathEntry} - 5))
dockerNetworkLength=$((${#infos[12]} - ${#dockerNetworkEntry} - 5))

dockerPort=${infos[9]:${#dockerPortEntry} + 3:dockerPortLength}
dockerUploadPath=${infos[10]:${#dockerUploadPathEntry} + 3:dockerUploadPathLength}
dockerLogsPath=${infos[11]:${#dockerLogPathEntry} + 3:dockerLogsPathLength}
dockerNetwork=${infos[12]:${#dockerNetworkEntry} + 3:dockerNetworkLength}

sed -i -e "s/<APP_PORT>/\"${dockerPort}\"/g" $composeTarget
sed -i -e 's|<UPLOAD_PATH>|'$dockerUploadPath'|g' $composeTarget
sed -i -e 's|<LOGS_PATH>|'$dockerLogsPath'|g' $composeTarget
sed -i -e "s/<DOCKER_NETWORK>/${dockerNetwork}/g" $composeTarget

# LAUNCH DOCKER
docker-compose up -d

# DELETE
rm $target-e
rm $composeTarget-e
rm Dockerfile
rm docker-compose.yml
rm library-infos.yml