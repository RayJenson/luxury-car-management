package cn.xzlei.annotation;

import cn.xzlei.valid.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {
    
    String message() default "用户名格式不正确";

    String prefix() default "用户名";
    
    int min() default 4;
    
    int max() default 20;
    
    boolean allowSpecialChars() default true;
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
