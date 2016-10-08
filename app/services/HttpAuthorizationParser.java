package services;

import play.mvc.Http;
import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * Created by Sara on 10/8/2016.
 */
public class HttpAuthorizationParser {

    private static final String AUTHORIZATION = "authorization";

    public String[] getAuthorizationFromHeader(Http.Context context) {
        String authHeader = context.request().getHeader(AUTHORIZATION);
        if (authHeader == null) {
            return null;
        }
        String auth = authHeader.substring(6);
        try {
            byte[] decodedAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodedAuth, "UTF-8").split(":");
            if (credString.length != 2) {
                return null;
            }
            return credString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
