/*
 */
package login;

import static login.OpenId.*;

import java.io.IOException;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static login.Constants.FACEBOOK_SIGNIN_LINK_REQUEST_ATTRIBUTE;
import static login.Constants.FACEBOOK_SIGNIN_REDIRECT_URL_SESSION_ATTRIBUTE;
import static login.Constants.FACEBOOK_SIGNIN_SERVLET_PATH;
import static login.Constants.USER_SESSION_ATTRIBUTE;

/**
 *
 * @author caikovsk
 */
@WebServlet(name = "MyHomeServlet", urlPatterns = {""})
public class HomeServlet extends HttpServlet {

    OpenId openId = new OpenId();
    HttpUtils http = new HttpUtils();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(">home");
        if (request.getSession().getAttribute(USER_SESSION_ATTRIBUTE) == null) {
            setFacebookSigninLink(request);
            request.getRequestDispatcher("/WEB-INF/jsp/loggedout.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/loggedin.jsp").forward(request, response);
        }
    }

    void setFacebookSigninLink(HttpServletRequest request) {
        String facebookSigninRedirectUrl = http.getFullUrl(request, FACEBOOK_SIGNIN_SERVLET_PATH);
        System.out.println("facebookSigninRedirectUrl="+facebookSigninRedirectUrl);
        HttpSession session = request.getSession();
        session.setAttribute(FACEBOOK_SIGNIN_REDIRECT_URL_SESSION_ATTRIBUTE, facebookSigninRedirectUrl);
        String newState = openId.getState(request.getRequestURL().toString()) ;
        session.setAttribute(STATE, newState);

        String facebookSigninUrl = openId.getUrl(newState,  facebookSigninRedirectUrl);
        System.out.println("facebookSigninUrl="+facebookSigninUrl);
        request.setAttribute(FACEBOOK_SIGNIN_LINK_REQUEST_ATTRIBUTE, facebookSigninUrl);
    }
}
