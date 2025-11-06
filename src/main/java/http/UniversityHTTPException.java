package http;

public class UniversityHTTPException extends Exception {
    public UniversityHTTPException(String message) {
        super(message);
    }

    public UniversityHTTPException(String message, Throwable cause){
        super(message, cause);
    }
}
