package org.example.project_manager_dashboard.views.screens.productDetailStrategies;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import org.example.project_manager_dashboard.models.LP;
import org.example.project_manager_dashboard.models.Product;

public class LPDetailStrategy implements ProductDetailStrategy {
    @Override
    public VBox loadSpecificDetails(Product product) {
        try {
            LP lp = (LP) product;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/lpDetail.fxml"));
            VBox pane = loader.load();
            ((TextField) pane.lookup("#lpArtistField")).setText(lp.getArtist());
            ((TextField) pane.lookup("#lpReleasedDateField")).setText(lp.getReleasedDate().toString());
            ((TextField) pane.lookup("#lpRecordLabelField")).setText(lp.getRecordLabel());
            ((TextField) pane.lookup("#lpMusicTypeField")).setText(lp.getMusicType());
            return pane;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateProductDetails(Product product, VBox detailsPane) {
        LP lp = (LP) product;

        TextField artistField = (TextField) detailsPane.lookup("#lpArtistField");
        artistField.setText(lp.getArtist());

        TextField releasedDateField = (TextField) detailsPane.lookup("#lpReleasedDateField");
        releasedDateField.setText(lp.getReleasedDate().toString());

        TextField recordLabelField = (TextField) detailsPane.lookup("#lpRecordLabelField");
        recordLabelField.setText(lp.getRecordLabel());

        TextField musicTypeField = (TextField) detailsPane.lookup("#lpMusicTypeField");
        musicTypeField.setText(lp.getMusicType());
    }
}
