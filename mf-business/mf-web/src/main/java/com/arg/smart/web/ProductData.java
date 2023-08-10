package com.arg.smart.web;

import lombok.Data;

import java.util.Map;

@Data
public class ProductData {

    private static Map<Integer,String> map;

    public static Map<Integer, String> getMap() {
        map.put(1,"肉鸡");
        map.put(2,"柑橘");
        map.put(3,"兰花");
        map.put(4,"对虾");
        map.put(5,"菜心");
        map.put(7,"鸽子");
        return map;
    }

    public static void setMap(Map<Integer, String> map) {
        ProductData.map = map;
    }
}
