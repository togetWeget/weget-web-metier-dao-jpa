package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.CartBlock;
import ci.weget.web.entites.CreditCard;
import ci.weget.web.entites.Order;
import ci.weget.web.entites.Personnes;
import ci.weget.web.exception.InvalideTogetException;

public interface IOrderMetier extends Imetier<Order, Long>{
	public Order creer( Personnes personne, CreditCard creditCard, List<CartBlock> cartItems) throws InvalideTogetException;  
}
