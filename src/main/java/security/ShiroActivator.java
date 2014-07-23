package security;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO write documentation<br>
 *<br>
 * Created at 23/07/2014 13:58.<br>
 *
 * @author Bastien
 *
 */
@Component
@Instantiate
public class ShiroActivator {
    /**
     * The famous {@link org.slf4j.Logger}
     */
    private static final Logger logger = LoggerFactory.getLogger(ShiroActivator.class);

    /**
     * initialize shiro, by adding some roles and users
     */
    @Validate
    private void start() {
        Ini ini = new Ini();
        Ini.Section usr = ini.addSection(IniRealm.USERS_SECTION_NAME);
        Ini.Section roles = ini.addSection(IniRealm.ROLES_SECTION_NAME);

        roles.put("ADMIN", "*");
        roles.put("GEST", "*");

        usr.put("admin", "admin, ADMIN");
        usr.put("guest", "guest, GUEST");

        org.apache.shiro.mgt.SecurityManager securityManager = new DefaultSecurityManager(new IniRealm(ini));
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Invalidate
    private void stop() {
        SecurityUtils.setSecurityManager(null);
    }
}
