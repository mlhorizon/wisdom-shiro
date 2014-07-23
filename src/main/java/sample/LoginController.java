package sample;

import model.User;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.DefaultSecurityManager;
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

    @View("access")
    Template accessDenied;

    /**
     * initialize shiro, by adding some roles and users
     */
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


    /**
     * the login page
     * @return
     */
    @Route(method = HttpMethod.GET, uri = "/")
    public Result loginForm() {
        return ok(render(login));
    }

    /**
     * Called when you submit the login form
     * @param user
     * @return
     */
    @Route(method = HttpMethod.POST, uri = "/login")
    public Result login(@Body User user) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        //that catch exceptions and do all the same things but it's just for the demo
        try {
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            return ok(render(accessDenied));
        } catch (IncorrectCredentialsException ice) {
            return ok(render(accessDenied));
        } catch (LockedAccountException lae) {
            return ok(render(accessDenied));
        } catch (AuthenticationException ae) {
            return ok(render(accessDenied));
        }

        return redirect("protected");
    }

    /**
     * logout the current user
     * @return
     */
    @Route(method = HttpMethod.GET, uri = "/logout")
    public Result logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return redirect("login");
    }

}
