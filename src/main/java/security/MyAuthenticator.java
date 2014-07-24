package security;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wisdom.api.http.Context;
import org.wisdom.api.http.Result;
import org.wisdom.api.http.Results;
import org.wisdom.api.security.Authenticator;

/**
 * TODO write documentation<br>
 *<br>
 * Created at 17/07/2014 17:03.<br>
 *
 * @author Bastien
 *
 */
@Component
@Provides
@Instantiate
public class MyAuthenticator implements Authenticator {
    /**
     * The famous {@link org.slf4j.Logger}
     */
    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticator.class);

    @Override
    public String getName() {
        return "my-authenticator";
    }

    /**
     * just check if the current user is logged in
     * @param context
     * @return
     */
    @Override
    public String getUserName(Context context) {
        Subject currentUser = SecurityUtils.getSubject();

        if (currentUser.isAuthenticated()) {
            return currentUser.toString();
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Context context) {
        return Results.ok("Your are not authenticated !");
    }
}
