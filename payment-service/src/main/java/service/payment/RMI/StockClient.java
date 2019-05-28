package service.payment.RMI;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("stock-service")
public interface StockClient {

}
