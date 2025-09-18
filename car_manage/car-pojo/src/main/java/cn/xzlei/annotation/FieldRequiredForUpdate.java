package cn.xzlei.annotation;

import cn.xzlei.valid.FieldRequiredForUpdateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldRequiredForUpdateValidator.class)
public @interface FieldRequiredForUpdate {
    String field() default "id";
    String message() default "更新操作必须提供ID字段";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
