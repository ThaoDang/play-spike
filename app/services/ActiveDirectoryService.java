package services;

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

}