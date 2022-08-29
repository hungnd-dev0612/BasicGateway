package vn.youmed.api.apis.studentApi.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.youmed.api.apis.entities.ClassEntity;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ClassDTO {
    @JsonProperty("_id")
    private String id;
    private String name;
    private String specialitiesId;


    public static ClassDTO convertToDTO(ClassEntity entity) {
        return JsonObject.mapFrom(entity).mapTo(ClassDTO.class);
    }

    public static ClassEntity convertToEntity(ClassDTO dto) {
        return JsonObject.mapFrom(dto).mapTo(ClassEntity.class);
    }

    public static List<ClassDTO> convertToListDTO(ClassDTO dto) {
        List<ClassDTO> list = new ArrayList<>();
        list.add(dto);
        return list;
    }

    public static List<ClassEntity> convertToListEntity(ClassEntity entity) {
        List<ClassEntity> list = new ArrayList<>();
        list.add(entity);
        return list;
    }

//    public <T> List<T> convert() {
//        List<T> list = new ArrayList<>();
//        return list;
//    }

    public static ClassEntity parseJsonToEntity(JsonObject json) {
        return json.mapTo(ClassEntity.class);
    }
}
