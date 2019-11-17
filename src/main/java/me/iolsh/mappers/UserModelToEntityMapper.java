package me.iolsh.mappers;

import me.iolsh.api.model.UserModel;
import me.iolsh.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserModelToEntityMapper {

    User mapUserToEntity(UserModel model);

}

