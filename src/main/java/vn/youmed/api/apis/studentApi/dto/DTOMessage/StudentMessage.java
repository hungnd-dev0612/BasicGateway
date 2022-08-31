package vn.youmed.api.apis.studentApi.dto.DTOMessage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.http.HttpMethod;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class StudentMessage {
    @JsonProperty("api")
    private String path;
    @JsonProperty("method")
    private HttpMethod methodType;
    @JsonProperty("pathParams")
    private String pathParams;
    @JsonProperty("queryParams")
    private String queryParams;
}
