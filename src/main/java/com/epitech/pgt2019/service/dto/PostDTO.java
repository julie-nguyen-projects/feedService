package com.epitech.pgt2019.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Post entity.
 */
public class PostDTO implements Serializable {

    private String id;

    @NotNull
    private LocalDate creationDate;

    private LocalDate lastModificationDate;

    @NotNull
    private String content;


    private String userFeedId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDate lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserFeedId() {
        return userFeedId;
    }

    public void setUserFeedId(String userFeedId) {
        this.userFeedId = userFeedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PostDTO postDTO = (PostDTO) o;
        if (postDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), postDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastModificationDate='" + getLastModificationDate() + "'" +
            ", content='" + getContent() + "'" +
            ", userFeed=" + getUserFeedId() +
            "}";
    }
}
