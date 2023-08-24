package com.arg.smart.test.crawl.sto.ram;



import com.arg.smart.test.crawl.entity.CrawlResult;
import com.arg.smart.test.crawl.sto.IStorage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author cgli
 */
public class RamStorage implements IStorage {

    private Map<String, CrawlResult> map = new ConcurrentHashMap<>();


    @Override
    public boolean putIfNotExist(String url, CrawlResult result) {
        if(map.containsKey(url)) {
            return false;
        }

        map.put(url, result);
        return true;
    }

    @Override
    public boolean contains(String url) {
        return map.containsKey(url);
    }
}
