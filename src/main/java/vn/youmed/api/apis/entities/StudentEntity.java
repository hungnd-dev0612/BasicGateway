package vn.youmed.api.apis.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentEntity {
    private String _id;
    private String name;
    private String birthday;
    private String classId;
}
