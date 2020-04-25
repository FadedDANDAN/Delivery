package clinicalpath.delivery.service.ProductInfoService;

import clinicalpath.delivery.dto.CartDTO;
import clinicalpath.delivery.entity.ProductInfoEntity.ProductInfo;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductInfoService {

    List<ProductInfo> findByProductStatus(Integer productStatus);

    ProductInfo findOne(String productId);

    Page<ProductInfo> findAll(Pageable pageable);

    void save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void outcreateStock(List<CartDTO> cartDTOList);
}
