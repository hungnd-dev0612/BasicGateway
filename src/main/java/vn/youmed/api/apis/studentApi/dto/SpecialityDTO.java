package vn.youmed.api.apis.studentApi.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.youmed.api.apis.entities.SpecialityEntity;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SpecialityDTO {
    @JsonProperty("_id")
    private String id;
    private String name;

    public static SpecialityDTO convertToDTO(SpecialityEntity entity) {
        return JsonObject.mapFrom(entity).mapTo(SpecialityDTO.class);
    }

    public static SpecialityEntity convertToEntity(SpecialityDTO dto) {
        return JsonObject.mapFrom(dto).mapTo(SpecialityEntity.class);
    }

}
