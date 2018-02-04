package login;

import login.hash.Sha256Digest;
import static login.Constants.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Pattern;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class OpenId {

    public static String CODE = "code",
            STATE = "state",
            EMAIL = "email",
            TOKEN_ENDPOINT = "https://www.facebookapis.com/oauth2/v4/token",
            //    REDIRECT_URI = "http://localhost:8080/test/server",
            ACCESS_TOKEN = "access_token";

    Random r = new Random();

    public String getUrl(String state, String redirectUrl) {
        return "https://www.facebook.com/v2.12/dialog/oauth?client_id=" + APP_ID + "&redirect_uri=" + redirectUrl + "&scope=email&response_type=code&" + STATE + "=" + state;
    }

    /*
    GET https://graph.facebook.com/v2.12/oauth/access_token?
   client_id={app-id}
   &redirect_uri={redirect-uri}
   &client_secret={app-secret}
   &code={code-parameter}
     */
    String getAccessTokenEndpointUrl(String code, String signinRedirectUrl) {
        return "https://graph.facebook.com/v2.12/oauth/access_token?code=" + code + "&client_id=" + APP_ID + "&client_secret=" + APP_SECRET + "&redirect_uri=" + signinRedirectUrl;
    }
    static String SEPARATOR = "|", QUOTED_SEPARATOR = Pattern.quote(SEPARATOR);

    public String getState(String url) {
        return url + SEPARATOR + getStateNonce();
    }

    public String extractUrlFromState(String state) {
        return state.split(QUOTED_SEPARATOR)[0];
    }

    String getStateNonce() {
        return String.valueOf(r.nextInt(Integer.MAX_VALUE));
    }

    String exchangeCodeForToken(String code, String signinRedirectUrl) throws IOException {
        String endpointUrl = getAccessTokenEndpointUrl(code, signinRedirectUrl);
        System.out.println("endpointUrl=" + endpointUrl);
        JsonObject response = http.getJsonFromUrl(endpointUrl);
        System.out.println("response=" + response);
        return response.getString( ACCESS_TOKEN);
    }

    String getUserEmailFromGraphAPI(String accessToken) throws IOException {
    //   try (JsonReader jsonReader = Json.createReader(getURLInputStream(token))) {
            JsonObject obj = http.getJsonFromUrl(getGraphAPIUrl(accessToken));
            System.out.println("<readGraphAPI: " + obj.toString());
            return obj.getString("email");
    }
 

   String getGraphAPIUrl(String accessToken) throws MalformedURLException {
        String url = "https://graph.facebook.com/v2.12/me?access_token=" + accessToken + "&debug=all&fields=email&format=json&method=get&pretty=0" + getAppSecretProof(accessToken) ;
        System.out.println("url=" + url);
        return url;
    }

    /*
    In the Advanced section of your app's settings, you can require use of appsecret_proof. When this is enabled, we will only allow API calls that either include appsecret_proof or are made from the same device the token was issued to.
    */
    String getAppSecretProof(String token) {
        String proof = null;
        try {
            proof = Sha256Digest.hashToken(token);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (proof != null) {
            return "&appsecret_proof=" + proof;
        } else {
            return "";
        }
    }
    /*
   Access tokens are portable. Graph API calls can be made from clients or from your server on behalf of clients. Calls from a server can be better secured by adding a parameter called appsecret_proof.
     */
    
    HttpUtils http = new HttpUtils();
}
