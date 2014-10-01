package demo.servlets;

import demo.service.SimpleItemQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SelfPurgingQueueServlet extends HttpServlet
{
    private static final Logger log = LoggerFactory.getLogger(SelfPurgingQueueServlet.class);

    private static final long serialVersionUID = -1137258632691070463L;

    @Autowired
    SimpleItemQueue simpleItemQueue;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException

    {
        log.info("== called ==");

        log.info("userAgent =" + request.getHeader("User-Agent"));
        log.info("host =" + request.getHeader("Host"));
        log.info("X-AppEngine-Cron =" + request.getHeader("X-AppEngine-Cron"));
        log.info("X-AppEngine-QueueName =" + request.getHeader("X-AppEngine-QueueName"));
        log.info("X-AppEngine-TaskName =" + request.getHeader("X-AppEngine-TaskName"));
        log.info("X-AppEngine-TaskRetryCount =" + request.getHeader("X-AppEngine-TaskRetryCount"));

      boolean isCronTask = "true".equals(request.getHeader("X-AppEngine-Cron"));
      //String queueName = request.getHeader("X-AppEngine-QueueName");

      if(isCronTask)
          simpleItemQueue.manuallySeekTimeouts();
      else
          log.warn("someone tried to invoke cron");
    }
  }