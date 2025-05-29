/**
 * Основной пакет проекта.
 */
package org.example;

/**
 * Утилитный класс для запуска программы.
 */
public final class Main {

    /**
     * Запрещает создание экземпляров класса.
     */
    private Main() {
        throw new UnsupportedOperationException("This is a utility class "
                + "and cannot be instantiated");
    }

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(final String[] args) {
        System.out.println("Hello, world!");
    }
}
