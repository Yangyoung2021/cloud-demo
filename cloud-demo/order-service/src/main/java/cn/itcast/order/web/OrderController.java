package cn.itcast.order.web;

import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.PatternProperties;
import cn.itcast.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/order")
@RefreshScope
public class OrderController {

   @Autowired
   private OrderService orderService;

   @Value("${pattern.dateformat}")
   private String dateformat;

    @Autowired
    private PatternProperties properties;

    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单并返回
        return orderService.queryOrderById(orderId);
    }

    @GetMapping("/now")
    public String now(@RequestHeader(required = false) String name) {
        System.out.println(name);
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));

    }
}
