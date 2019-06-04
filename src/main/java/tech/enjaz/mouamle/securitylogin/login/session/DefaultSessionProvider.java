package tech.enjaz.mouamle.securitylogin.login.session;

import org.springframework.stereotype.Component;
import tech.enjaz.mouamle.securitylogin.login.session.exception.SessionAlreadyExistsException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class DefaultSessionProvider implements SessionProvider {

    private final Map<String, Session> sessions;

    public DefaultSessionProvider() {
        this.sessions = new HashMap<>();
    }

    /**
     * Creates a new session given the SessionData object
     *
     * @param sessionData  the session data holder
     * @param checkPresent if set to true the code will call (the session data holder #equals method) to perform a check against the stored sessions list
     * @return the newly created session
     * @throws SessionAlreadyExistsException if checkPresent was set to true and a session with the same data is present
     */
    @Override
    public Session newSession(SessionData sessionData, boolean checkPresent) throws SessionAlreadyExistsException {
        String sessionId = UUID.randomUUID().toString();

        Session session = new Session(sessionId, sessionData);
        if (checkPresent) {
            Optional<Session> otherSession = sessions.values().stream().filter(other -> session.getSessionData().getData().equals(other.getSessionData().getData())).findFirst();
            if (otherSession.isPresent()) {
                throw new SessionAlreadyExistsException("A Session with the same data is already present", otherSession.get());
            }
        }
        sessions.put(session.getId(), session);
        return session;
    }

    /**
     * Gets the stored session given the sessionId
     *
     * @param sessionId the id of the session
     * @return the Session
     */
    @Override
    public Optional<Session> getSession(String sessionId) {
        Session session = sessions.get(sessionId);
        return Optional.ofNullable(session);
    }

    /**
     * Checks if there was a session with the given id
     *
     * @param sessionId the id of the session
     * @return true if there is a session, false otherwise
     */
    @Override
    public boolean isSessionPresent(String sessionId) {
        return sessions.containsKey(sessionId);
    }

}
