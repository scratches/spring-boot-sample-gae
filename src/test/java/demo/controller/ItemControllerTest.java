package demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.Application;
import demo.Item;
import demo.pojo.ItemDisplay;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
public class ItemControllerTest {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ItemControllerTest.class);

    @Value("${local.server.port}")
    private int port;

    @Test
    public void testVersion() throws IOException {
        String body = new TestRestTemplate().getForObject("http://127.0.0.1:" + port
                + "/info", String.class);
        log.info("found info = " + body);
        assertTrue("Wrong body: " + body, body.contains("{\"version"));
    }

    @Test
    @Ignore
    public void testSubmitRfq() throws Exception {

        ItemDisplay two = new ItemDisplay();
        two.value = "two";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        RestTemplate template = new RestTemplate();

        ObjectMapper mapper  = new ObjectMapper();
        String jsonItem =  mapper.writeValueAsString(two);

        log.info("item = " + jsonItem);
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonItem,headers);

        ResponseEntity<?> entity = template.postForEntity("http://127.0.0.1:" + port + "/item/add", requestEntity, ItemDisplay.class);


        String response = new TestRestTemplate().getForObject("http://127.0.0.1:" + port +"/item/size", String.class);
        assertEquals("1",response);

        log.info("sleeping for 3100 ms");
        Thread.sleep(3100);

        response = new TestRestTemplate().getForObject("http://127.0.0.1:" + port +"/item/size", String.class);
        assertEquals("0",response);

    }

    @After
    public void end()
    {

    }
}