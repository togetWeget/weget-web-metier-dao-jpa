package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.PositionRepository;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.Position;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class PositionMetierImpl implements IPositionMetier {
	@Autowired
	private PositionRepository positionRepository;

	@Override
	public Position creer(Position entity) throws InvalideTogetException {

		return positionRepository.save(entity);
	}

	@Override
	public Position modifier(Position modif) throws InvalideTogetException {

		return positionRepository.save(modif);
	}

	@Override
	public List<Position> findAll() {

		return positionRepository.findAll();
	}

	@Override
	public Position findById(Long id) {

		return positionRepository.getByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Position> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {

		return false;
	}

	@Override
	public List<Position> findAllPositionsParIdMembre(Long id) {

		return positionRepository.findAllPositionsParIdMembre(id);
	}

}
