package service.payment.RMI;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("order-service")
public interface OrderClient {

}
