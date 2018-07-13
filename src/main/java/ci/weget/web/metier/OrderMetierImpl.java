package ci.weget.web.metier;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.CartBlock;
import ci.weget.web.entites.CreditCard;
import ci.weget.web.entites.Order;
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
	public boolean addBlockToCart() {
		 Blocks block = blocksMetier.findById(idBlock);
		 boolean blockFound = false;
		cartBlock = new ArrayList<CartBlock>();
		for (CartBlock cartBlock : cartBlocks) {
            // If item already exists in the shopping cart we just change the quantity
            if (cartBlock.getTarif().getBlock().equals(item)) {
                cartBlock.setQuantity(cartBlock.getQuantity() + 1);
                blockFound = true;
            }
        }
        if (!blockFound)
            // Otherwise it's added to the shopping cart
            cartBlocks.add(new CartBlock(item, 1));

        
		return true;
	}

	@Override
	public Order creer(Personnes customer, final CreditCard creditCard, final List<CartBlock> cartItems)  {
		
		return null;
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
