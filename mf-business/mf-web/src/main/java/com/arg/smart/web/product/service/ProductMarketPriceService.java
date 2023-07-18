package com.arg.smart.web.product.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.product.entity.ProductMarketPrice;
import com.arg.smart.web.product.req.ReqProductMarketPrice;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 产品批发价格表
 * @author cgli
 * @date: 2023-07-08
 * @version: V1.0.0
 */
public interface ProductMarketPriceService extends IService<ProductMarketPrice> {

    /**
     * 通过爬虫将农情站中的数据添加到数据库中
     * @return
     */
    boolean nongQingSave();

    /**
     * 通过爬虫将食品商务网中的数据添加到数据库中
     * @return
     */
    boolean foodSave();

    /**
     * 通过爬虫将农产品商务信息添加到数据库中
     * @return
     */
    boolean mofcomSave();

    /**
     * 通过反序列化将数据添加到数据库中
     * @param file
     * @return
     */
    boolean jsonAdd(MultipartFile file);

    /**
     * 通过爬虫将惠农网信息添加到数据库中
     * @return
     */
    boolean cnhnbSave();

    /**
     * 后台分页查询
     * @param reqPage
     * @return
     */
    PageResult<ProductMarketPrice> list(ReqProductMarketPrice reqPage);
}
