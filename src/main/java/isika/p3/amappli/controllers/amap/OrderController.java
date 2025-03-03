package isika.p3.amappli.controllers.amap;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import isika.p3.amappli.entities.order.Order;
import isika.p3.amappli.entities.order.ShoppingCart;
import isika.p3.amappli.service.amap.GraphismService;
import isika.p3.amappli.service.amap.impl.OrderServiceImpl;

@Controller
@RequestMapping("amap/{tenancyAlias}")
public class OrderController {
	
	@Autowired
	private OrderServiceImpl orderService;
	@Autowired
	private GraphismService graphismService;
	
	@GetMapping("/account/my-orders")
	public String viewOrderByUser(@PathVariable("tenancyAlias") String alias, Model model) {
		Long userId = graphismService.getUserIdFromContext();
		
		List<Order> orderByUser = orderService.getListOrdersByUser(userId);
		model.addAttribute("orders", orderByUser);

		graphismService.setUpModel(alias, model);
        return "amap/front/user-profile/my-orders";
	}
	@GetMapping("/account/pickup")
	public String viewPickUpInfos(@PathVariable("tenancyAlias") String alias, Model model) {
		graphismService.setUpModel(alias, model);
		return "amap/front/user-profile/pickup-infos";
	}

	@GetMapping("/admin/orders/list")
	public String viewOrdersForAdmin(@PathVariable("tenancyAlias") String alias, Model model) {
		List<Order> orders = orderService.getOrdersByTenancyAlias(alias);
		model.addAttribute("orders", orders);
		
		graphismService.setUpModel(alias, model);
		return "amap/back/orders/orders-list";
	}
	
	@GetMapping("/account/my-orders/order-details/{orderId}")
	public String viewOrderDetails(@PathVariable("tenancyAlias") String alias, @PathVariable("orderId") Long orderId, Model model) {
		Long userId = graphismService.getUserIdFromContext();
		model.addAttribute("userId", userId);
		
		Order order = orderService.getOrderById(orderId);
		model.addAttribute("order", order);
		
		String formattedDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH));
		model.addAttribute("formattedDate", formattedDate);
		
		graphismService.setUpModel(alias, model);
		return "amap/front/user-profile/order-details";
	}
	
	@GetMapping("/admin/order-details/{orderId}")
	public String viewOrderDetailsForAdmin(@PathVariable("tenancyAlias") String alias, @PathVariable("orderId") Long orderId, Model model) {
		Long userId = graphismService.getUserIdFromContext();
		model.addAttribute("userId", userId);
		
		Order order = orderService.getOrderById(orderId);
		model.addAttribute("order", order);
		
		graphismService.setUpModel(alias, model);
		return "amap/back/orders/order-details";
	}
	
    
    @PostMapping("order/createOrder")
    public String createOrder(
    		@PathVariable("tenancyAlias") String alias, 
            @ModelAttribute("action") String action) {
    	Long userId = graphismService.getUserIdFromContext();
    	ShoppingCart cart = orderService.getCartByUserId(userId);
    	
    	if ("OrderWithPayment".equals(action)) {
    		orderService.createOrderFromCartWithOnlinePayment(cart.getShoppingCartId());
    	} else if ("OrderWithoutPayment".equals(action)) {
    		orderService.createOrderFromCartWithOnsitePayment(cart.getShoppingCartId());
    	}
    	return "redirect:/amap/{tenancyAlias}/account/my-orders";
    }
    
    @PostMapping("order/updateOrder")
    public String updateOrder(@PathVariable("tenancyAlias") String alias, @RequestParam("orderId") Long orderId, 
            @RequestParam("paymentType") String paymentType){
    	orderService.validatePayment(orderId, paymentType);
    	return "redirect:/amap/{tenancyAlias}/admin/order-details/" + orderId;
    }
    
}
