package chattingserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CommonAPIMessage {
    private ResultEnum message;
    private Object data;

    public enum ResultEnum{
        success, failed
    }

    public CommonAPIMessage() {
    }

    public CommonAPIMessage(ResultEnum message) {
        this.message = message;
    }

    public CommonAPIMessage(ResultEnum message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ResultEnum getMessage() {
        return message;
    }

    public void setMessage(ResultEnum message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
