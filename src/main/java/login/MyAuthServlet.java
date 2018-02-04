/*
 */
package login;

import static login.OpenId.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static login.Constants.FACEBOOK_SIGNIN_REDIRECT_URL_SESSION_ATTRIBUTE;
import static login.Constants.FACEBOOK_SIGNIN_SERVLET_PATH;
import static login.Constants.USER_SESSION_ATTRIBUTE;

/**
 *
 * @author caikovsk
 */
@WebServlet(name = "MyGoogleAuthServlet", urlPatterns = {FACEBOOK_SIGNIN_SERVLET_PATH})
public class MyAuthServlet extends HttpServlet {

    HttpUtils http = new HttpUtils();
    OpenId openId = new OpenId();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter(CODE);
        HttpSession session = request.getSession();
        String receivedState = request.getParameter(STATE); // state should also include last served url, so that the user can return to the page where he logs in
        System.out.println("receivedState=" + receivedState);
        String receivedStateUrl = openId.extractUrlFromState(receivedState);
        String savedState = (String) session.getAttribute(STATE);
        String signinRedirectUrl = (String) session.getAttribute(FACEBOOK_SIGNIN_REDIRECT_URL_SESSION_ATTRIBUTE);
        System.out.println(code);
        if (code != null) {
            //On the server, you must confirm that the state received from Google matches the session token you created in Step 1. This round-trip verification helps to ensure that the user, not a malicious script, is making the request.
            if (savedState.equals(receivedState)) {
                String accessToken = openId.exchangeCodeForToken(code, signinRedirectUrl);
                if (accessToken != null) {
                    System.out.println(accessToken);
                    String email = openId.getUserEmailFromGraphAPI(accessToken);
                    System.out.println("email=" + email);
                    session.setAttribute(USER_SESSION_ATTRIBUTE, email);
                } else {
                    System.out.println("Id token is missing");
                }
            } else {
                System.out.println("States are different");
            }
        } else {
            System.out.println("<p>Code is null</p>");
        }
        response.sendRedirect(receivedStateUrl);
    }
}
