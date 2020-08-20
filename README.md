[![Codacy Badge](https://api.codacy.com/project/badge/Grade/61d02e4a900f444f9cbf3731a022a927)](https://www.codacy.com/manual/MouamleH/security-login?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=MouamleH/auth-lib&amp;utm_campaign=Badge_Grade)
[![](https://jitpack.io/v/MouamleH/auth-lib.svg)](https://jitpack.io/#MouamleH/auth-lib)


# Auth Lib
A library to handle user authentication in spring-boot projects.

## Installation
[Jitpack](https://jitpack.io/#MouamleH/auth-lib/2.0.1)

## Usage
Basic usage details

### Authentication
Register the already implemented `DefaultSecurityInterceptor` passing to it the `SessionProvider` implementation, and the session id header name.

Or implement `SecurityInterceptor` yourself.

```java
// Defining beans for the session provider and the interceptor 
@Configuration
public class BeansConfig {

    @Bean
    public SessionProvider<Long> sessionProvider() {
        return new DefaultSessionProvider<>();
    }

    @Bean
    public SecurityInterceptor securityInterceptor(SessionProvider<Long> sessionProvider) {
        return new DefaultSecurityInterceptor("token", sessionProvider);
    }

}
```

```java
// Registering the security interceptor
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SecurityInterceptor securityInterceptor;

    @Autowired
    public WebConfig(SecurityInterceptor securityInterceptor) {
        this.securityInterceptor = securityInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(securityInterceptor);
    }

}
```

Then annotate the methods that requires the user to login with `@SecuredCall`

```java
@RestController
public class UserEndpoint {

    @SecuredCall
    @GetMapping("hello")
    public String hello() {
        return "hi";
    }

}
```

The default interceptor implementation will throw `SessionNotFoundException`
when the mapped request method gets called, and the token was missing or invalid,
you might wanna catch that exception

```java
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = SessionNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleSessionException(SessionNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
```

> If you are using roles, then the default implementation also throws `InvalidRoleException` if the role didn't match,
> you can handle it the same way

If you want to access the user session you can add 
an argument of type `Session` annotated with `@SessionContext` 
(the method needs to be annotated with `@SecuredCall`)

```java
@RestController
public class UserEndpoint {

    @SecuredCall
    @GetMapping("hello")
    public String hello(@SessionContext Session session) {
        return String.format("Last used: %s", session.getLastUsed());
    }

}
```

Then all you need in your request is the header name you passed to `DefaultSecurityInterceptor` with the session id as the value, and you're good to go.


### Session creation

In your login method you call your `SessionProvider.newSession()` implementation.
passing an instance of `SessionData` to get a session Object that you can get the id of and return to the user.

```java
// This code is an example and should not be used as a template
@RestController
@RequestMapping("auth")
public class AuthEndpoint {

    @PostMapping("login")
    public String login(@Valid @RequestBody LoginRequest request) {
        final Optional<Account> oAccount = accountRepo.findAccount(request.getUsername(), request.getPassword());

        if (!oAccount.isPresent()) {
            throw new BadRequestException("invalid credentials");
        }

        final Account account = oAccount.get();
        final Session session = sessionProvider.newSession(new SessionData<>(account.getId(), account.getRole().name()));

        return session.getId();
    }
   
}
```

### Static token

You can annotate any `Rest Contrller` method with `@SecuredCall(token = "")`  where `token` is your static token for the `DefaultSecurityInterceptor`  to check the header against. 
