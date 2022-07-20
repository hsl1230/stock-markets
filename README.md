# stock-markets

## Highlights

- Springfox is used to provide a swagger-ui
- All endpoints created can be tested through the swagger-ui
- File upload is supported in the swagger-ui
- MongoRepository is used to access mongo db
- Docker compose is used to set up the dev env

## What to do next?

- Add more unit tests to cover the code
- A framework to handle exceptions, logs, securities
- Add user authentication and authorization service to do login, access token generation, refreshing and validation.
- Add privilege service to manage and check the permissions
- Add security(authentication and authorization) through access token to the micro services
- Add spring cloud services(Config service, Netflix service and Vault Service)

## Build the project

```bash
./gradlew clean build
```

## Build the containers

```bash
docker compose build
```

## Start docker compose services

```bash
docker compose up -d
```

## Launch up swagger-ui of the micro services

http://localhost:25000/swagger-ui/index.html

## Launch up mongo-express

http://localhost:8081

## Stop docker compose services

```bash
docker compose down
```

## Run docker in none-root user

```bash
sudo groupadd docker
```

```bash
sudo usermod -aG docker $USER
```

or

```bash
sudo gpasswd -a $USER docker
```

```bash
newgrp docker
```
