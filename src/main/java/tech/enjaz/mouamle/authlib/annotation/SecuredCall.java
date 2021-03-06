package tech.enjaz.mouamle.authlib.annotation;

import tech.enjaz.mouamle.authlib.service.SessionProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecuredCall {

    String DEFAULT_STATIC = "DEFAULT_STATIC";
    String DEFAULT_ROLE = "NO_ROLE";

    /**
     * Used to declare a static token that will be used instead of going to the {@link SessionProvider}. <br/>
     * If left on default the token in the header will go through the {@link SessionProvider} implementation
     *
     * @return the static token
     */
    String token() default DEFAULT_STATIC;

    /**
     * Used for checking the token role.
     * @return the token role
     */
    @Deprecated
    String role() default DEFAULT_ROLE;

    String[] roles() default {};

}
