package org.example.project_manager_dashboard.views.screens.productFormStrategies;

import javafx.scene.layout.VBox;

import java.util.Map;

public interface ExtraFieldsStrategy {
    void addExtraFields(VBox extraFieldsVBox);
    Map<String, String> addFieldValues(Map<String, String> fieldValues, VBox extraFieldsVBox);
}
