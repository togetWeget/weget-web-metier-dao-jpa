package ci.weget.web.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggerDurree {
	private long t1, t2;

	@Pointcut("call(Personne ci.weget.web.metier.MembreMetierImpl.creer(..))")
	public void log() {}

	@Before("log()")
	public void avant(JoinPoint thisJointPoint) {
		System.out.println("Methode"+thisJointPoint);
		System.out.println("************************");
		System.out.println("Avant");
		t1 = System.currentTimeMillis();
		

	}

	@After("log()")
	public void apres() {
		t2 = System.currentTimeMillis();
		System.out.println("Apres");
		System.out.println("Durree"+(t2-t1));
		System.out.println("************************");
	}
}
