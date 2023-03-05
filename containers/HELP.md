# Getting Started

## Reference Documentation

This will help to quickly start with the project.
Below command will initialize all required containers:
* pulsar cluster (pulsar itself, zookeeper, bookkeeper, etc)
* postgres database

Maker sure to remove folder "data" before each execution of the below

To start all containers execute:
```
$ docker compose up -d
```

To stop and remove all containers:
```
$ docker compose down
```

### Pulsar manager (optional)

You can also start pulsar manager:
```
$ docker run --name pulsar-manager --network pulsar-network -it -p 9527:9527 -p 7750:7750 -e SPRING_CONFIGURATION_FILE=/pulsar-manager/pulsar-manager/application.properties apachepulsar/pulsar-manager:latest
```

Then configure access:
```
$ CSRF_TOKEN=$(curl http://localhost:7750/pulsar-manager/csrf-token)
$ curl -H 'X-XSRF-TOKEN: $CSRF_TOKEN' -H 'Cookie: XSRF-TOKEN=$CSRF_TOKEN;' -H "Content-Type: application/json" -X PUT http://localhost:7750/pulsar-manager/users/superuser -d '{"name": "admin", "password": "apachepulsar", "description": "test", "email": "username@test.org"}'
```

Go to http://localhost:9527/ and login with admin/apachepulsar

### PgAdmin (optional)

You can also start PgAdmin

```
$ docker run --hostname=b85f3ab0da04 --user=pgadmin --mac-address=02:42:ac:11:00:02 --env=PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin --env=PYTHONPATH=/pgadmin4 --env=PGADMIN_DEFAULT_PASSWORD=pgadmin --env=PGADMIN_DEFAULT_EMAIL=pgadmin --volume=/var/lib/pgadmin --workdir=/pgadmin4 -p 32779:443 -p 32780:80 --runtime=runc -t -d dpage/pgadmin4:latest
```

Navigate to http://localhost:32780 and login with pgadmin/pgadmin