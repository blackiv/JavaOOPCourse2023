package ru.ipanteev.oop23.temperature.controller;

import ru.ipanteev.oop23.temperature.model.TemperatureConverterModelInterface;
import ru.ipanteev.oop23.temperature.view.TemperatureConverterView;

import javax.swing.*;

public class TemperatureConverterController implements TemperatureConverterControllerInterface {

    private final TemperatureConverterModelInterface model;


    private final TemperatureConverterView view;

    public TemperatureConverterController(TemperatureConverterModelInterface model) {
        this.model = model;
        model.initialize();
        view = new TemperatureConverterView(model, this);
        view.updateCalcButton(false);
    }

    @Override
    public void doConvert() {
        model.convert();
    }

    @Override
    public void setUnitsFrom(String unitsKey) {
        model.setUnitsKeyFrom(unitsKey);
    }

    @Override
    public void setUnitsTo(String unitsKey) {
        model.setUnitsKeyTo(unitsKey);
    }

    @Override
    public void setDegrees(String degrees) {
        try {
            double value = Double.parseDouble(degrees);
            model.setDegrees(value);
            view.updateCalcButton(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Введенное значение не является числом");
            view.updateCalcButton(false);
        }
    }

    public void start() {
        SwingUtilities.invokeLater(view::showView);
    }
}
