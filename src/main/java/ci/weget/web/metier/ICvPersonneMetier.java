package ci.weget.web.metier;

import ci.weget.web.entites.CvPersonne;

public interface ICvPersonneMetier extends Imetier<CvPersonne, Long>{
CvPersonne	findCvPersonneParIdAbonnement(Long id);
}
