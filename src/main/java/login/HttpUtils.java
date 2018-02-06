/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author mcaikovs
 */
public class HttpUtils {
 

    public String getFullContextPath(HttpServletRequest request) {
        String fullUrl = request.getRequestURL().toString();
        String servletPath = request.getServletPath();
        if (fullUrl.endsWith("/") && servletPath.isEmpty()) {
            servletPath = "/";
        }
        return fullUrl.substring(0, fullUrl.lastIndexOf(servletPath));
    }

    public String getFullUrl(HttpServletRequest request, String servletPath) {
        return getFullContextPath(request) + servletPath;
    }

    public JsonObject getJsonFromUrl(String url) throws IOException {
        try (JsonReader jsonReader = Json.createReader(new InputStreamReader(new URL(url).openStream()))) {
            JsonObject obj = jsonReader.readObject();
            System.out.println("<getJsonFromUrl: " + obj);
            return obj;
        }
    }
}
