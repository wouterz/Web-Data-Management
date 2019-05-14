package payment;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class PaymentController {

	
    @RequestMapping("/payment/status/")
    public Payment getStatus(@RequestParam(value="order_id") long order_id) {
		return null;
    }
    
    @RequestMapping("/payment/pay/")
    public Payment pay(@RequestParam(value="user_id") long user_id, @RequestParam(value="order_id") long order_id) {
		return null;
    }
    
    @RequestMapping("/payment/cancelPayment/{user_id}/{order_id}")
    public Payment cancelPayment(@RequestParam(value="user_id") long user_id, @RequestParam(value="order_id") long order_id) {
		return null;
    }
    
}
