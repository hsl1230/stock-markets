# stock-markets

The project was developed on Ubuntu 22.04 LTS

## Highlights

- Springfox is used to provide a swagger-ui
- All endpoints created can be tested through the swagger-ui
- File upload is supported in the swagger-ui
- Unit tests are added and passed
- Api test is set up as a gradle task, ready to add more tests
- MongoRepository is used to access mongo db
- Docker compose is used to set up the dev env

## What to do next?

- Add more unit tests to cover the code
- A framework to handle exceptions, logs, securities
- Add user authentication and authorization service to do login, access token generation, refreshing and validation.
- Add privilege service to manage and check the permissions
- Add security(authentication and authorization) through access token to the micro services
- Add spring cloud services(Config service, Netflix service and Vault Service)

## Build the project together with unit test and api test

```bash
./gradlew clean build
```

## Execute unit tests

```bash
./gradlew test
```

## Execute api tests

```bash
./gradlew apiTest
```

## Clone the source code from github

```bash
git clone https://github.com/hsl1230/stock-markets.git
```

## Launch up the application

### Pull docker compose images from internet

- Change directory to the folder stock-markets
- Get the containers from internet by executing `docker compose pull`

### Build docker compose images from your local

```bash
docker compose build
```

- Start up services by executing `docker compose up -d`
- Launch up swagger-ui of the micro services by open the link(http://localhost:25000/swagger-ui/index.html)
- Launch up mongo-express by open the link(http://localhost:8081)

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
