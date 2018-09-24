package ci.weget.web.metier;

import ci.weget.web.entites.CategoryBlock;

public interface ICategoryBlockMetier extends Imetier<CategoryBlock, Long> {
	CategoryBlock rechercheParLibelle(String libelle);

}
