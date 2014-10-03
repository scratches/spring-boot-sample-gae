package demo.controller;

import demo.Item;
import demo.pojo.ItemDisplay;
import demo.service.SimpleItemQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by will on 01/10/2014.
 */

@RestController
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    SimpleItemQueue simpleItemQueue;

    @RequestMapping("/")
    public String home() {
        return "Hello World";
    }

    @RequestMapping("/version")
    public String getVersion() {
        return "1.0";
    }


    @RequestMapping(value = "/item/add", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json",
            headers = {"content-type=application/json"})
    @ResponseBody
    public void submitRfq(@RequestBody ItemDisplay itemDisplay) {
        log.info("adding " + itemDisplay.value + " to queue");

        Item item = new Item(itemDisplay.value);
        simpleItemQueue.add(item);
    }

    @RequestMapping(value = "/item/size",
            method = RequestMethod.GET,
            consumes = "*",
            produces = "application/json",
            headers = {"content-type=application/json"})
    @ResponseBody
    public String getAll() {

        return  String.valueOf(simpleItemQueue.size());

    }


}
