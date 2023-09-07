package cl.listplus.api.user.repository;

import cl.listplus.api.user.model.User;
import org.springframework.data.jpa.domain.Specification;

public final class UserSpecifications {

    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_EMAIL = "email";

    public static Specification<User> hasUsername(String username) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(PARAM_USERNAME), username);
    }

    public static Specification<User> hasEmail(String email) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(PARAM_EMAIL), email);
    }
}
