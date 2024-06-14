docker run -d \
--name dogWalker_redis \
-p 6378:6379 \
--network dogWalker_mysql \
-d redis:latest