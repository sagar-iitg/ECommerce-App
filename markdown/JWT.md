JWT -- JSON WEB TOKEN

JWT is mostly used for securing rest-apis

It follows stateless authentication mechanism.


x.y.z

Header , payload , signature
(algo+type)--Header
payload -- (data)
signature -(encode) header andd payload + secret key



For any incoming request this filter class gets executed.
It checks if the request has a valid JWT Token. if it has a 
valid JWTToken then it sets the authentication in the context,
to specify that the current user is authenticated.





 




