This API backend project using Spring boot With Spring Security JWT , Spring Boot 3.3.5 , Java 17
  - pom.xml ==> dependencies : Spring Web , Spring Data JPA , MySQL Driver , Spring boot DevTools , spring security , jsonwebtoken
  - Packages of the project :
    * Utils ==> contains JWT Utils class used to :
       - generate token based on the information of logged user , set Subject(user name) , set Expiration date of token , set secret 
         Key 
          , set claims(username and password from User details which come from database)
       - setSigningKey() method : convert the encoded secret key(TOKEN_SECRET) into a byte array using Base64 decoding. Then, using 
          Keys.hmacShaKeyFor() , we’ll convert the byte array into the Key 
       - isTokenValid() which compare between token and userDetails
       - Also methods like:  getUsernameFromToken() , isTokenEpired() , getUserFromToken() 

    * Configuration : 
       - JwtAuthenticationFilter ( each logged user has specific token which must be sent with each request ,this filter 
          check out existence this token and it's validation  , if not exist or invalid , the request will be cancled  )
       - WebSecurityConfiguration (to determine which requests are allowed for only admin, only employees or both or available for any 
         one )
           - WebSecurity  used to create the FilterChain that performs the web based security for Spring Security and this 
             configuration is imported when using EnableWebSecurity annotation
           - AuthenticationProvider : responsible for authenticating a user's credentials and returning an Authentication object that 
             represents the authenticated user
           - Password Encoder : storing passwords securely by integrating with PasswordEncoder
           - AuthenticationManager : handle and verify user credentials
    * Entities ==> entity for each os (Task , comment , User )
    * Dto ==> for each of entity and also for login (loginRequest and loginResponse) and signupDto
    * Repository ==> each entity must has repository for crud operations (TaskRepository , CommentRepository ,taskRepository)
    * Enums ==> UserRole which contains two roles(ADMIN , EMPLOYEE) and TaskStatus for different status of the task 
       (PENDING , INPROGRESS, COMPLETED, DEFERRED, CANCELED)
    *Services ==> Three Interfaces( AdminService , EmployeeService , AuthService) and three implemented classes ( AdminServiceImpl , 
      EmployeeServiceImpl , AuthServiceImpl)
      - AuthServiceImpl ==> create admin account and signup 
      - EmployeeServiceImpl ==> get employee tasks , update task status , get user from token
      - AdminServiceImpl ==> get all users , create task , get all tasks , delete task , get task by id , search task by title,  
        update task , createComment , getCommentsByTaskId, mapStringToStatus() which convert incoming string status as TaskStatus is 
        enum
    * Controllers ==> AuthControllers , AdminControllers , EmployeeControllers 
       each controller must has @CrossOrigin("*") to connect later with front end (Angualr)
  
      [Task Management.postman_collection.json]

