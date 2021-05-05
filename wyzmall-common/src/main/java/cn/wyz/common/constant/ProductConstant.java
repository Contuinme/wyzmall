package cn.wyz.common.constant;

public class ProductConstant {
    public enum AttrTypeEnum {
        ATTR_TYPE_BASE(1, "基本属性"), ATTR_TYPE_SALE(0, "销售属性");

        private final Integer code;
        private final String msg;

        AttrTypeEnum(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    public enum StatusEnum {
        NEW_SPU(0, "新建状态"), SPU_UP(1, "商品上架"), SPU_DOWN(2, "商品下架");

        private final Integer code;
        private final String msg;

        StatusEnum(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
