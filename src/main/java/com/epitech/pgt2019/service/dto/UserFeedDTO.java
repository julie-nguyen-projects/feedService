package com.epitech.pgt2019.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UserFeed entity.
 */
public class UserFeedDTO implements Serializable {

    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserFeedDTO userFeedDTO = (UserFeedDTO) o;
        if (userFeedDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userFeedDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserFeedDTO{" +
            "id=" + getId() +
            "}";
    }
}
