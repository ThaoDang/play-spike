package controllers;

import javax.inject.*;
import javax.naming.NamingException;

import model.User;
import play.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import services.ActiveDirectoryService;
import services.Counter;

import java.util.concurrent.ExecutionException;

/**
 * This controller demonstrates how to use dependency injection to
 * bind a component into a controller class. The class contains an
 * action that shows an incrementing count to users. The {@link Counter}
 * object is injected by the Guice dependency injection system.
 */
@Singleton
public class LoginController extends Controller {

    private FormFactory formFactory;

    @Inject
    public LoginController(FormFactory formFactory) {

        this.formFactory = formFactory;
    }

    /**
     * An action that responds with the {@link Counter}'s current
     * count. The result is plain text. This action is mapped to
     * <code>GET</code> requests with a path of <code>/count</code>
     * requests by an entry in the <code>routes</code> config file.
     */
    public Result login() throws NamingException, ExecutionException, InterruptedException {
        Form<User> form = formFactory.form(User.class);
        User user = form.bindFromRequest().get();
        boolean authenticated = ActiveDirectoryService.authenticate(user.getUserName(), user.getPassword()).get();
        return authenticated ? ok("Logged In!!") : forbidden("Failed Login");
    }

}
