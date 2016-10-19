package actions;

import daos.UserDAO;
import model.User;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import services.HttpAuthorizationParser;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by Sara on 9/30/2016.
 */
public class BasicAuthAction extends Action<BasicAuth> {

    private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String REALM = "Basic realm=\"104.236.163.131:9000/api/\"";

    @Override
    public CompletionStage<Result> call(Http.Context context) {

        HttpAuthorizationParser httpAuthorizationParser = new HttpAuthorizationParser();
        String[] credString = httpAuthorizationParser.getAuthorizationFromHeader(context);

        if(credString == null){
            context.response().setHeader(WWW_AUTHENTICATE, REALM);
            return CompletableFuture.completedFuture(Results.unauthorized());
        }
        String emailAddress = credString[0];
        String password = credString[1];
        User user = (new UserDAO()).findUserByEmail(emailAddress);
        if(user == null){
            return CompletableFuture.completedFuture(Results.badRequest("User does not exist"));
        }
        boolean validPassword = user.isPasswordValid(password);
        return (validPassword)? delegate.call(context): CompletableFuture.completedFuture(Results.unauthorized());
    }
}
