package searchengine.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private boolean result;
    private String error;
    
    public ApiResponse(boolean result) {
        this.result = result;
    }
    
    public ApiResponse(boolean result, String error) {
        this.result = result;
        this.error = error;
    }
    
    public boolean isResult() {
        return result;
    }
    
    public void setResult(boolean result) {
        this.result = result;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
}