package com.arg.smart.web.cms.enums;

public enum ArticleStatus {

    //草稿
    DRAFT(0),
    //审核
    AUDIT(1),
    //发布
    PUBLISH(2);

    private final Integer value;

    public Integer getValue() {
        return value;
    }
    ArticleStatus(Integer value) {
        this.value = value;
    }
}
