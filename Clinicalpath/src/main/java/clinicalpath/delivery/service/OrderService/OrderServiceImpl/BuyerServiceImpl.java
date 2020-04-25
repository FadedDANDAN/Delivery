package clinicalpath.delivery.service.OrderService.OrderServiceImpl;

import clinicalpath.delivery.dto.OrderDTO;
import clinicalpath.delivery.eunms.SellEnum.ResultEnum;
import clinicalpath.delivery.exception.SellException;
import clinicalpath.delivery.service.OrderService.BuyerService;
import clinicalpath.delivery.service.OrderService.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findorderOne(String openid, String orderid) {
        OrderDTO orderDto = checkownOrder(openid,orderid);
        return orderDto;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderid) {
        OrderDTO orderDto = checkownOrder(openid,orderid);
        if (orderDto == null){
            log.error("【取消订单】 查不到该订单 orderid={}",orderid);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancle(orderDto);
    }

    private OrderDTO checkownOrder(String openid, String orderid) {
        OrderDTO orderDto = orderService.findOne(orderid);

        //判断是否是自己的订单
        if (!orderDto.getBuyerOpenid().equals(openid)) {
            log.error("【查询订单】 订单openid不符 openid = {}", openid);
            throw new SellException(ResultEnum.ORDER_OPENID_ERROR);
        }
        return orderDto;
    }
}
