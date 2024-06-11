docker run -d \
--name dogWalker_mysql \
-e MYSQL_ROOT_PASSWORD="dogWalker" \
-e MYSQL_USER="dogWalker" \
-e MYSQL_PASSWORD="dogWalker" \
-e MYSQL_DATABASE="dogWalker" \
-p 3307:3306 \
--network dogWalker_mysql mysql:latest