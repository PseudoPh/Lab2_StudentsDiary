package studentsdiary_lab2.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class BeanLoggingAspect {

    @Pointcut("execution(public * studentsdiary_lab2.command..*.execute(..)) "
            + "|| execution(public * studentsdiary_lab2.repository..*(..)) "
            + "|| execution(public * studentsdiary_lab2.storage..*(..)) "
            + "|| execution(public * studentsdiary_lab2.factory..*(..)) "
            + "|| execution(public * studentsdiary_lab2.observer..*(..))")
    private void trackedMethods() {
    }

    @Before("trackedMethods()")
    public void logBefore(JoinPoint joinPoint) {
        AspectLogger.write("call  " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning("trackedMethods()")
    public void logAfterReturning(JoinPoint joinPoint) {
        AspectLogger.write("done  " + joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(pointcut = "trackedMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        AspectLogger.write("fail  " + joinPoint.getSignature().toShortString()
                + " (" + exception.getClass().getSimpleName() + ": " + exception.getMessage() + ")");
    }
}