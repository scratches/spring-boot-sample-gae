package demo.queue;

import demo.Item;
import demo.QueueItemListener;


public interface IQueueService
{
    void add(Item item);

    int size();

    Item find(String id);

    void manuallySeekTimeouts();

    void clear();
}
