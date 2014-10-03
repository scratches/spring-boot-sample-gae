package demo.controller;

import demo.service.SimpleItemQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by will on 01/10/2014.
 */

@RestController
public class CronController {

    private static final Logger log = LoggerFactory.getLogger(CronController.class);

    @Autowired
    SimpleItemQueue simpleItemQueue;


    @RequestMapping(value = "/cron/item",
            method = RequestMethod.GET,
            consumes = "*",
            headers = {"content-type=application/X-AppEngine-Cron"})
    @ResponseBody
    public ResponseEntity purgeQueue(HttpServletRequest request)
    {


        log.info("== called ==");

        log.debug("userAgent =" + request.getHeader("User-Agent"));
        log.debug("host =" + request.getHeader("Host"));
        log.debug("X-AppEngine-Cron =" + request.getHeader("X-AppEngine-Cron"));
        log.debug("X-AppEngine-QueueName =" + request.getHeader("X-AppEngine-QueueName"));
        log.debug("X-AppEngine-TaskName =" + request.getHeader("X-AppEngine-TaskName"));
        log.debug("X-AppEngine-TaskRetryCount =" + request.getHeader("X-AppEngine-TaskRetryCount"));

        boolean isCronTask = "true".equals(request.getHeader("X-AppEngine-Cron"));
        //String queueName = request.getHeader("X-AppEngine-QueueName");

        if(isCronTask)
            simpleItemQueue.manuallySeekTimeouts();
        else
            log.warn("someone tried to invoke cron");

        return new ResponseEntity(HttpStatus.OK);
    }

}
