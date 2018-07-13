package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.CartBlock;
import ci.weget.web.entites.Tarif;
import ci.weget.web.metier.ITarifMetier;
import ci.weget.web.modeles.Reponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ShopingCartController {
	@Autowired
	private ITarifMetier tarifMetier;
	@Autowired
	private ObjectMapper jsonMapper;

	private List<CartBlock> cartBlocks;

	private Tarif tarif;

	@PostMapping("/addBlockToCard")
	public String addBlockToCart(Blocks block) {
		Reponse<Blocks> reponse;

		block = tarifMetier.getTarifBlockId(block.getId());

		cartBlocks = new ArrayList<CartBlock>();

		boolean blockFound = false;
		for (CartBlock cartBlock : cartBlocks) {
			// If item already exists in the shopping cart we just change the quantity
			if (cartBlock.getTarif().getBlock().equals(block)) {
				cartBlock.setQuantity(cartBlock.getQuantity() + 1);
				blockFound = true;
			}
		}
		if (!blockFound)
			// Otherwise it's added to the shopping cart
			tarif = new Tarif();
		tarif.setBlock(block);

		cartBlocks.add(new CartBlock(tarif, 1));

		return jsonMapper.writeValueAsString(reponse);
	}

}
