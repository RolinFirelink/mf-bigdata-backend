package com.arg.smart.web.order.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author GlowingTree
 * @Date 2023-6/9/2023-9:52 AM
 * @PackageName com.arg.smart.web.order.model
 * @ClassName Flag
 * @Description 模块编号
 * @Version 1.0
 */
public class ModuleFlag {
    /**
     * 肉鸡
     */
    public static final int CHICKEN = 1;
    /**
     * 柑橘
     */
    public static final int ORANGE = 2;
    /**
     * 兰花
     */
    public static final int ORCHID = 3;
    /**
     * 对虾
     */
    public static final int SHRIMP = 4;
    /**
     * 菜心
     */
    public static final int FLOWER_CABBAGE = 5;
    /**
     * 预制菜
     */
    public static final int PREFABRICATED_DISHES = 6;
    /**
     * 鸽子
     */
    public static final int PIGEON = 7;
    /**
     * 金鲳鱼
     */
    public static final int FISH = 8;

    public static final Map<Integer, String> FLAG_NAME = new HashMap<>();

    public static void initFlagNameMap() {
        FLAG_NAME.put(ModuleFlag.CHICKEN, "肉鸡");
        FLAG_NAME.put(ModuleFlag.ORANGE, "柑橘");
        FLAG_NAME.put(ModuleFlag.ORCHID, "兰花");
        FLAG_NAME.put(ModuleFlag.SHRIMP, "对虾");
        FLAG_NAME.put(ModuleFlag.FLOWER_CABBAGE, "菜心");
        FLAG_NAME.put(ModuleFlag.PREFABRICATED_DISHES, "预制菜");
    }

}
