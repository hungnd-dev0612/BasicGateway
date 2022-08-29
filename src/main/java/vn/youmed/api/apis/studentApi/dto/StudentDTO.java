package vn.youmed.api.apis.studentApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class StudentDTO {
    private String id;
    private String name;
    private String birthday;
    private String classId;

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }
}

