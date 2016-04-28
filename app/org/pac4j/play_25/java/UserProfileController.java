package org.pac4j.play_25.java;

import com.google.inject.Inject;
import org.pac4j.core.config.Config;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play_25.PlayWebContext;
import play.mvc.Controller;

/**
 * This controller is an easy way to get the user profile and the injected configuration.
 *
 * @author Jerome Leleu
 * @since 2.0.0
 */
public class UserProfileController<P extends CommonProfile> extends Controller {

    @Inject
    protected Config config;

    /**
     * Get the current user profile.
     *
     * @return the user profile
     */
    protected P getUserProfile() {
        final org.pac4j.play_25.PlayWebContext context = new org.pac4j.play_25.PlayWebContext(ctx(), config.getSessionStore());
        final ProfileManager manager = new ProfileManager(context);
        return (P) manager.get(true);
    }
}
