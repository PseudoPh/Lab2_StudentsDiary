package studentsdiary_lab2.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class ExecutionTimeAspect {

    @Pointcut("execution(public * studentsdiary_lab2.command..*.execute(..)) "
            + "|| execution(public * studentsdiary_lab2.repository..*(..)) "
            + "|| execution(public * studentsdiary_lab2.storage..*(..)) "
            + "|| execution(public * studentsdiary_lab2.factory..*(..)) "
            + "|| execution(public * studentsdiary_lab2.observer..*(..))")
    private void measuredMethods() {
    }

    @Around("measuredMethods()")
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        long startNanos = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long elapsedMicros = (System.nanoTime() - startNanos) / 1_000;
            AspectLogger.write("time  " + joinPoint.getSignature().toShortString()
                    + " (" + elapsedMicros + " mcs)");
        }
    }
}