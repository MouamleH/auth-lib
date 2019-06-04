package tech.enjaz.mouamle.securitylogin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.enjaz.mouamle.securitylogin.login.session.Session;
import tech.enjaz.mouamle.securitylogin.login.session.SessionData;
import tech.enjaz.mouamle.securitylogin.login.session.SessionProvider;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityLoginApplicationTests {

    @Autowired
    private SessionProvider sessionProvider;

    @Test
    public void contextLoads() {
        Account account = new Account();
        account.setId(0);
        account.setName("Hello?");

        SessionData<Account> data = new SessionData<>(account);

        Session session = sessionProvider.newSession(data, false);

        Optional<Session> oNewSession = sessionProvider.getSession(session.getId());

        if (oNewSession.isPresent()) {
            Session newSession = oNewSession.get();

            SessionData<Account> sessionData = newSession.getSessionData();
            System.out.println(sessionData.getData().getName());
        }
    }

}
