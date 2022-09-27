package ca.sait.backup.exception;



import ca.sait.backup.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * exception handling class
 */
@ControllerAdvice
public class CustomExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handle(Exception e){

        logger.error("[ System errors ]{}",e.getMessage());

        if( e instanceof XDException ){

            XDException xdException = (XDException) e;

            return JsonData.buildError(xdException.getCode(),xdException.getMsg());

        }else {

            return JsonData.buildError(e.getMessage());

        }

    }



}
