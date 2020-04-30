package tech.enjaz.mouamle.authlib.service;

import tech.enjaz.mouamle.authlib.session.Session;
import tech.enjaz.mouamle.authlib.session.SessionData;
import tech.enjaz.mouamle.authlib.session.exception.SessionAlreadyExistsException;

import java.util.Optional;

public interface SessionProvider<T> {

    /**
     * Creates a new session given the SessionData object
     *
     * @param sessionData  the session data holder
     * @param checkPresent if set to true the code will call (the session data holder #equals method) to perform a check against the stored sessions list
     * @return the newly created session
     * @throws SessionAlreadyExistsException if checkPresent was set to true and a session with the same data is present
     */
    Session newSession(SessionData<T> sessionData, boolean checkPresent) throws SessionAlreadyExistsException;

    /**
     * Creates a new session given the session id and Session data object
     *
     * @param sessionId    the id that will be given to the session
     * @param sessionData  the session data holder
     * @param checkPresent if set to true the code will call (the session data holder #equals method) to perform a check against the stored sessions list
     * @return the newly created session
     * @throws SessionAlreadyExistsException if checkPresent was set to true and a session with the same data is present
     */
    Session newSession(String sessionId, SessionData<T> sessionData, boolean checkPresent) throws SessionAlreadyExistsException;

    /**
     * Gets the stored session given the sessionId
     *
     * @param sessionId the id of the session
     * @return the Session
     */
    Optional<Session> getSession(String sessionId);


    /**
     * Deletes the given session
     *
     * @param sessionId the id of the session to be deleted.
     * @return the deleted session, null if no session was presenet
     */
    Session deleteSession(String sessionId);

    /**
     * Checks if there was a session with the given id
     *
     * @param sessionId the id of the session
     * @return true if there is a session, false otherwise
     */
    boolean isSessionPresent(String sessionId);

}
