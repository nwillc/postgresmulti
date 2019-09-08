#!/bin/bash

set -e
set -u

function create_user_and_database() {
	local database=$1
	echo " Creating database '$database'"
	PGPASSWORD="$POSTGRES_PASSWORD" psql -v --username "$POSTGRES_USER" <<-EOSQL
	    CREATE DATABASE "$database" OWNER $POSTGRES_USER;
EOSQL
}

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
	echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
	for db in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ':' ' '); do
		create_user_and_database $db
	done
	echo "Multiple databases created"
fi
