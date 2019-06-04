package tech.enjaz.mouamle.securitylogin.login.session.exception;

public class SessionNotFoundException extends RuntimeException {

    private final String sessionId;

    public SessionNotFoundException(String message, String sessionId) {
        super(message);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

}
