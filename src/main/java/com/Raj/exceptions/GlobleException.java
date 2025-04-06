package com.Raj.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice

public class GlobleException {


    @ExceptionHandler({SellerExceptions.class})
    public ResponseEntity<ErrorDetails> sellerExceptionHandler(SellerExceptions se, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setError(se.getMessage());
        errorDetails.setDetails(req.getDescription(false));
        errorDetails.setTimestamp(java.time.LocalDateTime.now());
        return new ResponseEntity<>(errorDetails,org.springframework.http.HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ProductException.class})
    public ResponseEntity<ErrorDetails> ProductExceptionHandler(SellerExceptions se, WebRequest req){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setError(se.getMessage());
        errorDetails.setDetails(req.getDescription(false));
        errorDetails.setTimestamp(java.time.LocalDateTime.now());
        return new ResponseEntity<>(errorDetails,org.springframework.http.HttpStatus.BAD_REQUEST);

    }


}
