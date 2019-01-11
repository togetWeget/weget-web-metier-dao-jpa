package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.entites.Experiences;

public interface ExperienceRepository extends JpaRepository<Experiences, Long>{

}
