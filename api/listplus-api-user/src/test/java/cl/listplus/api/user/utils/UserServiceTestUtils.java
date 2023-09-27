package cl.listplus.api.user.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.InputStream;

public final class UserServiceTestUtils {

    private static ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.registerModule(new JavaTimeModule());
    }

    private UserServiceTestUtils() {}

    public static <T> T responseObject(String filePath, Class<T> type) throws Exception {
        InputStream stream = UserServiceTestUtils.class.getClassLoader().getResourceAsStream(filePath);
        return MAPPER.readValue(stream, type);
    }
}
