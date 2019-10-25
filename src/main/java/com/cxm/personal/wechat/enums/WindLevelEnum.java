package com.cxm.personal.wechat.enums;

/**
 * @author cxm
 * @description 风力级别
 * @date 2019-10-18 15:16
 **/
public enum WindLevelEnum {

    LEVEL_0(0, "无风", "静烟直上"),
    LEVEL_1(1, "软风", "烟示风向"),
    LEVEL_2(2, "轻风", "感觉有风"),
    LEVEL_3(3, "微风", "旌旗展开"),
    LEVEL_4(4, "和风", "吹起尘土"),
    LEVEL_5(5, "清风", "小树摇摆"),
    LEVEL_6(6, "强风", "电线有声"),
    LEVEL_7(7, "劲风", "步行困难"),
    LEVEL_8(8, "大风", "折毁树枝"),
    LEVEL_9(9, "烈风", "小损房屋"),
    LEVEL_10(10, "狂风", "拔起树木"),
    LEVEL_11(11, "暴风", "损毁重大"),
    LEVEL_12(12, "台风", "摧毁极大");

    private int level;
    private String levelName;
    private String description;


    public static String getDescriptionByLevel(int level) {
        for (WindLevelEnum platformFree : WindLevelEnum.values()) {
            if (level == platformFree.getLevel()) {
                return platformFree.getDescription();
            }
        }
        return null;
    }


    WindLevelEnum(int level, String levelName, String description) {
        this.level = level;
        this.levelName = levelName;
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
