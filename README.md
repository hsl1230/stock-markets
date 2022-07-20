# stock-markets

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
