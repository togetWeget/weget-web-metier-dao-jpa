package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.DetailAbonnement;
import ci.weget.web.entites.TypeEtablissement;

public interface ITypeEtablissementMetier extends Imetier<TypeEtablissement, Long> {
	TypeEtablissement rechercheParLibelle(String libelle);
    //List<TypeEtablissement>  findDetailAbonnementParETa(Long id);  
}
