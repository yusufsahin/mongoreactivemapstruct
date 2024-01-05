package tr.com.provera.pameraapi.model.common;
public interface SoftDeletable {
    void setIsDeleted(boolean isDeleted);
    Boolean getIsDeleted();
}