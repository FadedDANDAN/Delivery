package clinicalpath.delivery.controller.test;

import clinicalpath.delivery.entity.ProductCategoryEntity.ProductCategory;
import clinicalpath.delivery.entity.ProductInfoEntity.ProductInfo;
import clinicalpath.delivery.service.ProductCategoryService.CategoryService;
import clinicalpath.delivery.service.ProductInfoService.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductInfoService productInfoService;

    @PostMapping("/findByCategoryTypeIn")
    public List<ProductCategory> findByCategoryTypeIn(){
        List<Integer> categoryTypeList = Arrays.asList(3,4,6);
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(categoryTypeList);
        return productCategories;
    }

    @PostMapping("findproductinfo")
    public Page<ProductInfo> findAll(){
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> productInfos =  productInfoService.findAll(request);
        return productInfos;
    }
}
