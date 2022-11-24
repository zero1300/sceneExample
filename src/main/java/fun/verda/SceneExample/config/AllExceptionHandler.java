package fun.verda.SceneExample.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Msg defaultErrorHandler(Exception e) {
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            return new Msg(404, e.getMessage());
        }else {
            return new Msg(500, e.getMessage());
        }
    }
}

class Msg {
    int code;
    String msg;

    public Msg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}