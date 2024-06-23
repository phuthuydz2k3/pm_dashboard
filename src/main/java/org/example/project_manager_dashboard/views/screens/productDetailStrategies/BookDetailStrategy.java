package org.example.project_manager_dashboard.views.screens.productDetailStrategies;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import org.example.project_manager_dashboard.models.Book;
import org.example.project_manager_dashboard.models.Product;

public class BookDetailStrategy implements ProductDetailStrategy {
    @Override
    public VBox loadSpecificDetails(Product product) {
        try {
            Book book = (Book) product;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/bookDetail.fxml"));
            VBox pane = loader.load();
            ((TextField) pane.lookup("#bookAuthorField")).setText(book.getAuthor());
            ((TextField) pane.lookup("#bookCoverTypeField")).setText(book.getCoverType());
            ((TextField) pane.lookup("#bookPublisherField")).setText(book.getPublisher());
            ((TextField) pane.lookup("#bookPublishDateField")).setText(book.getPublishDate().toString());
            return pane;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateProductDetails(Product product, VBox detailsPane) {
        Book book = (Book) product;

        TextField authorField = (TextField) detailsPane.lookup("#bookAuthorField");
        authorField.setText(book.getAuthor());

        TextField coverTypeField = (TextField) detailsPane.lookup("#bookCoverTypeField");
        coverTypeField.setText(book.getCoverType());

        TextField publisherField = (TextField) detailsPane.lookup("#bookPublisherField");
        publisherField.setText(book.getPublisher());

        TextField publishDateField = (TextField) detailsPane.lookup("#bookPublishDateField");
        publishDateField.setText(book.getPublishDate().toString());
    }
}
