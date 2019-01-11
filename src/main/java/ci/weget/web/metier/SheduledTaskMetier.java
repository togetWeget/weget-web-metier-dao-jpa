package ci.weget.web.metier;

import org.springframework.scheduling.annotation.Scheduled;

public class SheduledTaskMetier {
	@Scheduled(fixedDelay = 1000)
	public void scheduleFixedDelayTask() {
	    System.out.println(
	      "ceci est un test des delai d'execution - " + System.currentTimeMillis() / 1000);
	}
}
