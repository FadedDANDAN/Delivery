package clinicalpath.delivery.service.ProductInfoService.ProductInfoServiceImpl;

import clinicalpath.delivery.dao.ProductCategory.ProductCategoryRepository;
import clinicalpath.delivery.dao.ProductInfo.ProductInfoRepository;
import clinicalpath.delivery.dto.CartDTO;
import clinicalpath.delivery.entity.ProductInfoEntity.ProductInfo;
import clinicalpath.delivery.eunms.SellEnum.ResultEnum;
import clinicalpath.delivery.exception.SellException;
import clinicalpath.delivery.service.ProductInfoService.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = productInfoRepository.getOne(cartDTO.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void outcreateStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = productInfoRepository.getOne(cartDTO.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0 ){
                throw new SellException(ResultEnum.product_stock_error);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }
}
