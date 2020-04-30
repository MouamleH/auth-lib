package tech.enjaz.mouamle.authlib.session.exception;

import tech.enjaz.mouamle.authlib.session.Session;

public class SessionAlreadyExistsException extends RuntimeException {

    private final Session session;

    public SessionAlreadyExistsException(String message, Session session) {
        super(message);
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

}
