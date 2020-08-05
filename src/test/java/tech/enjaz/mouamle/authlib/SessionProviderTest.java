package tech.enjaz.mouamle.authlib;

import org.junit.Test;
import tech.enjaz.mouamle.authlib.service.DefaultSessionProvider;
import tech.enjaz.mouamle.authlib.service.SessionProvider;
import tech.enjaz.mouamle.authlib.session.Session;
import tech.enjaz.mouamle.authlib.session.SessionData;

import java.util.Optional;

public class SessionProviderTest {

    private final SessionProvider<String> sessionProvider = new DefaultSessionProvider<>();

    @Test
    public void sessionProviderTest() {
        final SessionData<String> sessionData = new SessionData<>("USER1", "USER");
        final Session newSession = sessionProvider.newSession(sessionData);

        assert newSession != null;
        assert newSession.getId() != null;

        assert !sessionProvider.isSessionPresent("INVALID ID");
        assert sessionProvider.isSessionPresent(newSession.getId());

        final Optional<Session> oSession = sessionProvider.getSession(newSession.getId());
        assert oSession.isPresent();

        final Session session = oSession.get();
        assert session.getSessionData().equals(sessionData);

        assert !sessionProvider.checkRole(session.getId(), "ADMIN");
        assert sessionProvider.checkRole(session.getId(), "USER");

    }

}
