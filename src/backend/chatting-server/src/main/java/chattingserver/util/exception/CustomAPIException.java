package chattingserver.util.exception;

import chattingserver.util.constant.ErrorCode;
import lombok.Getter;

@Getter
public class CustomAPIException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private final String description;
    private final String code;
    private final int status;
    private String errorMsg;

    public CustomAPIException(ErrorCode errorCode, String errorMsg){
        this.description = errorCode.getDescription();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.errorMsg = errorMsg;
    }
}