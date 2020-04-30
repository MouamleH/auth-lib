package tech.enjaz.mouamle.authlib.session;

@SuppressWarnings("rawtypes")
public class Session {

    private final String id;
    private long lastUsed;

    private SessionData<?> sessionData;

    public Session(String sessionId, SessionData data) {
        this.id = sessionId;
        this.lastUsed = System.currentTimeMillis();
        this.sessionData = data;
    }

    public String getId() {
        return id;
    }

    public long getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(long lastUsed) {
        this.lastUsed = lastUsed;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public void setSessionData(SessionData<?> sessionData) {
        this.sessionData = sessionData;
    }

}
