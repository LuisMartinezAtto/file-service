package io.bshouse.dfsm.file.service.exception.global;
import io.bshouse.dfsm.file.service.dto.ResponseDTO;
import io.bshouse.dfsm.file.service.exception.AttachmentNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.List;

import static io.bshouse.dfsm.file.service.constants.ConstantsError.INTERNAL_SERVER_ERROR;
import static io.bshouse.dfsm.file.service.constants.ConstantsError.NOT_FOUND_ERROR;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    protected ResponseEntity<Object> exception(Exception e) {
        this.logger.error("error ", e);
        return new ResponseEntity<>(ResponseDTO.builder().errors(List.of(INTERNAL_SERVER_ERROR)).status(false).build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AttachmentNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<Object> AttachmentNotFoundException(AttachmentNotFoundException e) {
        this.logger.error("error ", e);
        return new ResponseEntity<>(ResponseDTO.builder().errors(List.of(NOT_FOUND_ERROR)).status(false).build(),
                HttpStatus.NOT_FOUND);
    }
}
