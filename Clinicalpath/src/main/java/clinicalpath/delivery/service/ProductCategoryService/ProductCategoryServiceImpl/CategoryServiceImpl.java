package clinicalpath.delivery.service.ProductCategoryService.ProductCategoryServiceImpl;

import clinicalpath.delivery.dao.ProductCategory.ProductCategoryRepository;
import clinicalpath.delivery.entity.ProductCategoryEntity.ProductCategory;
import clinicalpath.delivery.service.ProductCategoryService.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory findOne(Integer categoryid) {
        return productCategoryRepository.getOne(categoryid);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public void save(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }


    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }
}
