
# Task Management

It is a secured back-end task system that allows the admin/manager to distribute tasks among users, track the status of each task and write comments for each task specific to a particular user.

## Features
### Security

- This project has Authentication and autherization system handled by Spring Security
- User signup and login : User get JWT token after successful login which will be needed later in each request
- Access to secured end-points depends on the user's role (EMPLOYEE , ADMIN) and verification of the JWT token with this request.

### Admin Controll
- Admin can create and distribute tasks to specific employees,update, delete , get all tasks , get task by Id or title
- Admin can create comments for certain task and get all comments of one task
- Admin can get all employees

### Employee Services
- Employee can get his own tasks , update status of the task , can respon to admin's comments,

### Prerequisites
- Java 17
- Spring Boot 3.3.5
- Maven
- Postman
- IDE (Eclips)


