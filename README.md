# Notes App

Application for online notes/memos management
by Michal Wlodarczyk (2022)


## Description
Backend (mostly) app coded in Java (Spring framework)


### Technologies used
- Java
- Spring (incl. Spring Boot, Spring MVC, Spring REST, Spring Security)
- MySQL
- Maven
- Thymeleaf


### App functions/modules
- custom registration and login (incl. fields validation)
- OAuth2 authentication with Google and Facebook
- Separate roles for users and admin
- BCrypt password encryption
- all CRUD operations for notes
- basic frontend using Thymeleaf (REST API calls also possible)


DB pre-populated with the following seed records:

| user id | password | role |
|---------|----------|------|
| mike    | pass123  | ROLE_ADMIN |
| marty   | pass123  | ROLE_USER |
| jenny   | pass123  | ROLE_USER |

SQL script included in the project.