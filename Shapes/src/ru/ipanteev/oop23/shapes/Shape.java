package ru.ipanteev.oop23.shapes;

/**
 * Интерфейс фигуры
 **/
public interface Shape {
    /**
     * Ширина
     */
    double getWidth();

    /**
     * Высота
     */
    double getHeight();

    /**
     * Площадь
     */
    double getArea();

    /**
     * Периметр
     */
    double getPerimeter();
}
