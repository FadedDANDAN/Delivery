package clinicalpath.delivery.eunms.ProductInfoEnum;

import lombok.Getter;

@Getter
public enum  ProductInfoEnum {
    UP(0,"在架"),
    DOWN(1,"下架")
    ;

    private Integer code;
    private String message;

    ProductInfoEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
