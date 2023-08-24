package com.arg.smart.test.crawl;


import com.arg.smart.test.crawl.entity.CrawlHttpConf;
import com.arg.smart.test.crawl.entity.CrawlMeta;
import com.arg.smart.test.crawl.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * Created by yihui on 2017/7/6.
 */
public class HttpUtilTest {

    @Test
    public void testHttpGet() {
        String url = "http://chengyu.t086.com/gushi/1.htm";
        CrawlHttpConf conf = new CrawlHttpConf();

        CrawlMeta meta = new CrawlMeta();
        meta.setUrl(url);

        try {
            HttpResponse response = HttpUtils.request(meta, conf);
            String content = EntityUtils.toString(response.getEntity(), "gbk");
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
