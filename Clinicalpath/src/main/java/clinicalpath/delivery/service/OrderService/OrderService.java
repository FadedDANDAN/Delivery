package clinicalpath.delivery.service.OrderService;

import clinicalpath.delivery.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    /*  c创建订单 */
    OrderDTO create(OrderDTO orderDto);

    /*  查询单个订单 */
    OrderDTO findOne(String orderId);

    /* 查询订单列表*/
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /* 取消订单*/
    OrderDTO cancle(OrderDTO orderDto);


    /*完结订单*/
    OrderDTO finish(OrderDTO orderDto);


    /*支付订单*/
    OrderDTO pay(OrderDTO orderDto);

    /*查询订单列表*/
    Page<OrderDTO> findList(Pageable pageable);

}
