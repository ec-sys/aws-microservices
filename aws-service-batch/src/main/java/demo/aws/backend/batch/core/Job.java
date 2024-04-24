package demo.aws.backend.batch.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import static demo.aws.backend.batch.core.Constants.PROP_JOB_ID;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Conditional(Job.OnJobRunnerCondition.class)
@Component
public @interface Job {

    /**
     * ジョブタイプを取得する.
     *
     * @return {@link JobType}
     */
    JobType value();

    /**
     * OnJobRunnerCondition.
     */
    @Order(Ordered.HIGHEST_PRECEDENCE + 30)
    @Slf4j
    class OnJobRunnerCondition extends SpringBootCondition {

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext ctx, AnnotatedTypeMetadata metadata) {
            Map<String, Object> attrs = metadata.getAnnotationAttributes(Job.class.getName());
            if (attrs == null) {
                return ConditionOutcome.noMatch(ConditionMessage.empty());
            }
            String batchJobId = ctx.getEnvironment().getRequiredProperty(PROP_JOB_ID);
            String value = String.valueOf(attrs.get("value"));

            if (batchJobId.equals(value)) {
                return ConditionOutcome.match();
            }
            return ConditionOutcome.noMatch(ConditionMessage.empty());
        }
    }
}