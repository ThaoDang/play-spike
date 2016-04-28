package filters;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import javax.inject.Inject;
import akka.stream.Materializer;
import akka.stream.impl.fusing.Log;
import akka.util.ByteString;
import play.Logger;
import play.libs.streams.Accumulator;
import play.mvc.*;
import scala.collection.JavaConversions;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

public class AuthorizationFilter extends Filter {

    @Inject
    public AuthorizationFilter(Materializer mat) {
        super(mat);
    }

    @Override
    public CompletionStage<Result> apply(
            Function<Http.RequestHeader, CompletionStage<Result>> nextFilter,
            Http.RequestHeader requestHeader) {

            if(requestHeader.path().contains("login")){
                Logger.info("go to login");
                Logger.info(requestHeader.path());

                return nextFilter.apply(requestHeader);
            }

            String username = session(requestHeader).get("username");

            if(username == null){
                return CompletableFuture.completedFuture(redirect("/login"));
            }

            return nextFilter.apply(requestHeader);
    }

    private Http.Session session(Http.RequestHeader requestHeader) {
        return new Http.Session(JavaConversions.mapAsJavaMap(requestHeader._underlyingHeader().session().data()));
    }

}
