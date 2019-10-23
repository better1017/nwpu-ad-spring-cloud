package edu.nwpu.ad.constant;

import lombok.Getter;

/**
 * 物料的枚举类型
 */
@Getter
public enum  CreatvieMaterialType {
    JPG(1,"jpg"),

    BMP(2,"bmp"),

    PNG(3,"png"),

    MP4(4,"mp4"),

    AVI(5,"avi"),

    TXT(6,"txt");

    private int type;
    private String desc;

    CreatvieMaterialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
