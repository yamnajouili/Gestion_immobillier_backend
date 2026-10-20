package com.example.gestionimmobilier.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(LockedException.class)
//    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
//        return ResponseEntity
//                .status(UNAUTHORIZED)
//                .body(
//                        ExceptionResponse.builder()
//                                .businessErrorCode(ACCOUNT_LOCKED.getCode())
//                                .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
//                                .error(exp.getMessage())
//                                .build()
//                );
//    }

//    @ExceptionHandler(DisabledException.class)
//    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
//        return ResponseEntity
//                .status(UNAUTHORIZED)
//                .body(
//                        ExceptionResponse.builder()
//                                .businessErrorCode(ACCOUNT_DISABLED.getCode())
//                                .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
//                                .error(exp.getMessage())
//                                .build()
//                );
//    }


//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ExceptionResponse> handleException() {
//        return ResponseEntity
//                .status(UNAUTHORIZED)
//                .body(
//                        ExceptionResponse.builder()
//                                .businessErrorCode(BAD_CREDENTIALS.getCode())
//                                .businessErrorDescription(BAD_CREDENTIALS.getDescription())
//                                .error("Login and / or Password is incorrect")
//                                .build()
//                );
//    }

//    @ExceptionHandler(MessagingException.class)
//    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
//        return ResponseEntity
//                .status(INTERNAL_SERVER_ERROR)
//                .body(
//                        ExceptionResponse.builder()
//                                .error(exp.getMessage())
//                                .build()
//                );
//    }

//    @ExceptionHandler(ActivationTokenException.class)
//    public ResponseEntity<ExceptionResponse> handleException(ActivationTokenException exp) {
//        return ResponseEntity
//                .status(BAD_REQUEST)
//                .body(
//                        ExceptionResponse.builder()
//                                .error(exp.getMessage())
//                                .build()
//                );
//    }

//    @ExceptionHandler(OperationNotPermittedException.class)
//    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp) {
//        return ResponseEntity
//                .status(BAD_REQUEST)
//                .body(
//                        ExceptionResponse.builder()
//                                .error(exp.getMessage())
//                                .build()
//                );
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ValidationError(fieldName, errorMessage));
        });

        ExceptionResponse response = ExceptionResponse.builder()
                .validationErrors(errors)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
//        exp.printStackTrace();
//        return ResponseEntity
//                .status(INTERNAL_SERVER_ERROR)
//                .body(
//                        ExceptionResponse.builder()
//                                .businessErrorDescription("Internal error, please contact the admin")
//                                .error(exp.getMessage())
//                                .build()
//                );
//    }




    @ExceptionHandler(ProprietaireHasBiensException.class)
    public ResponseEntity<ExceptionResponse> handleProprietaireHasBiensException(
            ProprietaireHasBiensException exp) {

        BusinessErrorCodes errorCode =
                BusinessErrorCodes.PROPRIETAIRE_HAS_BIENS;

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(errorCode.getCode())
                                .businessErrorDescription(
                                        errorCode.getDescription()
                                )
                                .error(exp.getMessage())
                                .build()
                );
    }


    @ExceptionHandler(BienHasContratException.class)
    public ResponseEntity<ExceptionResponse> handleBienHasContratException(
            BienHasContratException exp) {

        BusinessErrorCodes errorCode =
                BusinessErrorCodes.BIEN_HAS_CONTRAT;

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(errorCode.getCode())
                                .businessErrorDescription(errorCode.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }





    @ExceptionHandler(UserHasDependenciesException.class)
    public ResponseEntity<ExceptionResponse> handleUserHasDependenciesException(
            UserHasDependenciesException exp) {

        BusinessErrorCodes errorCode =
                BusinessErrorCodes.USER_HAS_DEPENDENCIES;

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(errorCode.getCode())
                                .businessErrorDescription(errorCode.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }



}
