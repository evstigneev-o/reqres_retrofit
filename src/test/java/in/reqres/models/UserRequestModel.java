package in.reqres.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestModel {
    private String name;
    private String job;
}
