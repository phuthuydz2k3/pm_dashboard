package org.example.project_manager_dashboard.views.screens.productFormStrategies;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Map;

public class DVDFieldsStrategy implements ExtraFieldsStrategy{
    @Override
    public void addExtraFields(VBox extraFieldsVBox) {
        TextField discTypeTextField = new TextField();
        discTypeTextField.setPromptText("Enter disc type");
        discTypeTextField.setId("discTypeTextField");

        TextField directorTextField = new TextField();
        directorTextField.setPromptText("Enter director");
        directorTextField.setId("directorTextField");

        TextField studioTextField = new TextField();
        studioTextField.setPromptText("Enter studio");
        studioTextField.setId("studioTextField");

        DatePicker releasedDatePickerDVD = new DatePicker();
        releasedDatePickerDVD.setPromptText("Enter released date");
        releasedDatePickerDVD.setId("releasedDatePickerDVD");

        TextField subtitleTextField = new TextField();
        subtitleTextField.setPromptText("Enter subtitle");
        subtitleTextField.setId("subtitleTextField");

        TextField runtimeTextField = new TextField();
        runtimeTextField.setPromptText("Enter runtime");
        runtimeTextField.setId("runtimeTextField");

        extraFieldsVBox.getChildren().addAll(
                new Label("Disc Type"), discTypeTextField,
                new Label("Director"), directorTextField,
                new Label("Studio"), studioTextField,
                new Label("Released Date"), releasedDatePickerDVD,
                new Label("Subtitle"), subtitleTextField,
                new Label("Runtime"), runtimeTextField
        );
    }

    @Override
    public Map<String, String> addFieldValues(Map<String, String> fieldValues, VBox extraFieldsVBox) {
        fieldValues.put("discTypeTextField", ((TextField) extraFieldsVBox.lookup("#discTypeTextField")).getText());
        fieldValues.put("directorTextField", ((TextField) extraFieldsVBox.lookup("#directorTextField")).getText());
        fieldValues.put("studioTextField", ((TextField) extraFieldsVBox.lookup("#studioTextField")).getText());
        fieldValues.put("subtitleTextField", ((TextField) extraFieldsVBox.lookup("#subtitleTextField")).getText());
        fieldValues.put("runtimeTextField", ((TextField) extraFieldsVBox.lookup("#runtimeTextField")).getText());
        return fieldValues;
    }
}
