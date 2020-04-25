package clinicalpath.delivery.service.OrderService;


import clinicalpath.delivery.dto.OrderDTO;

public interface BuyerService {

    //查询一个订单
    OrderDTO findorderOne(String openid, String orderid);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderid);
}
