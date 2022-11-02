# Notes App

Application for online notes/memos management
by Michal Wlodarczyk (2022)


## Description
Backend (mostly) app coded in Java (Spring framework)


### Technologies used
- Java 11
- Spring (incl. Spring Boot, Spring MVC, Spring REST, Spring Security)
- Hibernate
- MySQL and MySQL Workbench
- Gradle
- Thymeleaf (simple front-end)


### App functions/modules
- custom registration and login (incl. fields validation)
- OAuth2 authentication with Google
- Separate roles for users and admin (admin can access and delete all notes)
- BCrypt password encryption
- all CRUD operations for notes
- basic frontend using Thymeleaf

JUnit and Mockito tests included in the app.


DB pre-populated with the following seed records:

| user id | password | role |
|---------|----------|------|
| mike    | pass123  | ROLE_ADMIN |
| marty   | pass123  | ROLE_USER |
| jenny   | pass123  | ROLE_USER |


### How to run and use the app

1. Create the seed database - run SQL script included in the project with MySQL Workbench (sql-scripts/NotesAppDatabase.sql)
2. Run the main app (src/main/java/io/github/miwlodar/NotesApplication.java) using JDK 11
3. Use one of the seed accounts listed above or log in with Google
4. Enjoy the mw_notes_app equipped with all CRUD operations and more!*

*Oauth2 Google credentials available upon request
