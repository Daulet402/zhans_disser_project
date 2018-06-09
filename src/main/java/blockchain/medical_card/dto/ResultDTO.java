package blockchain.medical_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultDTO {
    private ResultCode resultCode;
    private String from;
    private String errorMessage;

    public ResultDTO(ResultCode resultCode, String from) {
        this.resultCode = resultCode;
        this.from = from;
    }

    public enum ResultCode {
        ACCEPTABLE,
        NOT_ACCEPTABLE,
        INVALID_BLOCK,
        INVALID_CURRENT_BLOCKCHAIN,
        ERROR,
        ADDED,
        UNKNOWN,
    }
}
