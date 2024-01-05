package tr.com.provera.pameraapi.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import tr.com.provera.pameraapi.model.common.BaseDocument;
@Data
public class Note extends BaseDocument {

    @Field(name = "name")
    private String name;

    @Field(name = "description")
    private String description;

}
