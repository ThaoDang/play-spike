package controllers;

import javax.inject.*;
import javax.naming.NamingException;

import models.User;
import org.ldaptive.LdapException;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import services.ActiveDirectoryService;
import services.Counter;
import services.PersonService;
import views.html.login;
import views.html.welcome;
import play.db.jpa.Transactional;

import java.util.concurrent.ExecutionException;

/**
 * This controller demonstrates how to use dependency injection to
 * bind a component into a controller class. The class contains an
 * action that shows an incrementing count to users. The {@link Counter}
 * object is injected by the Guice dependency injection system.
 */
@Singleton
public class LoginController extends Controller {

    @Inject
    private FormFactory formFactory;
    @Inject
    private ActiveDirectoryService activeDirectoryService;
    @Inject
    private PersonService personService;

    public Result loginForm() {
        return ok(login.render("Please Login"));
    }

    /**
     * An action that responds with the {@link Counter}'s current
     * count. The result is plain text. This action is mapped to
     * <code>GET</code> requests with a path of <code>/count</code>
     * requests by an entry in the <code>routes</code> config file.
     */
    @Transactional
    public Result login() throws NamingException, ExecutionException, InterruptedException {
        Form<User> form = formFactory.form(User.class);
        User user = form.bindFromRequest().get();
        boolean authenticated = false;
        try {
            authenticated = activeDirectoryService.authenticateLdap(user.getUsername(), user.getPassword());
        } catch (LdapException e) {
            e.printStackTrace();
            internalServerError(e.getMessage());
        }
        if(authenticated){
            session().clear();
            session("username", user.getUsername());
        }

        return authenticated ? ok("Logged In!!") : forbidden("Failed Login");


    }
}
