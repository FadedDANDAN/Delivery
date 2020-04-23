package clinicalpath.delivery.entity.ProductInfoEntity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
public class ProductInfo {

    /*商品ID*/
    @Id
    private String productId;

    /*商品名称*/
    private String productName;

    /*商品价格*/
    private BigDecimal productPrice;

    /*商品库存*/
    private Integer productStock;

    /*商品描述*/
    private String productDescription;

    /*商品图片*/
    private String productIcon;

    /*商品状态 0表示下架 1表示上架*/
    private Integer productStatus;

    /*类目编号*/
    private Integer categoryType;

    /*创建时间*/
    private Date createTime;

    /*更新时间*/
    private Date updateTime;

}
