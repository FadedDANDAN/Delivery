package clinicalpath.delivery.converter;

import clinicalpath.delivery.dto.OrderDTO;
import clinicalpath.delivery.entity.OrderEntity.OrderDetail;
import clinicalpath.delivery.eunms.SellEnum.ResultEnum;
import clinicalpath.delivery.exception.SellException;
import clinicalpath.delivery.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OderDtoConverter {

    public static OrderDTO converter(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDto = new OrderDTO();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try{
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【对象转换】 错误，string={}" ,orderForm.getItems());
            throw new SellException(ResultEnum.PARA_ERROR);
        }

        orderDto.setOrderDetailList(orderDetailList);

        return orderDto;
    }

}
