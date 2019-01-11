package ci.weget.web.config;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.dao.BlocksRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.TarifRepository;
import ci.weget.web.entites.Abonnement;
import ci.weget.web.entites.Block;
import ci.weget.web.entites.Tarif;

@Component
public class ScheduledTasks {
	@Autowired
	private AbonnementRepository abonnementRepository;
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

   
	@Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
    	List<Abonnement> abonnes = abonnementRepository.findAll();
    	for(Abonnement abonne: abonnes) {
    		
    	 LocalDateTime dateExpire=abonne.getDateExpire();
    	 LocalDateTime currentTime = LocalDateTime.now();
    	 if (currentTime.isAfter(dateExpire)) {
    	        log.info("Abonne supprimer de la base");
          abonnementRepository.findAbonneParId(abonne.getId());
          abonne.setActive(false);
          }
    		
    		}
        log.info("l'heure actuelle est : {}", dateFormat.format(new Date()));
    }
}
