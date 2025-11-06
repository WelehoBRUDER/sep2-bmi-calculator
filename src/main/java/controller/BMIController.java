package controller;

import db.BMIResultService;
import db.LocalizationService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class BMIController {
    private final String[] languages = {"EN", "FR", "UR", "VI"};
    private String lang = languages[0];
    private ResourceBundle rb = ResourceBundle.getBundle("Messages", new Locale("en", "US"));
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
    ComboBox<String> languageSelect;

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
            errorMessage.setText(rb.getString("errorMessage.text"));
        }
    }

    public void displayResult() {
        String text = rb.getString("bmiLabel.text") + ": ";
        if (result != 0) {
            bmiLabel.setText(text + String.format("%.2f", result));
        } else {
            bmiLabel.setText(text);
        }
    }

    public void createLanguageSelect() {
        languageSelect.getItems().clear();
        languageSelect.getItems().addAll(languages);
        languageSelect.getSelectionModel().selectFirst();

        languageSelect.setOnAction(event -> {
            lang = languageSelect.getSelectionModel().getSelectedItem();
            System.out.println(lang);
            setLanguage();
        });
    }

    public void setLanguage() {
        switch (lang) {
            case "EN":
                rb = ResourceBundle.getBundle("Messages", new Locale("en", "us"));
                break;
            case "FR":
                rb = ResourceBundle.getBundle("Messages", new Locale("fr", "FR"));
                break;
            case "UR":
                rb = ResourceBundle.getBundle("Messages", new Locale("ur", "PK"));
                break;
            case "VI":
                rb = ResourceBundle.getBundle("Messages", new Locale("vi", "VN"));
                break;
        }
        updateAllTexts();
    }

    private void updateAllTexts() {
        weightLabel.setText(rb.getString("weightLabel.text"));
        heightLabel.setText(rb.getString("heightLabel.text"));
        bmiLabel.setText(rb.getString("bmiLabel.text"));
        calculateButton.setText(rb.getString("calculateButton.text"));
        errorMessage.setText("");
        validateValues();
        displayResult();
    }
}
