package ru.ipanteev.oop23.temperature.model;

import java.util.*;

public class TemperatureConverterModel implements TemperatureConverterModelInterface {
    ArrayList<ConvertResultObserver> convertResultObservers = new ArrayList<>();
    private Map<String, UnitsInfoRecord> unitsInfo;
    private String unitsKeyFrom;
    private String unitsKeyTo;
    private double degrees;
    private double degreesConvertResult;

    private void notifyObservers() {
        for (ConvertResultObserver convertResultObserver : convertResultObservers) {
            convertResultObserver.updateConvertResult();
        }
    }

    @Override
    public Collection<UnitsInfoRecord> getUnitsInfo() {
        return unitsInfo.values();
    }

    @Override
    public double getConvertResult() {
        return degreesConvertResult;
    }

    @Override
    public void registerObserver(ConvertResultObserver observer) {
        convertResultObservers.add(observer);
    }

    @Override
    public void initialize() {
        unitsInfo = new HashMap<>();
        unitsInfo.put("C", new UnitsInfoRecord("C", "Цельсия", degrees -> degrees, degrees -> degrees));
        unitsInfo.put("K", new UnitsInfoRecord("K", "Кельвина", degrees -> degrees - 273.15, degrees -> degrees + 273.15));
        unitsInfo.put("F", new UnitsInfoRecord("F", "Фаренгейта", degrees -> (degrees - 32) * 5 / 9, degrees -> (degrees * 9 / 5) + 32));
    }

    @Override
    public void setUnitsKeyFrom(String unitsKey) {
        unitsKeyFrom = unitsKey;
    }

    @Override
    public void setUnitsKeyTo(String unitsKey) {
        unitsKeyTo = unitsKey;
    }

    @Override
    public void setDegrees(double degrees) {
        this.degrees = degrees;
    }

    @Override
    public void convert() {
        if (unitsKeyFrom.equals(unitsKeyTo)) {
            degreesConvertResult = degrees;
        } else {
            degreesConvertResult = unitsInfo.get(unitsKeyFrom).toCelsium().convert(degrees);
            degreesConvertResult = unitsInfo.get(unitsKeyTo).fromCelsium().convert(degreesConvertResult);
        }

        notifyObservers();
    }

}
