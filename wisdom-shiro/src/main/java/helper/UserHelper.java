package helper;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO write documentation<br>
 *<br>
 * Created at 22/07/2014 14:54.<br>
 *
 * @author Bastien
 *
 */
public class UserHelper {
    /**
     * The famous {@link org.slf4j.Logger}
     */
    private static final Logger logger = LoggerFactory.getLogger(UserHelper.class);

    public boolean isAdmin() {
        return SecurityUtils.getSubject().hasRole("ADMIN");
    }

    public boolean isGuest() {
        return SecurityUtils.getSubject().hasRole("GUEST");
    }

}
