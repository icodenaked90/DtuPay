/*
@Author: Simon s163595
 */

package clientApp.models;


public class ResponseStatus {
    public boolean status;
    public String errorMessage;

    public ResponseStatus(boolean status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
