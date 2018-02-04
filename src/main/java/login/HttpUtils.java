/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

    public InputStream post(String url, String params) throws IOException {
        System.out.println("post: url=" + url);
        System.out.println("post: " + params);
        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {
            out.write(params);
        }

        return con.getInputStream();
    }

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
