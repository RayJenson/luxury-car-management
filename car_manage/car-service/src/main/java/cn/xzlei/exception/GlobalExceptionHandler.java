package cn.xzlei.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.xzlei.constant.MessageConstant;
import cn.xzlei.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 全局异常拦截 
    @ExceptionHandler(NotLoginException.class)
    public R handlerException(Exception e) {
        return R.error(403,MessageConstant.USER_NOT_LOGIN,0);
    }

    @ExceptionHandler(NotRoleException.class)
    public R handlerException(NotRoleException e) {
        return R.error(403,MessageConstant.NOT_PERMISSION,0);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 取出第一个错误
        String message = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        return R.error(message);
    }

    @ExceptionHandler
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.info("数据库主键重复");
        String msg = e.getMessage();
        int index = msg.indexOf("Duplicate entry");
        msg = msg.substring(index);
        String[] split = msg.split(" ");
        System.out.println(Arrays.toString(split));
        return R.error(split[2]+"已存在");
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public R handlerSQLSyntaxErrorException(Exception e) {
        log.error("SQL语法错误：{}", e.getMessage());
        return R.error(MessageConstant.SQL_ERROR);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R handlerSQLIntegrityConstraintViolationException(Exception e) {
        String message = e.getMessage();
        if (message.contains("foreign key constraint fails")) {
            // 提取表名和字段信息
            if (message.contains("fk_car_type_id") || message.contains("car_id")) {
                return R.error("车辆类型不存在，请选择有效的车辆类型");
            } else if(message.contains("sales_record_ibfk_2")){
                return R.error("该员工下关联销售订单，无法删除，请先删除关联的销售订单信息");
            }
            return R.error("数据关联错误：所选的关联数据不存在");
        }
        return R.error("数据完整性约束违反");
    }


}