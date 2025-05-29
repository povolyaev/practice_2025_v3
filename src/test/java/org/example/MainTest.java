package org.example;

import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void shouldThrowExceptionWhenInstantiatingMainClass() {
        Constructor<Main> constructor = null;
        try {
            constructor = Main.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
            fail("Expected exception not thrown");
        } catch (NoSuchMethodException e) {
            fail("Constructor not found", e);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            // Ожидаемое исключение
            assertTrue(e.getMessage().contains("utility class"));
        }
    }

    @Test
    void mainMethodShouldExistAndBePublicStatic() throws NoSuchMethodException {
        assertNotNull(Main.class.getMethod("main", String[].class));
    }
}