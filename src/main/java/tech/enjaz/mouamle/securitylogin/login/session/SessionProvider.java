package tech.enjaz.mouamle.securitylogin.login.session;

import tech.enjaz.mouamle.securitylogin.login.session.exception.SessionAlreadyExistsException;

import java.util.Optional;

public interface SessionProvider {

    /**
     * Creates a new session given the SessionData object
     *
     * @param sessionData  the session data holder
     * @param checkPresent if set to true the code will call (the session data holder #equals method) to perform a check against the stored sessions list
     * @return the newly created session
     * @throws SessionAlreadyExistsException if checkPresent was set to true and a session with the same data is present
     */
    Session newSession(SessionData sessionData, boolean checkPresent) throws SessionAlreadyExistsException;

    /**
     * Gets the stored session given the sessionId
     *
     * @param sessionId the id of the session
     * @return the Session
     */
    Optional<Session> getSession(String sessionId);

    /**
     * Checks if there was a session with the given id
     *
     * @param sessionId the id of the session
     * @return true if there is a session, false otherwise
     */
    boolean isSessionPresent(String sessionId);

}
