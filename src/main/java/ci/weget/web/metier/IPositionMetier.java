package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Position;

public interface IPositionMetier extends Imetier<Position, Long> {
List<Position> findAllPositionsParIdMembre(Long id);
}
