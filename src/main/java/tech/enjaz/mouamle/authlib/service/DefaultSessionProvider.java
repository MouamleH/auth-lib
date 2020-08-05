package tech.enjaz.mouamle.authlib.service;

import tech.enjaz.mouamle.authlib.session.Session;
import tech.enjaz.mouamle.authlib.session.SessionData;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSessionProvider<T> implements SessionProvider<T> {

    private final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    /**
     * Creates a new session given the SessionData object
     *
     * @param sessionData the session data holder
     * @return the newly created session
     */
    @Override
    public Session newSession(SessionData<T> sessionData) {
        String sessionId = UUID.randomUUID().toString();
        return newSession(sessionId, sessionData);
    }

    /**
     * Creates a new session given the session id and Session data object
     *
     * @param sessionId   the id that will be given to the session
     * @param sessionData the session data holder
     * @return the newly created session
     */
    @Override
    public Session newSession(String sessionId, SessionData<T> sessionData) {
        Session session = new Session(sessionId, sessionData);
        sessions.put(sessionId, session);
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
        return Optional.ofNullable(sessions.get(sessionId));
    }

    /**
     * Deletes the given session
     *
     * @param sessionId the id of the session to be deleted.
     * @return the deleted session, null if no session was presenet
     */
    @Override
    public Session deleteSession(String sessionId) {
        return sessions.remove(sessionId);
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

    /**
     * Checks the role for a given session id
     *
     * @param sessionId the id of the session
     * @param role      the role to check against
     * @return true if the role matches, false otherwise
     */
    @Override
    public boolean checkRole(String sessionId, String role) {
        Optional<Session> oSession = getSession(sessionId);
        if(!oSession.isPresent()){
            return false;
        }

        Session session = oSession.get();
        return String.valueOf(role).equals(session.getSessionData().getRole());
    }

}
