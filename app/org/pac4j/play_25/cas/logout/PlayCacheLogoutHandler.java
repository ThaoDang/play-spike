/*
  Copyright 2012 - 2015 pac4j organization

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.pac4j.play_25.cas.logout;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.cas.logout.NoLogoutHandler;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play_25.PlayWebContext;
import org.pac4j.play_25.store.PlayCacheStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.cache.Cache;

/**
 * This class handles logout requests from a CAS server using the Play Cache and the {@link PlayCacheStore}.
 * 
 * @author Jerome Leleu
 * @since 1.1.0
 */
public class PlayCacheLogoutHandler extends NoLogoutHandler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public PlayCacheLogoutHandler() {}

    @Deprecated
    public PlayCacheLogoutHandler(final PlayCacheStore playCacheStore) {
        logger.warn("It is no longer necessary to provide the playCacheStore to initialize the PlayCacheLogoutHandler");
    }

    public void destroySession(WebContext context) {
        final org.pac4j.play_25.PlayWebContext webContext = (org.pac4j.play_25.PlayWebContext) context;
        final String logoutRequest = context.getRequestParameter("logoutRequest");
        logger.debug("logoutRequest: {}", logoutRequest);
        final String ticket = StringUtils.substringBetween(logoutRequest, "SessionIndex>", "</");
        logger.debug("extract ticket: {}", ticket);
        final String sessionId = (String) Cache.get(ticket);
        Cache.remove(ticket);
        webContext.getJavaSession().put(Pac4jConstants.SESSION_ID, sessionId);
        final ProfileManager profileManager = new ProfileManager(webContext);
        profileManager.remove(true);
    }

    public void recordSession(WebContext context, String ticket) {
        logger.debug("ticket: {}", ticket);
        final org.pac4j.play_25.PlayWebContext webContext = (org.pac4j.play_25.PlayWebContext) context;
        final PlayCacheStore playCacheStore = (PlayCacheStore) webContext.getSessionStore();
        final String sessionId = playCacheStore.getOrCreateSessionId(webContext);
        logger.debug("save sessionId: {}", sessionId);
        Cache.set(ticket, sessionId, playCacheStore.getProfileTimeout());
    }
}
