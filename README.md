This API backend project using Spring boot With Spring Security JWT , It's consists of three sections:
- Auth section : contain login and signup  , JWT Utils , JWTAuthenticationFilter
- configuration : JwtAuthenticationFilter ( each logged user has specific token which must be sent with each request ,this filter check out existence this token and it's validation  , if not exist or invalid , the request will be cancled  )
- , WebSecurityConfiguration (to determine which requests are allowed for adminonly , employees only or both or available for any one )
- Admin section : control employees , creat tasks and give each user specific task and communicate with employees through comments for each task
- Employee section : can register , show all his/her tasks , update status the task after finish the work , publish comments
