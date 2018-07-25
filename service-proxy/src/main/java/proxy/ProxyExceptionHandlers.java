package proxy;

import com.netflix.zuul.exception.ZuulException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Created by H on 2018. 7. 25.
 */

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ProxyExceptionHandlers {

    @ExceptionHandler(ZuulException.class)
    public ResponseEntity<VndErrors> zuulExceptionHandler(ZuulException ze) {
        return error(ze, HttpStatus.resolve(ze.nStatusCode), ze.errorCause);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<VndErrors> exceptionHandler(Exception e) {
        return error(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private ResponseEntity<VndErrors> error(Exception e, HttpStatus httpStatus, String logRef) {
        String msg = Optional.of(e.getMessage()).orElse(e.getClass().getSimpleName());
        return new ResponseEntity<VndErrors>(new VndErrors(logRef, msg), httpStatus);
    }
}
