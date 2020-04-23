package clinicalpath.delivery.controller.ProductController;

import clinicalpath.delivery.ViewObj.ProductVO;
import clinicalpath.delivery.ViewObj.ResultVO;
import clinicalpath.delivery.entity.ProductCategoryEntity.ProductCategory;
import clinicalpath.delivery.entity.ProductInfoEntity.ProductInfo;
import clinicalpath.delivery.entity.ProductInfoEntity.VO.ProductInfoVO;
import clinicalpath.delivery.eunms.ProductInfoEnum.ProductInfoEnum;
import clinicalpath.delivery.service.ProductCategoryService.CategoryService;
import clinicalpath.delivery.service.ProductInfoService.ProductInfoService;
import clinicalpath.delivery.untils.ResultVOUntil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/buyer/product")
@RestController
public class BuyerProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductInfoService productInfoService;

    @GetMapping("/list")
    public ResultVO buyProductInfo(){

        //1.先查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findByProductStatus(ProductInfoEnum.UP.getCode());

        //2.根据上架商品来查找类目
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据封装成外层想要的
        List<ProductVO> resultVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList){
                ProductInfoVO productInfoVO = new ProductInfoVO();
                BeanUtils.copyProperties(productInfo,productInfoVO);
                productInfoVOList.add(productInfoVO);
            }
            productVO.setProductinfos(productInfoVOList);
            resultVOList.add(productVO);
        }

        return ResultVOUntil.success(resultVOList);
    }
}
