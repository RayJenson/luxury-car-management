package cn.xzlei.valid;

import cn.xzlei.annotation.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    
    private int min;
    private int max;
    private boolean allowSpecialChars;
    private String prefix;
    
    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.allowSpecialChars = constraintAnnotation.allowSpecialChars();
        this.prefix = constraintAnnotation.prefix();
    }
    
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        // 允许null值（由@NotBlank处理空值检查）
        if (username == null) {
            return true;
        }
        
        // 检查长度
        if (username.length() < min || username.length() > max) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                this.prefix+"长度必须在" + min + "-" + max + "个字符之间")
                .addConstraintViolation();
            return false;
        }
        
        // 检查不能是null或undefined
        if ("null".equalsIgnoreCase(username) || "undefined".equalsIgnoreCase(username)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.prefix+"不能是null或undefined")
                .addConstraintViolation();
            return false;
        }
        
        // 检查特殊字符
        if (!allowSpecialChars) {
            if (!username.matches("^[a-zA-Z0-9-_\\u4e00-\\u9fa5]+$")) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(this.prefix+"只能包含字母、数字、下划线和中文字符")
                    .addConstraintViolation();
                return false;
            }
        }
        
        return true;
    }
}
