package com.arton.backend.infra.event.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

@Component
@Aspect
public class PublishEventAspect implements ApplicationEventPublisherAware {

    private final String spelRegex = "^#\\{(.*)\\}$";
    private final Pattern spelPattern = Pattern.compile(spelRegex);

    private ApplicationEventPublisher eventPublisher;
    private ExpressionParser expressionParser = new SpelExpressionParser();

    @Pointcut("@annotation(publishEvent)")
    public void pointcut(PublishEvent publishEvent) {
    }

    /**
     * compile time에 체크하지 않음 주의!!!!!!!!
     *
     * @param publishEvent
     * @param result
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @AfterReturning(pointcut = "pointcut(publishEvent)", returning = "result", argNames = "publishEvent,result")
    public void afterReturning(PublishEvent publishEvent, Object result) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Object event;

        if (result == null) {
            // method 반환 값이 void일 때에는 new eventType(this);
            event = publishEvent.eventType()
                    .getDeclaredConstructor()
                    .newInstance();

        } else if (StringUtils.isEmpty(publishEvent.params())) {
            // params가 비어 있는 경우 new eventType(retVal);
            event = publishEvent.eventType()
                    .getConstructor(result.getClass())
                    .newInstance(result);

        } else if (isSpel(publishEvent.params())) {
            // params가 spel인 경우 new eventType(parsed(publishEvent.params()))
            String spel = publishEvent.params().replaceAll(spelRegex, "$1");
            Object constructArg = expressionParser.parseExpression(spel).getValue(result);
            event = publishEvent.eventType()
                    .getDeclaredConstructor(constructArg.getClass())
                    .newInstance(constructArg);

        } else {
            // params가 그냥 string인 경우 new eventType(publishEvent.params());
            event = publishEvent.eventType().getConstructor(String.class).newInstance(publishEvent.params());
        }

        eventPublisher.publishEvent(event);
    }

    private boolean isSpel(String params) {
        return spelPattern.matcher(params).matches();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}