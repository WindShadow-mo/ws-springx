package ws.spring.autoconfigure.condition;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author WindShadow
 * @version 2026-05-01
 */
class OnEnumProperty extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {

        MergedAnnotations annotations = metadata.getAnnotations();
        Assert.isTrue(annotations.isPresent(ConditionalOnEnumProperty.class), "The ConditionalOnEnumProperty is not present");
        MergedAnnotation<ConditionalOnEnumProperty> annotation = annotations.get(ConditionalOnEnumProperty.class);
        AnnotationAttributes attributes = annotation.asAnnotationAttributes();
        String key = attributes.getString("key");
        Assert.hasText(key, "The ConditionalOnEnumProperty key is empty");
        Class<? extends Enum> enumType = attributes.getClass("enumType");
        String enumValueField = attributes.getString("enumValueField");

        MergedAnnotation<Annotation> actualAnnotation = annotations.stream()
                .filter(e -> !e.getType().equals(ConditionalOnEnumProperty.class))
                .findFirst()
                .get();
        Enum[] havingEnums = actualAnnotation.getEnumArray(enumValueField, enumType);

        Environment env = context.getEnvironment();
        if (env.containsProperty(key)) {

            String resolvedValue = env.getProperty(key);
            String[] enumValues = StringUtils.tokenizeToStringArray(resolvedValue, ",");
            Set<Enum> resolvedEnums = resolveEnums(enumType, enumValues);
            for (Enum havingEnum : havingEnums) {
                if (resolvedEnums.contains(havingEnum)) {
                    return ConditionOutcome.match("The configuration's enumeration matches one of the options");
                }
            }
            return ConditionOutcome.noMatch("The configured enumeration was not found");
        } else {

            boolean matchIfMissing = attributes.getBoolean("matchIfMissing");
            if (matchIfMissing) {
                return ConditionOutcome.match("Configuration is missing, but the 'matchIfMissing' is true");
            } else {
                return ConditionOutcome.noMatch("Configuration is missing");
            }
        }
    }

    @Nullable
    private static <T extends Enum<T>> Set<T> resolveEnums(Class<T> enumType, String[] values) {

        if (values.length == 0) return null;
        Set<T> havingEnums = new HashSet<>();
        for (String value : values) {
            havingEnums.add(Enum.valueOf(enumType, value));
        }
        return havingEnums;
    }
}
