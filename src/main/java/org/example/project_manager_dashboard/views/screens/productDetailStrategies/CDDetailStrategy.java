package org.example.project_manager_dashboard.views.screens.productDetailStrategies;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import org.example.project_manager_dashboard.models.CD;
import org.example.project_manager_dashboard.models.Product;

public class CDDetailStrategy implements ProductDetailStrategy {
    @Override
    public VBox loadSpecificDetails(Product product) {
        try {
            CD cd = (CD) product;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cdDetail.fxml"));
            VBox pane = loader.load();
            ((TextField) pane.lookup("#cdArtistField")).setText(cd.getArtist());
            ((TextField) pane.lookup("#cdReleasedDateField")).setText(cd.getReleasedDate().toString());
            ((TextField) pane.lookup("#cdRecordLabelField")).setText(cd.getRecordLabel());
            ((TextField) pane.lookup("#cdMusicTypeField")).setText(cd.getMusicType());
            return pane;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateProductDetails(Product product, VBox detailsPane) {
        CD cd = (CD) product;

        TextField artistField = (TextField) detailsPane.lookup("#cdArtistField");
        artistField.setText(cd.getArtist());

        TextField releasedDateField = (TextField) detailsPane.lookup("#cdReleasedDateField");
        releasedDateField.setText(cd.getReleasedDate().toString());

        TextField recordLabelField = (TextField) detailsPane.lookup("#cdRecordLabelField");
        recordLabelField.setText(cd.getRecordLabel());

        TextField musicTypeField = (TextField) detailsPane.lookup("#cdMusicTypeField");
        musicTypeField.setText(cd.getMusicType());
    }
}
