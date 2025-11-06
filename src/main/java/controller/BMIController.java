package controller;

import db.BMIResultService;
import db.LocalizationService;
import enums.Language;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class BMIController {
    private Language lang = Language.ENGLISH;
    private Map<String, String> localizedStrings;

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
    ComboBox<Language> languageSelect;

    double weight;
    double height;
    double result = 0;

    private void setLanguage(Locale locale) {
        bmiLabel.setText("");
        localizedStrings = LocalizationService.getLocalizedStrings(locale);
        weightLabel.setText(localizedStrings.getOrDefault("weight", "Weight"));
        heightLabel.setText(localizedStrings.getOrDefault("height", "Height"));
        calculateButton.setText(localizedStrings.getOrDefault("calculate", "Calculate"));
        //displayLocalTime(locale);
    }

    public void initialize() {
        createLanguageSelect();
        setLanguage(new Locale(lang.getCode(), lang.getCountry()));
        calculateButton.setOnAction(event -> {
            validateValues();
        });
    }

    public double calculateBMI() {
        double meters = height / 100.0;
        return weight / (meters * meters);
    }

    public void validateValues() {
        if (weightField.getText().isEmpty() || heightField.getText().isEmpty()) {
            errorMessage.setText("");
            return;
        }
        try {
            weight = Double.parseDouble(weightField.getText());
            height = Double.parseDouble(heightField.getText());
            result = calculateBMI();
            DecimalFormat df = new DecimalFormat("#0.00");
            bmiLabel.setText(localizedStrings.getOrDefault("result", "Your BMI is") + " " + df.format(result));
            // Save to database
            String language = Locale.getDefault().getLanguage(); // or store current locale
            BMIResultService.saveResult(weight, height, result, language);
            displayResult();
            errorMessage.setText("");
        } catch (Exception e) {
            //errorMessage.setText(rb.getString("errorMessage.text"));
        }
    }

    public void displayResult() {
        //String text = rb.getString("bmiLabel.text") + ": ";
        if (result != 0) {
            //bmiLabel.setText(text + String.format("%.2f", result));
        } else {
            //bmiLabel.setText(text);
        }
    }

    public void createLanguageSelect() {
        languageSelect.getItems().clear();
        languageSelect.getItems().addAll(Language.values());
        languageSelect.getSelectionModel().selectFirst();

        languageSelect.setOnAction(event -> {
            lang = languageSelect.getSelectionModel().getSelectedItem();
            System.out.println(lang);
            setLanguage(new Locale(lang.getCode(), lang.getCountry()));
        });
    }

//    private void updateAllTexts() {
//        localizedStrings = LocalizationService.getLocalizedStrings(locale);
//        weightLabel.setText(rb.getString("weightLabel.text"));
//        heightLabel.setText(rb.getString("heightLabel.text"));
//        bmiLabel.setText(rb.getString("bmiLabel.text"));
//        calculateButton.setText(rb.getString("calculateButton.text"));
//        errorMessage.setText("");
//        validateValues();
//        displayResult();
//    }
}

