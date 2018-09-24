package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.CategoryBlockRepository;
import ci.weget.web.entites.CategoryBlock;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class CategoryBlockMetierImpl implements ICategoryBlockMetier {
@Autowired
private CategoryBlockRepository categoryBlockRepository;
	@Override
	public CategoryBlock creer(CategoryBlock entity) throws InvalideTogetException {
		
		return categoryBlockRepository.save(entity);
	}

	@Override
	public CategoryBlock modifier(CategoryBlock entity) throws InvalideTogetException {
		
		return categoryBlockRepository.save(entity);
	}

	@Override
	public List<CategoryBlock> findAll() {
		
		return categoryBlockRepository.findAll();
	}

	@Override
	public CategoryBlock findById(Long id) {
		
		return categoryBlockRepository.getCategoryBlockByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<CategoryBlock> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CategoryBlock rechercheParLibelle(String libelle) {
		
		return categoryBlockRepository.findByLibelle(libelle);
	}

}
