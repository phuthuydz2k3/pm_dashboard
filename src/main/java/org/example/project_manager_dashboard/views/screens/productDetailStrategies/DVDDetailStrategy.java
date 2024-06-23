package org.example.project_manager_dashboard.views.screens.productDetailStrategies;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import org.example.project_manager_dashboard.models.DVD;
import org.example.project_manager_dashboard.models.Product;

public class DVDDetailStrategy implements ProductDetailStrategy {
    @Override
    public VBox loadSpecificDetails(Product product) {
        try {
            DVD dvd = (DVD) product;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dvdDetail.fxml"));
            VBox pane = loader.load();
            ((TextField) pane.lookup("#dvdDiscTypeField")).setText(dvd.getDiscType());
            ((TextField) pane.lookup("#dvdDirectorField")).setText(dvd.getDirector());
            ((TextField) pane.lookup("#dvdStudioField")).setText(dvd.getStudio());
            ((TextField) pane.lookup("#dvdSubtitleField")).setText(dvd.getSubtitle());
            ((TextField) pane.lookup("#dvdRuntimeField")).setText(dvd.getRuntime());
            return pane;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateProductDetails(Product product, VBox detailsPane) {
        DVD dvd = (DVD) product;

        TextField discTypeField = (TextField) detailsPane.lookup("#dvdDiscTypeField");
        discTypeField.setText(dvd.getDiscType());

        TextField directorField = (TextField) detailsPane.lookup("#dvdDirectorField");
        directorField.setText(dvd.getDirector());

        TextField studioField = (TextField) detailsPane.lookup("#dvdStudioField");
        studioField.setText(dvd.getStudio());

        TextField subtitleField = (TextField) detailsPane.lookup("#dvdSubtitleField");
        subtitleField.setText(dvd.getSubtitle());

        TextField runtimeField = (TextField) detailsPane.lookup("#dvdRuntimeField");
        runtimeField.setText(dvd.getRuntime());
    }
}
