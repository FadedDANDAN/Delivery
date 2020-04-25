package clinicalpath.delivery.service.OrderService.OrderServiceImpl;

import clinicalpath.delivery.converter.OrderMaster2OrderDto;
import clinicalpath.delivery.dao.Order.OrderDetailRepository;
import clinicalpath.delivery.dao.Order.OrderMasterRepository;
import clinicalpath.delivery.dto.CartDTO;
import clinicalpath.delivery.dto.OrderDTO;
import clinicalpath.delivery.entity.OrderEntity.OrderDetail;
import clinicalpath.delivery.entity.OrderEntity.OrderMaster;
import clinicalpath.delivery.entity.ProductInfoEntity.ProductInfo;
import clinicalpath.delivery.eunms.OrderEnum.OrderStatusEnum;
import clinicalpath.delivery.eunms.OrderEnum.PayStatusEnum;
import clinicalpath.delivery.eunms.SellEnum.ResultEnum;
import clinicalpath.delivery.exception.SellException;
import clinicalpath.delivery.service.OrderService.OrderService;
import clinicalpath.delivery.service.ProductInfoService.ProductInfoService;
import clinicalpath.delivery.untils.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductInfoService productInfoService;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDto) {
        String orderId = UUIDUtil.getUUID();

        BigDecimal OrderAmount = new BigDecimal(0);

        //查询订单商品价格
        for (OrderDetail orderDetail : orderDto.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //计算订单总价
            OrderAmount = orderDetail.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(OrderAmount);

            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(UUIDUtil.getUUID());
            orderDetailRepository.save(orderDetail);
        }

        //写入订单表
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderAmount(OrderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //减库存
        productInfoService.outcreateStock(orderDto.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())
                ).collect(Collectors.toList()));

        return orderDto;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.getOne(orderId);
        if (orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);

        List<OrderDTO> orderDtoList = OrderMaster2OrderDto.convert(orderMasters.getContent());

        Page<OrderDTO> orderDtoPage = new PageImpl<OrderDTO>(orderDtoList,pageable,orderMasters.getTotalElements());

        return orderDtoPage;
    }

    @Override
    @Transactional
    public OrderDTO cancle(OrderDTO orderDto) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //如果已支付, 需要退款
        if (orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //todo
        }
        //pushMessage.orderStatus(orderDto);
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDto) {
        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //推送微信模版消息
        //pushMessage.orderStatus(orderDto);

        return orderDto;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDto) {
        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if (!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDto;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDtoList = OrderMaster2OrderDto.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDtoPage = new PageImpl<OrderDTO>(orderDtoList,pageable,orderMasterPage.getTotalElements());

        return orderDtoPage;
    }
}
