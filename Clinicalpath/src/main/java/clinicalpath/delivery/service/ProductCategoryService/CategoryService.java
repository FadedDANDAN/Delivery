package clinicalpath.delivery.service.ProductCategoryService;

import clinicalpath.delivery.entity.ProductCategoryEntity.ProductCategory;

import java.util.List;

public interface CategoryService {
    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    void save(ProductCategory productCategory);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
