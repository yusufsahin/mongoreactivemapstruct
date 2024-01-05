package tr.com.provera.pameraapi.model.common;

import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
@Data
@Document
public abstract class BaseDocument implements SoftDeletable,Versioning{

    @Id
    private String id;

    @CreatedBy
    @Field(name = "createdBy")
    private String createdBy;

    @CreatedDate
    @Field(name = "createdAt")
    private Date createdAt;

    @LastModifiedBy
    @Field(name = "modifiedBy")
    private String modifiedBy;

    @LastModifiedDate
    @Field(name = "modifiedAt")
    private Date modifiedAt;

    @Field(name = "isDeleted")
    private Boolean isDeleted = false;

    @Field(name = "version")
    private Long version=0L;
    // MongoDB doesn't support JPA's @Version in the same way
    // You might need to implement custom versioning logic if needed
    @Override
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public Boolean getIsDeleted() {
        return isDeleted;
    }
    @Override
    public void setVersion(Long version) {
        this.version = version;
    }
    @Override
    public Long getVersion() {
        return version;
    }

    public boolean isNew() {
        return id == null;
    }

    public void incrementVersion() {
        if (version == null) {
            version = 1L;
        } else {
            version += 1;
        }
    }
}