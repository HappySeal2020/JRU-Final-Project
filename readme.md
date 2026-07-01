<h1>Учебный проект "Книги"</h1>


1. Описание
2. Technologies
3. How to run
4. Services
5. Endpoints

## 1. Описание
Система позволяет через REST-запросы выполнять операции:
- CRUD издательств;
- CRUD авторов;
- CRUD книг.  
 Книга имеет одно издательство и одного или несколько авторов.  
- предусмотрены роли: ADMIN и USER (пароли admin и user соответственно). Читать могут пользователи любой из этих ролей, вносить изменения - только пользователи с ролью ADMIN. 

Для хранения данных используется PostgreSQL. Возможны два варианта запуска:  
-База уже установлена локально;  
-База будет поднята в Docker-контейнере.  

## 2. Technologies
Java 21    
Spring Boot 3    
Spring Data JPA  
Spring Security  
PostgreSQL  
FlyWay  
Docker  
JUnit 5 
TestContainers  
Rest Assured  

## 3. How to run
git clone git@github.com:HappySeal2020/bank_REST.git ???   
cd bank_REST  ???

_Run all in Docker:_  
(if first time) docker compose up --build    
(if not first time) docker compose up -d    

_Run DB in container (same as local Postgres):_  
docker compose up db -d  

to stop: docker compose down

_Manual run in Intellij Idea_  
-DB must be active  
-set active profile to dev  

_tests and build:_   
mvn clean install

_Run without docker:_  
DB must be active  
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"     

_Update doc/openapi.yaml:_  
1. start application: mvn spring-boot:run "-Popenapi" "-Dspring-boot.run.profiles=dev"  
2. in other console: mvn springdoc-openapi:generate "-Popenapi" "-Dspring-boot.run.profiles=dev" 

## 4. Services
application http://localhost:8080  
Postgres localhost:5432

## 5. Endpoints
| N  | Адрес                                                                 | Метод  | Body                                                                                                                                                                                                                                   | Роль  | Описание               |
|----|-----------------------------------------------------------------------|--------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------|------------------------|
| 1  | http://localhost:8080/test-task-cd/api/publishers?name=питер&size=3   | GET    |                                                                                                                                                                                                                                        | любая | Список издательств     |
| 2  | http://localhost:8080/test-task-cd/api/publishers                     | POST   | {"name": "Азимут", "site": "www.aziko.ru" }                                                                                                                                                                                            | ADMIN | Добавляем издательство |
| 3  | http://localhost:8080/test-task-cd/api/publishers/14                  | PUT    | {"name": "Азимут-супер", "site": "www.aziko-super.ru" }                                                                                                                                                                                | ADMIN | Изменяем издательство  |
| 4  | http://localhost:8080/test-task-cd/api/publishers/14                  | DELETE |                                                                                                                                                                                                                                        | ADMIN | Удаляем издательство   |
| 5  | http://localhost:8080/test-task-cd/api/authors?size=10&name=дж        | GET    |                                                                                                                                                                                                                                        | любая | Список авторов         |
| 6  | http://localhost:8080/test-task-cd/api/authors                        | POST   | {"name": "Джек Лондон"}                                                                                                                                                                                                                | ADMIN | Добавляем автора       |
| 7  | http://localhost:8080/test-task-cd/api/authors/35                     | PUT    | {"name": "Jack London"}                                                                                                                                                                                                                | ADMIN | Изменяем автора        |
| 8  | http://localhost:8080/test-task-cd/api/authors/35                     | DELETE |                                                                                                                                                                                                                                        | любая | Удаляем автора         |
| 9  | http://localhost:8080/test-task-cd/api/books?size=4&page=2&name=java  | GET    |                                                                                                                                                                                                                                        | ADMIN | Список книг            |
| 10 | http://localhost:8080/test-task-cd/api/books                          | POST   | {"name": "Java. Библиотека профессионала, том 2. Расширенные средства программирования, 17-е издание", "authorIds": [3], "printYear": 2025, "publisherId": 4, "bbk": "32.973.26-018.2.75", "isbn": "978-5-9909445-0-3", "pages": 500}  | ADMIN | Добавляем книгу        |
| 11 | http://localhost:8080/test-task-cd/api/books/23                       | PUT    | {"name": "Java. Библиотека профессионала, том 2. Расширенные средства программирования, 18-е издание", "authorIds": [3], "printYear": 2026, "publisherId": 4, "bbk": "32.973.26-018.2.75", "isbn": "978-5-9909445-0-3", "pages": 1100} | ADMIN | Изменяем книгу         |
| 12 | http://localhost:8080/test-task-cd/api/books/23                       | DELETE |                                                                                                                                                                                                                                        | ADMIN | Удаляем книгу          |
| 13 | http://localhost:8080/test-task-cd/swagger-ui/index.html              |        |                                                                                                                                                                                                                                        | любая | Swagger                |