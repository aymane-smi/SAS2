FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD = aymane@123
ENV MYSQL_DATABASE = SAS2

COPY db.sql .

EXPOSE 3306