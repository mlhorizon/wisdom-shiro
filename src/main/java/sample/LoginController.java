package sample;

import model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
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
            context().flash().error("Unknown account");
            return loginForm();
        } catch (IncorrectCredentialsException ice) {
            context().flash().error("Wrong password");
            return loginForm();
        } catch (LockedAccountException lae) {
            context().flash().error("Account locked");
            return loginForm();
        } catch (AuthenticationException ae) {
            context().flash().error("some error");
            return loginForm();
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
        return redirect("/");
    }
}
