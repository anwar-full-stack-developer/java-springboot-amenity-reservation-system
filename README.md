# java-springboot-amenity-reservation-system

Amenity reservation CRUD REST API, Reservation Capacity CRUD REST API, Basic Authentication, User login, Add New User, user role, end-point security based on user roles  implementation with language java, spring boot version 3, spring security 6, MySQL 


## Installation Pre-Requirements
- Download and install JAVA (JDK) v17
- Download and install Git 
- Download and install IDE Eclipse or any other your choice (e.g. vscode, eclipse, netbeans)
- Install MySql or MariaDb.


## Setup
- Clone this Git Repository. Or download manually.
- Open project using Eclipse. Considering Eclipse IDE in this case.
- From Eclipse IDE project explorer > RUN As > Maven Build.
- Run the project: after successful build, system will open `http://localhost:8080/` in your Browser in new Tab. If does not open new tab ,then browse manually
- Browse and Navigate the API manually. Details bellow.


## Features
- routing
- Data validation
- Login, AddUser
- CRUD Capacity
- CRUD Reservation
- End-point Security ....


## Sample End-points / Public Access
- Base URL `http://localhost:8080/`
- GET  `http://localhost:8080/api/auth/generate-token`
- POST `http://localhost:8080/api/auth/add-new-user`
- ....

## Secured Access
- GET `http://localhost:8080/basic-auth/user/profile`
- GET `http://localhost:8080/basic-auth/admin/profile`
- ... .
- GET `http://localhost:8080/api/reservation/list`
- POST `http://localhost:8080/api/reservation/add-new-reservation`
- PUT `http://localhost:8080/api/reservation/update/{id}`
- GET `http://localhost:8080/api/reservation/get-reservation/{id}`
- DELETE `http://localhost:8080/api/reservation/delete/{id}`
-... .
- GET `http://localhost:8080/api/reservation-capacity/list`
- POST `http://localhost:8080/api/reservation-capacity/create`
- PUT `http://localhost:8080/api/reservation-capacity/update/{id}`
- DELETE `http://localhost:8080/api/reservation-capacity/delete/{id}`
- GET `http://localhost:8080/api/reservation-capacity/read/{id}`
- ... .



# Conclusion
For future development make a job offer / hire me. 

