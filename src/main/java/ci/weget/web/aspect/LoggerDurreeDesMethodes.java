package ci.weget.web.aspect;

import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggerDurreeDesMethodes {
	private long t1;
	private long t2;
	Logger log = Logger.getLogger(this.getClass().getName());

	@Pointcut("execution(* ci.weget.web.controller.*.*(..))")
	public void log() {
	}

	@Before("log()")
	public void avant(JoinPoint thisJointPoint) {
		System.out.println("********************************");
        log.info("************************");
		System.out.println("Methode" + thisJointPoint.getSignature());
		log.info("Methode" + thisJointPoint.getSignature());
		System.out.println("Avant");
		log.info("Avant");
		t1 = System.currentTimeMillis();

	}

	@After("log()")
	public void apres(JoinPoint thisJointPoint) {
		t2 = System.currentTimeMillis();
		System.out.println("************************************");
		log.info("Apres");
		System.out.println("Apres");
		System.out.println("Methode" + thisJointPoint.getSignature());
        System.out.println("Durree" + (t2 - t1)+"ms");
        log.info("Durree" + (t2 - t1)+"ms");
		System.out.println("************************************");
        log.info("************************");
	}
}
