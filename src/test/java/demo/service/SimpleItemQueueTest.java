package demo.service;


import demo.Application;
import demo.Item;
import demo.mock.CronSimulator;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.PostConstruct;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SimpleItemQueueTest
{
    private static final Logger log = LoggerFactory.getLogger(SimpleItemQueueTest.class);

    @BeforeClass
    public static void init()
    {
        SimpleItemQueue.MAX_QUEUE_LIFETIME_SECONDS = "3";
    }

    @Autowired
    private SimpleItemQueue simpleItemQueue;

    private CronSimulator cronSimulator;

    @PostConstruct
    public void start()
    {
        cronSimulator = new CronSimulator(simpleItemQueue);
    }

    @After
    public void end()
    {
        simpleItemQueue.clear();
        cronSimulator.stop();
    }

    @Test
    public void testCleanExpiredTokens() throws Exception
    {
        cronSimulator.start();

        Item one = new Item("one");

        simpleItemQueue.add(one);

        Assert.assertEquals(1, simpleItemQueue.size());

        log.info("sleeping");
        Thread.sleep(6000);
        log.info("waking");

        Assert.assertEquals(0, simpleItemQueue.size());


    }
}