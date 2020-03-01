package me.iolsh.users.control;

import me.iolsh.users.boundry.UserModel;
import me.iolsh.users.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserModelToEntityMapper {

    User mapUserToEntity(UserModel model);

}

