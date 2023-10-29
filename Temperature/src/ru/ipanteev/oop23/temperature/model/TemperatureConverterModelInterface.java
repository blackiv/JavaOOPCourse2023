package ru.ipanteev.oop23.temperature.model;

import java.util.Collection;

public interface TemperatureConverterModelInterface {
    Collection<UnitsInfoRecord> getUnitsInfo();

    double getConvertResult();

    void registerObserver(ConvertResultObserver observer);

    void initialize();

    void setUnitsKeyFrom(String unitsKey);

    void setUnitsKeyTo(String unitsKey);

    void setDegrees(double degrees);

    void convert();
}
