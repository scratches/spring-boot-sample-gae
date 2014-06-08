package demo;/**
 * Author: wge
 * Date: 08/06/2014
 * Time: 09:09
 */


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AllIntegrationTests
{
    private static final Logger log = Logger.getLogger(AllIntegrationTests.class);

    @Test
    public void testVersion() throws IOException
    {
        HttpResponse response = runGet("http://127.0.0.1:8080/version");
        String jsonStatusResponse = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        String foundVersion = objectMapper.readValue(jsonStatusResponse, String.class);
        log.info("found version = " + foundVersion);
        Assert.assertEquals(foundVersion, "1.0");
    }

    private static HttpResponse runGet(String url) throws IOException
    {
        HttpGet get= new HttpGet(url);
        return HttpClientBuilder.create().build().execute(get);
    }
}
