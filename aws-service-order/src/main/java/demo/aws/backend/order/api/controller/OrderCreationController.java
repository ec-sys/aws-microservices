package demo.aws.backend.order.api.controller;


import demo.aws.backend.order.api.request.OrderCreateRequest;
import demo.aws.backend.order.api.response.OrderCreateResponse;
import demo.aws.backend.order.service.OrderProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order-creation")
@Slf4j
public class OrderCreationController {

    @Autowired
    OrderProcessService orderProcessService;

    @PostMapping
    public ResponseEntity<OrderCreateResponse> createOrder(@RequestBody OrderCreateRequest request) throws Exception {
        return new ResponseEntity<>(orderProcessService.createOrder(request), HttpStatus.OK);
    }
}
