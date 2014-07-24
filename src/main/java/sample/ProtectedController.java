package sample;

import helper.UserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wisdom.api.DefaultController;
import org.wisdom.api.annotations.Controller;
import org.wisdom.api.annotations.Route;
import org.wisdom.api.annotations.View;
import org.wisdom.api.http.HttpMethod;
import org.wisdom.api.http.Result;
import org.wisdom.api.security.Authenticated;
import org.wisdom.api.templates.Template;

/**
 * TODO write documentation<br>
 *<br>
 * Created at 23/07/2014 09:04.<br>
 *
 * @author Bastien
 *
 */
@Controller
public class ProtectedController extends DefaultController {
    /**
     * The famous {@link org.slf4j.Logger}
     */
    private static final Logger logger = LoggerFactory.getLogger(ProtectedController.class);

    @View("protected")
    Template protectedView;

    /**
     * this route is accessible only if you're logged. Try to visit /protected before login
     * @return
     */
    @Authenticated("my-authenticator")
    @Route(method = HttpMethod.GET, uri = "/protected")
    public Result protectedArea() {
        return ok(render(protectedView, "user", new UserHelper()));
    }
}
