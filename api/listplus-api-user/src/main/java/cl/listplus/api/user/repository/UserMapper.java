package cl.listplus.api.user.repository;

import cl.listplus.api.user.dto.UserRequest;
import cl.listplus.api.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface UserMapper {
    User map(UserRequest userRequest);
    UserRequest map(User user);
}
