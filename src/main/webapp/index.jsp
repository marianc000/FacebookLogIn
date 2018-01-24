<%-- 
    Document   : index
    Created on : 22 janv. 2018, 14:25:10
    Author     : mcaikovs
--%>

<%@page import="static folder.Constants.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>TEST</title>
        <link rel="stylesheet" href="css/buttonOnly.css">
        <script src="lib/jquery.js"></script>
        <script   src="facebook.js"></script>
    </head> 
    <body>  
        <div id="fb-root"></div>
        <!--Google standard button-->
 <input id="appId" type="hidden"   value="<%=APP_ID%>">
        <!-- In the callback, you would hide the gSignInWrapper element on a successful sign in -->
        <div id="facebookLoginButton" class="_xvm  facebookLoginButton"  >
            <div class="_5h0c _5h0d" style="" role="button" tabindex="0">
                <table   cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr  >
                            <td class="_51m-">
                                <div class="_5h0j">
                                    <span class="_5h0k">
                                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 216 216" class="_5h0m" color="#ffffff">
                                        <path fill="#ffffff" d="
                                              M204.1 0H11.9C5.3 0 0 5.3 0 11.9v192.2c0 6.6 5.3 11.9 11.9
                                              11.9h103.5v-83.6H87.2V99.8h28.1v-24c0-27.9 17-43.1 41.9-43.1
                                              11.9 0 22.2.9 25.2 1.3v29.2h-17.3c-13.5 0-16.2 6.4-16.2
                                              15.9v20.8h32.3l-4.2 32.6h-28V216h55c6.6 0 11.9-5.3
                                              11.9-11.9V11.9C216 5.3 210.7 0 204.1 0z">
                                        </path>
                                        </svg>
                                    </span>
                                </div>
                            </td>
                            <td class="_51m- _51mw">
                                <div class="_5h0s">
                                    <div class="_5h0o">Log in With Facebook</div>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="authenticated" style="display:none">
            <p>Your are signed in as: <span class="userEmail"></span></p> 
            <a id='facebookLogOutButton' href="#" style="display:none" >Logout</a>
            <a id='facebookLogOutRedirectButton' href="#"   >Logout</a>
        </div>


    </body>
</html>
