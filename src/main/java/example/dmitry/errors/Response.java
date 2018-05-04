package example.dmitry.errors;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Getter
@AllArgsConstructor
public enum Response {

    ACCOUNT_ALREADY_EXIST(1001, "Account already exists"),
    ACCOUNT_NOT_FOUND(1003, "Account not found"),
    WRITE_OFF_MORE_BALANCE(1004, "Write-off more balance"),
    BALANCE_IS_NOT_ZERO_FOR_REMOVAL(1005, "Balance is not zero for removal"),
    ACCOUNT_IS_NOT_CORRECT(1006, "Account is not correct. Account must contain 5 numbers"),
    VALUE_IS_NOT_CORRECT(1007, "Value is not correct"),
    BD_CONNECTION_ERROR(1008, "B");
    private int errorCode;

    private String errorMessage;

    @SneakyThrows
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse response = BaseResponse.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
        return objectMapper.writeValueAsString(response);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class BaseResponse {

        private Integer errorCode;
        private String errorMessage;
    }

}
