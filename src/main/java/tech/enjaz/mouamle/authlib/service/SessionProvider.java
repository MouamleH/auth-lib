package tech.enjaz.mouamle.authlib.service;

import tech.enjaz.mouamle.authlib.session.Session;
import tech.enjaz.mouamle.authlib.session.SessionData;

import java.util.List;
import java.util.Optional;

public interface SessionProvider<T> {

    /**
     * Creates a new session given the SessionData object
     *
     * @param sessionData  the session data holder
     * @return the newly created session
     */
    Session newSession(SessionData<T> sessionData);

    /**
     * Creates a new session given the session id and Session data object
     *
     * @param sessionId    the id that will be given to the session
     * @param sessionData  the session data holder
     * @return the newly created session
     */
    Session newSession(String sessionId, SessionData<T> sessionData);

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

    /**
     * Checks the role for a given session id
     * @param sessionId the id of the session
     * @param role the role to check against
     * @return true if the role matches, false otherwise
     */
    @Deprecated
    boolean checkRole(String sessionId, String role);

    /**
     * Checks the given session if it contains any of the roles in the list
     * @param sessionId the id of the session
     * @param roles the list of roles to check against
     * @return true if the session role is in the list, false otherwise
     */
    boolean checkRoles(String sessionId, List<String> roles);

}
