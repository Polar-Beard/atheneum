package actions;

import model.User;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by Sara on 9/30/2016.
 */
public class BasicAuthAction extends Action<BasicAuth> {

    private static final String AUTHORIZATION = "authorization";
    private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String REALM = "Basic realm=\"104.236.163.131:9000/api/\"";

    @Override
    public CompletionStage<Result> call(Http.Context context) {

        Result unauthorized = Results.unauthorized();
        Result internalServerError = Results.internalServerError();

        String authHeader = context.request().getHeader(AUTHORIZATION);
        if(authHeader == null){
            context.response().setHeader(WWW_AUTHENTICATE, REALM);
            return CompletableFuture.completedFuture(unauthorized);
        }

        String auth = authHeader.substring(6);
        try {
            byte[] decodedAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodedAuth, "UTF-8").split(":");
            if (credString.length != 2) {
                return CompletableFuture.completedFuture(unauthorized);
            }

            String emailAddress = credString[0];
            String password = credString[1];
            boolean userIsValid = User.isValid(emailAddress, password);
            return (userIsValid)? delegate.call(context): CompletableFuture.completedFuture(unauthorized);

        } catch (IOException e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(internalServerError);
        }
    }
}
