package in.reqres.models;

import lombok.Data;

@Data
public class UpdateUserResponseModel {
    private String name;
    private String job;
    private String updatedAt;
}
