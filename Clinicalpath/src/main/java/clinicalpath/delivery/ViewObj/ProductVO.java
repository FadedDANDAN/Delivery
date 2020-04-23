package clinicalpath.delivery.ViewObj;

import clinicalpath.delivery.entity.ProductInfoEntity.VO.ProductInfoVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class ProductVO {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productinfos;
}
