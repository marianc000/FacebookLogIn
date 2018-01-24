/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package folder;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mcaikovs
 */
public class UserResourceTest {

    UserResource i = new UserResource();

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

String response="{\"email\":\"dummy000\\u0040mail.ru\",\"id\":\"10215579219697013\",\"__debug__\":{}}";// {"email":"dummy000\u0040mail.ru","id":"10215579219697013","__debug__":{}}  
    // https://graph.facebook.com/v2.11/me?access_token=EAAFzBKZBWT9QBAHzWcGGSy5GepjlS9S1YEPvN1p2jwaGxc0QZCaVoAZCmsZB8YaE1AkegbmObdBDY64DDD1t1kxezOgpEFKbbLKlyQyPcEiyUZCwSI3iJOhe9ioahZA9Ye6hvOybhzGeOODFdihEnPbuw5sso5CzPEZAQL1RkdM3cfKajOdKsPmMWOvNhrDtE0ZD&debug=all&fields=email&format=json&method=get&pretty=0

    @Test
    public void testReadUserEmailFromGraphAPI() throws IOException {
        i = new UserResource() {
            @Override
            Reader getURLInputStream(String token) throws IOException {
                return new StringReader(response);
            }

        };

        assertEquals("dummy000@mail.ru",i.readUserEmailFromGraphAPI(response));
                
    }

}
