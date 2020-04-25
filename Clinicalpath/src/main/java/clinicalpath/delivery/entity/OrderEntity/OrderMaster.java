package clinicalpath.delivery.entity.OrderEntity;

import clinicalpath.delivery.eunms.OrderEnum.OrderStatusEnum;
import clinicalpath.delivery.eunms.OrderEnum.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
public class OrderMaster {

    /*订单ID*/
    @Id
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
    private Date createTime;

    /*更新时间*/
    private Date updateTime;
}
