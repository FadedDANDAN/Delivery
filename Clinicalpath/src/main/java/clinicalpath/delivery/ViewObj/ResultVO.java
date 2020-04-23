package clinicalpath.delivery.ViewObj;

import lombok.Data;

@Data
public class ResultVO<T> {

    /*状态码*/
    private Integer code;

    /*信息*/
    private String message;

    /*数据*/
    private T data;
}
