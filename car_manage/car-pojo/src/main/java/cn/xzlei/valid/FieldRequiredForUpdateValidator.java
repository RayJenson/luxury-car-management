package cn.xzlei.valid;

import cn.xzlei.annotation.FieldRequiredForUpdate;
import cn.xzlei.entity.Product;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldRequiredForUpdateValidator implements ConstraintValidator<FieldRequiredForUpdate, Product> {
    
    @Override
    public boolean isValid(Product product, ConstraintValidatorContext context) {
        if (product == null) {
            return true; // 让其他验证器处理null情况
        }
        
        // 检查id字段是否为null
        if (product.getId() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                   .addPropertyNode("id")
                   .addConstraintViolation();
            return false;
        }
        
        return true;
    }
}
