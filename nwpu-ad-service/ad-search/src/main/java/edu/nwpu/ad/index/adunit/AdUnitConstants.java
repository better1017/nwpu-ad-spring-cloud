package edu.nwpu.ad.index.adunit;

/**
 * 流量类型
 * 需要同步给媒体方，否则媒体方不知道选用哪些流量类型向我们发起请求
 */
public class AdUnitConstants {

    /**
     * 内部类，定义流量类型
     */
    public static class POSITION_TYPE {

        /**
         * 值使用二进制编排
         * 可以用来使用位或、位与运算，加快运算速度
         * **/
        public static final int KAIPING = 1; //开屏
        public static final int TIEPIAN = 2; //贴片
        public static final int TIEPIAN_MIDDLE = 4; //中片
        public static final int TIEPIAN_PAUSE = 8; //暂停贴
        public static final int TIEPIAN_POST = 16; //后贴
    }
}
