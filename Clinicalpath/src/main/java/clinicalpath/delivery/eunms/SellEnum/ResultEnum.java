package clinicalpath.delivery.eunms.SellEnum;

import lombok.Getter;

@Getter
public enum  ResultEnum {

    PARA_ERROR(1,"json转换错误"),

    PRODUCT_NOT_EXIST(10,"商品不存在"),
    product_stock_error(11,"商品库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态错误"),
    ORDER_UPDATE_FAIL(15,"订单更新错误"),
    ORDER_DETAIL_EMPTY(16,"订单订单详情为空"),
    ORDER_PAY_STATUS_ERROR(16,"订单支付状态异常"),
    CART_EMPTY_ERROR(17,"购物车为空"),
    ORDER_OPENID_ERROR(18,"openid不正确"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
