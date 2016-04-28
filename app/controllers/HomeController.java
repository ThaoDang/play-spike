package controllers;

import com.mysema.query.jpa.impl.JPAQuery;
import models.Person;
import models.QPerson;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
//    @RequiresAuthentication
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }
}
