package ru.ipanteev.oop23.temperature.model;

public record UnitsInfoRecord(String key, String caption, Converter toCelsium, Converter fromCelsium) {
    @Override
    public String toString() {
        return caption;
    }
}
