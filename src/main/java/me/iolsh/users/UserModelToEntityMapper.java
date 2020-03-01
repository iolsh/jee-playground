package me.iolsh.users;

import org.mapstruct.Mapper;

@Mapper
public interface UserModelToEntityMapper {

    User mapUserToEntity(UserModel model);

}

