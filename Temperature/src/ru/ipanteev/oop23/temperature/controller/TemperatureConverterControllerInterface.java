package ru.ipanteev.oop23.temperature.controller;

public interface TemperatureConverterControllerInterface {
    void doConvert();

    void setUnitsFrom(String unitsKey);

    void setUnitsTo(String unitsKey);

    void setDegrees(String degrees);
}
