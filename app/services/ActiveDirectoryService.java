package services;

import org.ldaptive.*;
import org.ldaptive.auth.*;
import org.ldaptive.control.ResponseControl;
import play.api.Play;
import play.libs.F;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

/**
 * Created by thaodang on 26/4/16.
 */
public class ActiveDirectoryService {

    public static final String ldapURL = "ldap://127.0.0.1:389";
    public static final String domainName =   "dc=localhost,dc=com";
    public static final int timeout = 1;
//
//    public static final String ldapURL = Play._currentApp().configuration().getString("ActiveDirectory.url", null).get();
//    public static final String domainName =   Play._currentApp().configuration().getString("ActoveDirectory.DomainName", null).get();
//    public static final int timeout =         Integer.parseInt(Play._currentApp().configuration().getInt("ActoveDirectory.timeout").get().toString());

    public CompletableFuture<Boolean> authenticate(String username, String password) throws AuthenticationException, CommunicationException, NamingException {

        Hashtable<String, String> env = new Hashtable<String,String>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put("com.sun.jndi.ldap.connect.timeout", ""+(timeout*1000));
        env.put(Context.PROVIDER_URL, ldapURL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn="+username+","+domainName);
        env.put(Context.SECURITY_CREDENTIALS, password);

        new InitialDirContext(env);
        return CompletableFuture.completedFuture(Boolean.TRUE);
    }

    public Boolean authenticateLdap(String username, String password) throws LdapException {
        ConnectionConfig connConfig = new ConnectionConfig("ldap://directory.ldaptive.org");
        connConfig.setUseStartTLS(true);

        SearchDnResolver dnResolver = new SearchDnResolver(new DefaultConnectionFactory(connConfig));
        dnResolver.setBaseDn(domainName);

        BindAuthenticationHandler authHandler = new BindAuthenticationHandler(new DefaultConnectionFactory(connConfig));
        Authenticator auth = new Authenticator(dnResolver, authHandler);
        AuthenticationResponse response = auth.authenticate(
                new AuthenticationRequest(username, new Credential(password)));
        if (response.getResult()) { // authentication succeeded
            LdapEntry entry = response.getLdapEntry(); // read mail and sn attributes
            return true;
        } else { // authentication failed
            String msg = response.getMessage(); // read the failure message
            ResponseControl[] ctls = response.getControls(); // read any response controls
        }
        return false;
    }

}