package net.dean.jraw.test;

import com.squareup.okhttp.MediaType;
import net.dean.jraw.ApiException;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.RestRequest;
import net.dean.jraw.models.Captcha;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This class tests anything and everything related to captchas.
 */
public class CaptchaTest extends RedditTest {

    @Test
    public void testNeedsCaptcha() {
        try {
            // Make sure it doesn't error, could return true or false
            reddit.needsCaptcha();
        } catch (NetworkException e) {
            handle(e);
        }
    }

    @Test
    public void testCaptchaComponents() {
        try {
            Captcha c = reddit.getNewCaptcha();
            Assert.assertNotNull(c.getId());
            Assert.assertNotNull(c.getImageUrl());

            // Test out the image URL
            RestRequest imageRequest = RestRequest.Builder.from("GET", c.getImageUrl())
                    .expected(MediaType.parse("image/png"))
                    .build();
            reddit.execute(imageRequest);
        } catch (NetworkException | ApiException e) {
            handle(e);
        }
    }
}
