* OAuth 2.0

OAuth 2.0, which stands for “Open Authorization”, is a standard designed to allow a website or application to access resources hosted by
other web apps(i.e google, facebook, github etc.) on behalf of a user. It replaced OAuth 1.0 in 2012 and is now the de facto industry
standard for online authorization. OAuth 2.0 provides consented access and restricts actions of what the client app can perform on
resources on behalf of the user, without ever sharing the user's credentials.
So, what it meana is the application which uses OAuth dosen't have to manage or authorize the user they will be managed by the other web apps and 
this other authoriaztion app or server will be used as the security layer without giving credentials to the application.

OAuth 2.0 for Authorization
OAuth 2.0 is fundamentally about authorization. It lets a user grant a third-party application access to their resources without sharing credentials. The user authenticates with the service provider, and the service provider issues an access token that the third-party application can use to access specific resources.

OpenID Connect (OIDC) for Authentication
OIDC is built on top of OAuth 2.0 and is explicitly designed for authentication. It provides an ID token that includes information about the authenticated user. This allows applications to verify the user's identity and obtain basic profile information.

* Principles of OAuth2.0

-> It's an authorization protocol not authentication i.e it only used to grant access to use the api or access resources.
-> It uses acces tokens which is a piece of data that represents the authorization to access resources on the behalf of the end user.
-> It dosen't a specific format of access of token but JWT format is often used in most cases, which token issuer to include data in the token so these access token may have expiration date.


 ![OAuth 2.0 request flow diagram](https://github.com/GargSaurab/To-do-List-Project/blob/main/Project%20Image/Bouali%20Ali%20-%20OAuth2%20%26%20Spring%20boot%203%20%26%20Social%20login%20never%20been%20easier%20%5B2WNjmT2z7c4%20-%201079x607%20-%2010m24s%5D.png)

    
