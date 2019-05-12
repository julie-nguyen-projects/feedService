package com.epitech.pgt2019.service.mapper;

import com.epitech.pgt2019.domain.*;
import com.epitech.pgt2019.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {



    default Comment fromId(String id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
