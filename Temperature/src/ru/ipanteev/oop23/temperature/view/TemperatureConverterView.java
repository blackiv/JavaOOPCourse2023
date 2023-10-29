package ru.ipanteev.oop23.temperature.view;

import ru.ipanteev.oop23.temperature.controller.TemperatureConverterControllerInterface;
import ru.ipanteev.oop23.temperature.model.ConvertResultObserver;
import ru.ipanteev.oop23.temperature.model.UnitsInfoRecord;
import ru.ipanteev.oop23.temperature.model.TemperatureConverterModelInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;

import static java.awt.GridBagConstraints.HORIZONTAL;

public class TemperatureConverterView extends JFrame implements ConvertResultObserver {
    private final TemperatureConverterModelInterface model;
    private final TemperatureConverterControllerInterface controller;
    private JTextField degreesNumberField;
    private JButton calcButton;
    private JLabel resultText;

    @Override
    public void updateConvertResult() {
        resultText.setText(String.format("%.2f", model.getConvertResult()));
    }

    private static class OneCellBagConstraints extends GridBagConstraints {
        public OneCellBagConstraints() {
            fill = BOTH;
            anchor = NORTHWEST;
            gridx = 0;
            gridy = 0;
            gridwidth = 1;
            gridheight = 1;
            weightx = 1.0f; // чтобы красиво пропорционально были все колонки
            weighty = 0f;   // а вот строки нет
            insets.right = 10;  // отступ между колонками
            insets.bottom = 10;  //строками
        }

        public void setCell(int x, int y) {
            gridx = x;
            gridy = y;
        }
    }

    public TemperatureConverterView(TemperatureConverterModelInterface model, TemperatureConverterControllerInterface controller) {
        this.model = model;
        model.registerObserver(this);
        this.controller = controller;
        initializeWindow();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initializeWindow() {
        //заголовок
        String TITLE = "Конвертер температур";
        setTitle(TITLE);

        //размеры и положение
        int WINDOW_WIDTH = 600;
        int WINDOW_HEIGHT = 400;
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - WINDOW_WIDTH) / 2, (screenSize.height - WINDOW_HEIGHT) / 2);

        // менеджер содержимого
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        //содержимое
        OneCellBagConstraints oneCellBagConstraints = new OneCellBagConstraints();

        add(new JLabel("Градусы"), oneCellBagConstraints);

        oneCellBagConstraints.setCell(1, 0);
        add(new JLabel("Единица измерения из"), oneCellBagConstraints);

        oneCellBagConstraints.setCell(2, 0);
        add(new JLabel("Единица измерения в"), oneCellBagConstraints);

        degreesNumberField = new JTextField();
        degreesNumberField.addActionListener(e -> controller.setDegrees(e.getActionCommand()));
        degreesNumberField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                degreesNumberField.postActionEvent();
            }
        });

        oneCellBagConstraints.setCell(0, 1);
        add(degreesNumberField, oneCellBagConstraints);

        oneCellBagConstraints.setCell(1, 1);
        JComboBox<UnitsInfoRecord> degreesUnitsFrom = new JComboBox<>();
        degreesUnitsFrom.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                controller.setUnitsFrom(((UnitsInfoRecord) e.getItem()).key());
            }
        });

        add(degreesUnitsFrom, oneCellBagConstraints);

        oneCellBagConstraints.setCell(2, 1);
        JComboBox<UnitsInfoRecord> degreesUnitsTo = new JComboBox<>();
        degreesUnitsTo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                controller.setUnitsTo(((UnitsInfoRecord) e.getItem()).key());
            }
        });

        add(degreesUnitsTo, oneCellBagConstraints);

        for (UnitsInfoRecord scale : model.getUnitsInfo()) {
            degreesUnitsFrom.addItem(scale);
            degreesUnitsTo.addItem(scale);
        }

        GridBagConstraints rowConstraints = new GridBagConstraints();
        rowConstraints.gridy = 2;
        rowConstraints.gridwidth = 3;

        calcButton = new JButton("Рассчитать");
        calcButton.addActionListener(e -> controller.doConvert());
        add(calcButton, rowConstraints);

        rowConstraints.gridy = 3;
        rowConstraints.fill = HORIZONTAL;
        rowConstraints.weightx = 1.0;

        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        resultPanel.add(new JLabel("Результат:"));
        resultText = new JLabel("");
        resultPanel.add(resultText);
        add(resultPanel, rowConstraints);

        // и заполним оставшееся снизу пространство чем нибудь пустым
        // как вариант можно просто окно в размерах зафиксировать
        rowConstraints.gridy++;
        rowConstraints.weighty = 1.0;
        add(new JLabel(), rowConstraints);
        pack();
    }

    public void showView() {
        setVisible(true);
    }

    public void updateCalcButton(boolean enabled) {
        calcButton.setEnabled(enabled);
    }

}
