package isika.p3.amappli.controllers.amap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import isika.p3.amappli.entities.contract.Contract;
import isika.p3.amappli.entities.order.ShoppingCart;
import isika.p3.amappli.entities.product.Product;
import isika.p3.amappli.entities.workshop.Workshop;
import isika.p3.amappli.service.amap.GraphismService;
import isika.p3.amappli.service.amap.ShoppingCartService;

@Controller
@RequestMapping("amap/{tenancyAlias}/cart")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private GraphismService graphismService;

	@GetMapping("/")
	public String viewCart(@PathVariable("tenancyAlias") String alias,
			Model model) {
		// get user info from context (connected user) or userId = (check method) when logout
		Long userId = graphismService.getUserIdFromContext();
		graphismService.setUpModel(alias, model);
		
		ShoppingCart cart = shoppingCartService.getCartByUserId(userId);

		model.addAttribute("userId", userId); 
		model.addAttribute("cart", cart);
		model.addAttribute("total", shoppingCartService.calculateTotal(cart.getShoppingCartId()));

		// totals formulas for CONTRACTS and PRODUCTS
		addTotalsToCartView(cart, model);

		return "amap/front/shopping-cart";
	}
	
	public void addTotalsToCartView(ShoppingCart cart, Model model) {
		double totalContracts = cart.getShoppingCartItems().stream()
				.filter(item -> item.getShoppable() instanceof Contract)
				.mapToDouble(item -> item.getShoppable().getPrice() * item.getQuantity()).sum();

		double totalProducts = cart.getShoppingCartItems().stream()
				.filter(item -> item.getShoppable() instanceof Product)
				.mapToDouble(item -> item.getShoppable().getPrice() * item.getQuantity()).sum();

		double totalWorkshops = cart.getShoppingCartItems().stream()
				.filter(item -> item.getShoppable() instanceof Workshop)
				.mapToDouble(item -> item.getShoppable().getPrice() * item.getQuantity()).sum();

		model.addAttribute("totalContracts", totalContracts);
		model.addAttribute("totalProducts", totalProducts);
		model.addAttribute("totalWorkshops", totalWorkshops);
	}

	@PreAuthorize("hasAuthority(#alias)")
	@PostMapping("/add")
	public String addItem(@PathVariable("tenancyAlias") String alias,
			@RequestParam("shoppableId") Long shoppableId, @RequestParam("shoppableType") String shoppableType,
			@RequestParam("quantity") int quantity, RedirectAttributes redirectAttributes) {
		Long userId = graphismService.getUserIdFromContext();
		ShoppingCart cart = shoppingCartService.getCartByUserId(userId);
		shoppingCartService.addItemToCart(cart.getShoppingCartId(), shoppableId, shoppableType, quantity);
		
		 redirectAttributes.addFlashAttribute("successMessage", "L'article a bien été ajouté au panier !");
		 String redirectUrl;
		    switch (shoppableType) {
		        case "PRODUCT":
		            redirectUrl = "redirect:/amap/{tenancyAlias}/shop/products";
		            break;
		        case "CONTRACT":
		            redirectUrl = "redirect:/amap/{tenancyAlias}/shop/contracts";
		            break;
		        case "WORKSHOP":
		            redirectUrl = "redirect:/amap/{tenancyAlias}/shop/workshops";
		            break;
		        default:
		            redirectUrl = "redirect:/amap/{tenancyAlias}/cart/";
		            break;
		    }
		return redirectUrl;
	}

	@PostMapping("/updateQuantity/{itemId}")
	public String updateItemQuantity(@PathVariable("tenancyAlias") String alias,
			@PathVariable("itemId") Long itemId, @ModelAttribute("action") String action) {
		Long userId = graphismService.getUserIdFromContext();
		ShoppingCart cart = shoppingCartService.getCartByUserId(userId);
		if ("increase".equals(action)) {
			shoppingCartService.increaseItemQuantity(cart.getShoppingCartId(), itemId);
		} else if ("decrease".equals(action)) {
			shoppingCartService.decreaseItemQuantity(cart.getShoppingCartId(), itemId);
		}
		return "redirect:/amap/{tenancyAlias}/cart/";
	}
	


}
