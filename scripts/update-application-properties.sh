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

source=$1

ymldata="$(parse_yaml $source)"
infos=(${ymldata// / })

# DOCKER
eurekaPathEntry='library_eureka_path'

eurekaPathLength=$((${#infos[13]} - ${#eurekaPathEntry} - 5))

eurekaPath=${infos[13]:${#eurekaPathEntry} + 3:eurekaPathLength}

sed -i -e 's|<EUREKA_PATH>|'$eurekaPath'|g' src/main/resources/application-prod.yml