```

docker network create bootApp

docker run -d --name mysqldb -p 3308:3306 --network=bootApp -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=electronic_store mysql

docker build -t ecom .
docker run -it --name store -p 9091:9090 --network=bootApp  -e MYSQL_HOST=mysqldb -e MYSQL_PORT=3306 

```
