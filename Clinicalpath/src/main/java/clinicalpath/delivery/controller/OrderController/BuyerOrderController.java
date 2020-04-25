package clinicalpath.delivery.controller.OrderController;

import clinicalpath.delivery.ViewObj.ResultVO;
import clinicalpath.delivery.converter.OrderForm2OderDtoConverter;
import clinicalpath.delivery.dto.OrderDTO;
import clinicalpath.delivery.eunms.SellEnum.ResultEnum;
import clinicalpath.delivery.exception.SellException;
import clinicalpath.delivery.form.OrderForm;
import clinicalpath.delivery.service.OrderService.BuyerService;
import clinicalpath.delivery.service.OrderService.OrderService;
import clinicalpath.delivery.untils.ResultVOUntil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;


    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确 orderForm={}" ,orderForm);
            throw new SellException(ResultEnum.PARA_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage()
            );
        }


        OrderDTO orderDto = OrderForm2OderDtoConverter.converter(orderForm);
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空");
            throw  new SellException(ResultEnum.CART_EMPTY_ERROR);
        }

        OrderDTO createorderdato = orderService.create(orderDto);
        Map<String,String> map = new HashMap<>();
        map.put("orderid",createorderdato.getOrderId());
        return ResultVOUntil.success(map);
    }

    //订单列表

    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARA_ERROR);
        }

        PageRequest request = PageRequest.of(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);

        return ResultVOUntil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        OrderDTO orderDto = buyerService.findorderOne(openid, orderId);
        return ResultVOUntil.success(orderDto);
    }

    //取消订单
    @PostMapping("/cancle")
    public ResultVO<OrderDTO> cancle(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {

        buyerService.cancelOrder(openid, orderId);
        return ResultVOUntil.success();
    }


}
