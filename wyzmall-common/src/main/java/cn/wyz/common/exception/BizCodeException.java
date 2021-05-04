package cn.wyz.common.exception;

public enum BizCodeException {
    UNKNOW_EXCEPTION(10000, "系统未知异常"),
    VAILD_EXCEPTION(10001, "参数格式校验失败"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架异常");

    private Integer code;
    private String msg;

    BizCodeException(Integer code, String msg) {
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
