package cat.itacademy.s05.t02.n01.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception {
    private HttpStatus status;
    
    public CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    
    public HttpStatus getStatus() {
        return status;
    }
}
