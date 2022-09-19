package vn.youmed.api.apis.gatewayDO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkerInfo {
    @JsonProperty("workerName")
    private String worker;
    private int numberRequest;
    private int currentRequest;
    private boolean requested;
}