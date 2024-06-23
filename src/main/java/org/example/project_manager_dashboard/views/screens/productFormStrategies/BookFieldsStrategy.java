package org.example.project_manager_dashboard.views.screens.productFormStrategies;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Map;

public class BookFieldsStrategy implements ExtraFieldsStrategy {
    @Override
    public void addExtraFields(VBox extraFieldsVBox) {
        TextField authorTextField = new TextField();
        authorTextField.setPromptText("Enter author");
        authorTextField.setId("authorTextField");

        TextField coverTypeTextField = new TextField();
        coverTypeTextField.setPromptText("Enter cover type");
        coverTypeTextField.setId("coverTypeTextField");

        TextField publisherTextField = new TextField();
        publisherTextField.setPromptText("Enter publisher");
        publisherTextField.setId("publisherTextField");

        DatePicker publishDatePicker = new DatePicker();
        publishDatePicker.setPromptText("Enter publish date");
        publishDatePicker.setId("publishDatePicker");

        TextField numOfPagesTextField = new TextField();
        numOfPagesTextField.setPromptText("Enter number of pages");
        numOfPagesTextField.setId("numOfPagesTextField");

        TextField languageTextField = new TextField();
        languageTextField.setPromptText("Enter language");
        languageTextField.setId("languageTextField");

        TextField bookCategoryTextField = new TextField();
        bookCategoryTextField.setPromptText("Enter book category");
        bookCategoryTextField.setId("bookCategoryTextField");

        extraFieldsVBox.getChildren().addAll(
                new Label("Author"), authorTextField,
                new Label("Cover Type"), coverTypeTextField,
                new Label("Publisher"), publisherTextField,
                new Label("Publish Date"), publishDatePicker,
                new Label("Number of Pages"), numOfPagesTextField,
                new Label("Language"), languageTextField,
                new Label("Book Category"), bookCategoryTextField
        );
    }

    @Override
    public Map<String, String> addFieldValues(Map<String, String> fieldValues, VBox extraFieldsVBox) {
        fieldValues.put("authorTextField", ((TextField) extraFieldsVBox.lookup("#authorTextField")).getText());
        fieldValues.put("coverTypeTextField", ((TextField) extraFieldsVBox.lookup("#coverTypeTextField")).getText());
        fieldValues.put("publisherTextField", ((TextField) extraFieldsVBox.lookup("#publisherTextField")).getText());
        fieldValues.put("publishDatePicker", ((DatePicker) extraFieldsVBox.lookup("#publishDatePicker")).getValue().toString());

        return fieldValues;
    }
}
