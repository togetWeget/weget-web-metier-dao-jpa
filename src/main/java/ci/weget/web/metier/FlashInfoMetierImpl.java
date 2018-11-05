package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.FlashInfoRepository;
import ci.weget.web.entites.FlashInfo;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class FlashInfoMetierImpl implements IFlashInfoMetier {
@Autowired
private FlashInfoRepository flashInfoRepository;
	@Override
	public FlashInfo creer(FlashInfo entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return flashInfoRepository.save(entity);
	}

	@Override
	public FlashInfo modifier(FlashInfo entity) throws InvalideTogetException {
		
		return flashInfoRepository.save(entity);
	}

	@Override
	public List<FlashInfo> findAll() {
		
		return flashInfoRepository.findAll();
	}

	@Override
	public FlashInfo findById(Long id) {
		
		return flashInfoRepository.getByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		
	flashInfoRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<FlashInfo> entites) {
		
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<FlashInfo> findAllFlashInfoParIdSousBlock(Long id) {
		
		return flashInfoRepository.findAllFlashInfoParIdSousBlock(id);
	}

}
