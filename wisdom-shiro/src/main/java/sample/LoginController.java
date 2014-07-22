package sample;

import model.User;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.*;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.wisdom.api.DefaultController;
import org.wisdom.api.annotations.Body;
import org.wisdom.api.annotations.Controller;
import org.wisdom.api.annotations.Route;
import org.wisdom.api.annotations.View;
import org.wisdom.api.http.HttpMethod;
import org.wisdom.api.http.Result;
import org.wisdom.api.templates.Template;

/**
 * Created by billey_b on 22/07/14.
 */
@Controller
public class LoginController extends DefaultController {

    @View("login")
    Template login;

    @Validate
    public void init() {
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


    @Route(method = HttpMethod.GET, uri = "/login")
    public Result loginForm() {
        return ok(render(login));
    }

    @Route(method = HttpMethod.POST, uri = "/login")
    public Result login(@Body User user) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());
        try {
            currentUser.login( token );
            //if no exception, that's it, we're done!
        } catch ( UnknownAccountException uae ) {
            //username wasn't in the system, show them an error message?
        } catch ( IncorrectCredentialsException ice ) {
            //password didn't match, try again?
        } catch ( LockedAccountException lae ) {
            //account for that username is locked - can't login.  Show them a message?
        }
        catch ( AuthenticationException ae ) {
        //unexpected condition - error?
        }

        return ok(render(login));
    }

}
