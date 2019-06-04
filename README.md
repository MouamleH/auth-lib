# Security Login
A library to handle user authentication (login, login checking)

# Usage
Basic usage details

## Session creation
In your login method you call your `SessionProvider.newSession()` implementation or `DefaultSessionProvider`
passing an instance of `SessionData` to get a session Object that you can get the id of and return to the user

## Authentication
You can just register the already implemented `DefaultSecurityInterceptor` <br>
Or implement `SecurityInterceptor` yourself 

Then you annotate the methods that requires the user to login with `@SecuredCall`
the default interceptor will throw `SessionNotFoundException` 
when the request mapped method gets called.

If you want to access the user session you can add 
an argument of type `Session` annotated with `@SessionContext` 
(the method needs to be annotated with `@SecuredCall`)

And then all you need in your request is a header called `token` with the sessionId as the value

# Examples
An example can be found in the tests.