package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.CartBlock;
import ci.weget.web.entites.CreditCard;
import ci.weget.web.entites.Order;
import ci.weget.web.entites.Personnes;

public interface IOrderMetier extends Imetier<Order, Long>{
	public boolean addBlockToCart(); 
	public Order creer( Personnes personne, final CreditCard creditCard, final List<CartBlock> cartItems);  
}
