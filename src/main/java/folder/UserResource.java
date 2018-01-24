/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package folder;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import static folder.Constants.CLIENT_ID;
import static folder.Constants.JSON_FACTORY;
import static folder.Constants.TRANSPORT;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.util.Collections;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author mcaikovs
 */
@Path("/test")
public class UserResource {

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public User getToken(String token) throws GeneralSecurityException, IOException {
        System.out.println(">getToken: " + token);
        return new User(checkAndGetEmail(token));
    }

    String checkAndGetEmail(String token) throws GeneralSecurityException, IOException {
        System.out.println(">checkAndGetEmail: " + token);
        try {
            readGraphAPI(token);
            return "test@test.tt";
        } catch (IOException ex) {
            throw new WebApplicationException("Invalid token", ex, Response.Status.FORBIDDEN);
        }
    }

    /*
    Most Graph API requests require the use of access tokens, which your app can generate by implementing Facebook Login.
The /me node is a special endpoint that translates to the user_id of the person (or the page_id of the Facebook Page) whose access token is currently being used to make the API calls. 

All nodes and edges in the Graph API can be read simply with an HTTP GET request to the relevant endpoint.
You can choose the fields or edges that you want returned with the fields query parameter.

To enable debug mode, use the debug query string parameter.
https://graph.facebook.com/v2.11/me?access_token=EAACEdEose0cBAD4gCeEroI6ftQmEi4Itd6bn1GDoQiyaGomXhMXXqgV27NJpON7uaWpfZBil0wzzEUSTl878oAUohpk9LC1ATu37ZC7WToB66MZC0lD9Q3h27uyhKlsQ2tZAhyuZCzZBxVe7cZAVfVqZCcK0rcrFfn4WrsUDGHbApJQjZCoZB6nvHb6mBntqdPY58ZD&debug=all&fields=email&format=json&method=get&pretty=0&suppress_http_code=1

     */
    String readGraphAPI(String token) throws IOException {
        System.out.println(">readGraphAPI: " + token);
        URL url = new URL(getGraphAPIURL(token));
        System.out.println(">readGraphAPI url: " + url);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine, response = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            response += inputLine;
            System.out.println("<readGraphAPI: " + response);
            return response;
        }
    }

    String getGraphAPIURL(String idTokenString) {
        return "https://graph.facebook.com/v2.11/me?access_token=" + idTokenString + "&debug=all&fields=email&format=json&method=get&pretty=0";

    }
}
