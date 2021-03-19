package cn.wyz.common.constant;

public class WareConstant {
    public enum PurchaseStatusEnum {
        CREATE(0, "新建"),ASSIGNED(1, "已分配"),
        RECEIVE(2, "已领取"),FINISH(3, "已完成"),
        ERROR(4, "有异常");

        private final Integer code;
        private final String msg;

        PurchaseStatusEnum(Integer code, String msg) {
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

    public enum PurchaseDetailEnum {
        CREATE(0, "新建"),ASSIGNED(1, "已分配"),
        BUYING(2, "正在采购"),FINISH(3, "已完成"),
        ERROR(4, "采购失败");

        private final Integer code;
        private final String msg;

        PurchaseDetailEnum(Integer code, String msg) {
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
