package isika.p3.amappli.service.amap;

import isika.p3.amappli.entities.order.ShoppingCart;

public interface ShoppingCartService {

	ShoppingCart getOrCreateCart(Long cartId);
	ShoppingCart getShoppingCartById(Long id);
	ShoppingCart getCartByUserId(Long userId);
	ShoppingCart addItemToCart(Long cartId, Long shoppableId, String shoppableType, int quantity);
	void increaseItemQuantity(Long cartId, Long itemId);
	void decreaseItemQuantity(Long cartId, Long itemId);
	double calculateTotal(Long cartId);
	void initShoppingCart();
}
