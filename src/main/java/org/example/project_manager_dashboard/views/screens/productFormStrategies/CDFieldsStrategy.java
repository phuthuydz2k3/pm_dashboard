package org.example.project_manager_dashboard.views.screens.productFormStrategies;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Map;

public class CDFieldsStrategy implements ExtraFieldsStrategy {
    @Override
    public void addExtraFields(VBox extraFieldsVBox) {
        TextField artistTextField = new TextField();
        artistTextField.setPromptText("Enter artist");
        artistTextField.setId("artistTextField");

        DatePicker releasedDatePickerCD = new DatePicker();
        releasedDatePickerCD.setPromptText("Enter released date");
        releasedDatePickerCD.setId("releasedDatePickerCD");

        TextField recordLabelTextField = new TextField();
        recordLabelTextField.setPromptText("Enter record label");
        recordLabelTextField.setId("recordLabelTextFieldCD");

        TextField musicTypeTextField = new TextField();
        musicTypeTextField.setPromptText("Enter music type");
        musicTypeTextField.setId("musicTypeTextField");

        extraFieldsVBox.getChildren().addAll(
                new Label("Artist"), artistTextField,
                new Label("Released Date"), releasedDatePickerCD,
                new Label("Record Label"), recordLabelTextField,
                new Label("Music Type"), musicTypeTextField
        );
    }

    @Override
    public Map<String, String> addFieldValues(Map<String, String> fieldValues, VBox extraFieldsVBox) {
        fieldValues.put("artistTextField", ((TextField) extraFieldsVBox.lookup("#artistTextField")).getText());
        fieldValues.put("releasedDatePickerCD", ((DatePicker) extraFieldsVBox.lookup("#releasedDatePickerCD")).getValue().toString());
        fieldValues.put("recordLabelTextFieldCD", ((TextField) extraFieldsVBox.lookup("#recordLabelTextFieldCD")).getText());
        fieldValues.put("musicTypeTextField", ((TextField) extraFieldsVBox.lookup("#musicTypeTextField")).getText());
        return fieldValues;
    }
}
