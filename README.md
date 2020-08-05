[![Codacy Badge](https://api.codacy.com/project/badge/Grade/61d02e4a900f444f9cbf3731a022a927)](https://www.codacy.com/manual/MouamleH/security-login?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=MouamleH/auth-lib&amp;utm_campaign=Badge_Grade)

# Auth Lib
A library to handle user authentication in spring-boot projects.

## Installation
[Jitpack](https://jitpack.io/#MouamleH/auth-lib/1.0.0)

## Usage
Basic usage details

### Authentication
Register the already implemented `DefaultSecurityInterceptor` passing to it the `SessionProvider` implementation, and the session id header name.

Or implement `SecurityInterceptor` yourself 

Then you annotate the methods that requires the user to login with `@SecuredCall`
the default interceptor will throw `SessionNotFoundException` 
when the mapped request method gets called.

If you want to access the user session you can add 
an argument of type `Session` annotated with `@SessionContext` 
(the method needs to be annotated with `@SecuredCall`)

Then all you need in your request is the header name you passed to `DefaultSecurityInterceptor` with the session id as the value, and you're good to go.


### Session creation

In your login method you call your `SessionProvider.newSession()` implementation.
passing an instance of `SessionData` to get a session Object that you can get the id of and return to the user.


### Static token

You can annotate any `Rest Contrller` method with `@SecuredCall(token = "")`  where `token` is your static token for the `DefaultSecurityInterceptor`  to check the header against. 
