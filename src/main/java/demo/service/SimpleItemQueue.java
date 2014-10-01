package demo.service;


import demo.Item;
import demo.QueueItemListener;
import demo.queue.IQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is a queue that self purges every 30 seconds.
 */

@Service
public class SimpleItemQueue implements IQueueService
{
    private static final Logger log = LoggerFactory.getLogger(SimpleItemQueue.class);

    static String MAX_QUEUE_LIFETIME_SECONDS = "30";

    private final Long timeout = Long.parseLong(MAX_QUEUE_LIFETIME_SECONDS) * 1000L;
    private final ConcurrentLinkedQueue<Item> fifoQueue = new ConcurrentLinkedQueue<>();
    private QueueItemListener rfqListener = new SelfPurger();


    public SimpleItemQueue(){}

    @Override
    public void add(Item rfq)
    {
        rfq.startClockTicking();
        fifoQueue.add(rfq);
    }

    @Override
    public int size()
    {
        return fifoQueue.size();
    }


    @Override
    public Item find(String id)
    {
        Iterator<Item> iterator = fifoQueue.iterator();
        Item found = null;
        while(iterator.hasNext())
        {
            found = iterator.next();
            if(found.getId().equals(id))
                return found;
        }

        return null;
    }

    @Override
    public void manuallySeekTimeouts()
    {

        log.info(" seeking timeout");
        long now = System.currentTimeMillis();
        long elapsedTimeMs;
        Iterator<Item> iterator = fifoQueue.iterator();
        while(iterator.hasNext())
        {
            Item item = iterator.next();
            elapsedTimeMs = now - item.getCreationTimeMs();
            if(elapsedTimeMs >= timeout)
            {
                log.info("found a timedout id = " + item.getId());

                rfqListener.onTrigger(item);
            }
            else
            {
                log.info("elapsedTimeMs = " + elapsedTimeMs);
            }
        }

    }

    @Override
    public void clear() {
        fifoQueue.clear();
    }


    class SelfPurger implements QueueItemListener
    {

        @Override
        public void onTrigger(Item rfq)
        {
            fifoQueue.remove(rfq);
        }
    }
}
