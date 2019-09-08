FROM library/postgres

COPY create-databases.sh /docker-entrypoint-initdb.d/

RUN chmod +x /docker-entrypoint-initdb.d/create-databases.sh

EXPOSE 5432
