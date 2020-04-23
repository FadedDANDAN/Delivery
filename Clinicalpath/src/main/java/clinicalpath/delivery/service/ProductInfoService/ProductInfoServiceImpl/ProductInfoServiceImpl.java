package clinicalpath.delivery.service.ProductInfoService.ProductInfoServiceImpl;

import clinicalpath.delivery.dao.ProductCategory.ProductCategoryRepository;
import clinicalpath.delivery.dao.ProductInfo.ProductInfoRepository;
import clinicalpath.delivery.entity.ProductInfoEntity.ProductInfo;
import clinicalpath.delivery.service.ProductInfoService.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findByProductStatus(Integer productStatus) {
        return productInfoRepository.findByProductStatus(productStatus);
    }

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.getOne(productId);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public void save(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
    }
}
