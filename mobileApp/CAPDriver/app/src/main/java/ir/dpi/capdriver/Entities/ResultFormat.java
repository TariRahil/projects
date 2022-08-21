package ir.dpi.capdriver.Entities;

/**
 * Created by Noroozi on 5/22/2017.
 */

public class ResultFormat {
    private Boolean result;
    private String message;

    public ResultFormat(Boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
