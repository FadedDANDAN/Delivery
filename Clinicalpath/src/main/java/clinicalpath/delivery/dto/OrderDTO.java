package clinicalpath.delivery.dto;

import clinicalpath.delivery.entity.OrderEntity.OrderDetail;
import clinicalpath.delivery.eunms.OrderEnum.OrderStatusEnum;
import clinicalpath.delivery.eunms.OrderEnum.PayStatusEnum;
import clinicalpath.delivery.untils.serlalizer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    /*订单ID*/
    private String orderId;

    /*消费者姓名*/
    private String buyerName;

    /*消费者电话*/
    private String buyerPhone;

    /*消费者地址*/
    private String buyerAddress;

    /*消费者openid*/
    private String buyerOpenid;

    /*总金额*/
    private BigDecimal orderAmount;

    /*点单状态 默认0 新订单*/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /*支付状态 默认0 未支付*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /*创建时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /*更新时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
