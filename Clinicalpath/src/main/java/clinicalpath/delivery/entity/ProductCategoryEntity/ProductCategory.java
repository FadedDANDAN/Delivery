package clinicalpath.delivery.entity.ProductCategoryEntity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


@Data
@Entity
@DynamicUpdate
public class ProductCategory {

    /*类目id*/
    @Id
    @GeneratedValue
    private Integer categoryId;

    /*类目名*/
    private String categoryName;

    /*类目编号*/
    private Integer categoryType;

    /*创建时间*/
    private Date createTime;

    /*修改时间*/
    private Date updateTime;

}
