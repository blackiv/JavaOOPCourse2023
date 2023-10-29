package ru.ipanteev.oop23.temperature_main;

import ru.ipanteev.oop23.temperature.controller.TemperatureConverterController;
import ru.ipanteev.oop23.temperature.model.TemperatureConverterModel;

public class Main {
    public static void main(String[] args) {
        TemperatureConverterController temperatureConverterController = new TemperatureConverterController(new TemperatureConverterModel());
        temperatureConverterController.start();
    }
}
