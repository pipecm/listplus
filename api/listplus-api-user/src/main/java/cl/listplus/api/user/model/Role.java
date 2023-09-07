package cl.listplus.api.user.model;

import java.util.stream.Stream;

public enum Role {
    USER(0), MODERATOR(1), ADMIN(2);

    private int code;

    Role(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Role valueOf(int code) {
        return Stream.of(Role.values())
                .filter(role -> role.getCode() == code)
                .findFirst()
                .orElse(Role.USER);
    }
}
