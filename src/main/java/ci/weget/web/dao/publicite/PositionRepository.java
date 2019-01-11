package ci.weget.web.dao.publicite;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.entites.publicite.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {

}
