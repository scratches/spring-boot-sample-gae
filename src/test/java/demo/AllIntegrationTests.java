
package demo;

/**
 * Author: wge
 * Date: 08/06/2014
 * Time: 09:09
 */

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
public class AllIntegrationTests {

	private static final Logger log = Logger.getLogger(AllIntegrationTests.class);

	@Value("${local.server.port}")
	private int port;

	@Test
	public void testVersion() throws IOException {
		String body = new TestRestTemplate().getForObject("http://127.0.0.1:" + port
				+ "/info", String.class);
		log.info("found info = " + body);
		assertTrue("Wrong body: " + body, body.contains("{\"version"));
	}

}
