package blockchain.medical_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckResultDTO {
    private Result result;
    private String from;
    private String errorMessage;

    public CheckResultDTO(Result result, String from) {
        this.result = result;
        this.from = from;
    }

    public enum Result {
        ACCEPTABLE,
        NOT_ACCEPTABLE,
        INVALID_BLOCK,
        INVALID_CURRENT_BLOCKCHAIN,
        ERROR,
        UNKNOWN,
    }
}
