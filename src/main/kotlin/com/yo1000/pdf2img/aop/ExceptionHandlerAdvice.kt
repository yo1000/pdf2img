package com.yo1000.pdf2img.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

/**
 *
 * @author yo1000
 */
@Aspect
@Component
class ExceptionHandlerAdvice {
    @Around("execution(* org.springframework.boot.CommandLineRunner+.run(..))")
    fun handleException(joinPoint: ProceedingJoinPoint) {
        try {
            joinPoint.proceed()
        } catch (e: Exception) {
            System.err.println(e.message)
        }
    }
}