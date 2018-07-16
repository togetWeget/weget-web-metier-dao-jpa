package ci.weget.web.metier;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.entites.CartBlock;
import ci.weget.web.entites.CreditCard;
import ci.weget.web.entites.Order;
import ci.weget.web.entites.OrderLine;
import ci.weget.web.entites.Personnes;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class OrderMetierImpl implements IOrderMetier {

	private List<CartBlock> cartBlocks;
	@Autowired
	private IOrderMetier orderMetier;
	@Autowired
	private IBlocksMetier blocksMetier;

	@Override
	public Order creer(Personnes personnes, CreditCard creditCard, List<CartBlock> cartBlocks)
			throws InvalideTogetException {

		// on sássure que cartBlock est valid
		if (cartBlocks == null || cartBlocks.size() == 0)
			throw new ValidationException("Shopping cart is empty");

		// on creer alors une commande
		Order order = new Order(personnes, creditCard);

		// on creer alors les lignes de commande
		List<OrderLine> orderLines = new ArrayList<OrderLine>();

		for (CartBlock cartBlock : cartBlocks) {
			orderLines.add(new OrderLine(cartBlock.getQuantity(), cartBlock.getTarif()));
		}
	//	order.setOrderLines(orderLines);

		return orderMetier.creer(order);

	}

	
	@Override
	public Order modifier(Order entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Order> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Order creer(Order entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return null;
	}

}
