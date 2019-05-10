package com.epitech.pgt2019.service.mapper;

import com.epitech.pgt2019.domain.*;
import com.epitech.pgt2019.service.dto.UserFeedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserFeed and its DTO UserFeedDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserFeedMapper extends EntityMapper<UserFeedDTO, UserFeed> {



    default UserFeed fromId(String id) {
        if (id == null) {
            return null;
        }
        UserFeed userFeed = new UserFeed();
        userFeed.setId(id);
        return userFeed;
    }
}
