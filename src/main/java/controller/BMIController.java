package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Locale;

public class BMIController {
    private final String[] languages = {"EN", "FR", "UR", "VI"};
    private String lang = languages[0];

    @FXML
    TextField weightField;
    @FXML
    TextField heightField;

    @FXML
    Label weightLabel;
    @FXML
    Label heightLabel;
    @FXML
    Label bmiLabel;
    @FXML
    Label errorMessage;

    @FXML
    Button calculateButton;
    @FXML
    ChoiceBox<String> languageSelect;

    double weight;
    double height;
    double result;

    public void initialize() {
        errorMessage.setText("");
        createLanguageSelect();
        setLanguage();
        calculateButton.setOnAction(event -> {
            validateValues();
        });
    }

    public double calculateBMI() {
        double meters = height / 100.0;
        return weight / (meters * meters);
    }

    public void validateValues() {
        try {
            weight = Double.parseDouble(weightField.getText());
            height = Double.parseDouble(heightField.getText());
            result = calculateBMI();
            displayResult();
            errorMessage.setText("");
        } catch (Exception e) {
            errorMessage.setText("invalid_weight_or_height");
        }
    }

    public void displayResult() {
        String text = "bmi_text: ";
        bmiLabel.setText(text + String.format("%.2f", result));
    }

    public void createLanguageSelect() {
        languageSelect.getItems().clear();
        languageSelect.getItems().addAll(languages);
        languageSelect.getSelectionModel().selectFirst();

        languageSelect.setOnAction(event -> {
            lang = languageSelect.getSelectionModel().getSelectedItem();
            setLanguage();
        });
    }

    public void setLanguage() {
        switch (lang) {
            case "EN":
                Locale locale = new Locale("en-US");
            case "FR":
                locale = new Locale("fr-FR");
            case "UR":
                locale = new Locale("ur-UR");
            case "VI":
                locale = new Locale("vi-VN");
        }
    }
}
