package cn.wyz.wyzmall.product.exception;

import cn.wyz.common.exception.BizCodeException;
import cn.wyz.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "cn.wyz.wyzmall.product.controller")
public class WyzmallExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach(item -> {
            map.put(item.getField(), item.getDefaultMessage());
        });
        return R.error(BizCodeException.VAILD_EXCEPTION.getCode(), BizCodeException.VAILD_EXCEPTION.getMsg()).put("data", map);
    }

    @ExceptionHandler(Throwable.class)
    public R handleException(Throwable throwable) {
        System.out.println(throwable);
        return R.error(BizCodeException.UNKNOW_EXCEPTION.getCode(), BizCodeException.UNKNOW_EXCEPTION.getMsg());
    }
}
