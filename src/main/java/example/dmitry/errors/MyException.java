package example.dmitry.errors;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyException extends RuntimeException {

    private Response response;
}
