package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Item {
    private static final Logger log = LoggerFactory.getLogger(Item.class);
    private final String id = java.util.UUID.randomUUID().toString();
    private final String value;
    private long creationTimeMs = 0;

    public Item(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void startClockTicking() {
        if (creationTimeMs == 0) //protect against being called twice.
        {
            creationTimeMs = System.currentTimeMillis();
            log.info("started clock ticking");
        }
    }

    public long getCreationTimeMs() {
        return creationTimeMs;
    }


    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", creationTimeMs=" + creationTimeMs +
                '}';
    }
}

