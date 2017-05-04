package co.megaterios.shoppingcarttest.util;

import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by yeison on 04/05/17.
 */

public class Helpers {

    public static String getResponseBody(Response response) {
        String result = "";
        //Try to get response body
        if (response.getBody() instanceof TypedByteArray) {
            TypedByteArray b = (TypedByteArray) response.getBody();
            result = new String(b.getBytes());
        }
        return result;
    }
}
