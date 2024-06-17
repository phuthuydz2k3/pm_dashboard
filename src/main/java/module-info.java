module org.example.project_manager_dashboard {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires static lombok;
    requires mysql.connector.java;
    requires java.desktop;
    requires java.persistence;
    requires com.fasterxml.jackson.databind;
    requires org.hibernate.orm.core;
    requires org.controlsfx.controls;

    // Allow reflective access by the javafx.fxml module
    opens org.example.project_manager_dashboard to javafx.fxml;
    opens org.example.project_manager_dashboard.views.screens to javafx.fxml;
    opens org.example.project_manager_dashboard.models to org.hibernate.orm.core;
    opens org.example.project_manager_dashboard.views.items to javafx.fxml;

    // Exporting the specific package to javafx.fxml
    exports org.example.project_manager_dashboard.models;
    exports org.example.project_manager_dashboard.views.screens to javafx.fxml;
    exports org.example.project_manager_dashboard;
    exports org.example.project_manager_dashboard.controllers to javafx.fxml;
    opens org.example.project_manager_dashboard.controllers to javafx.fxml;
    exports org.example.project_manager_dashboard.views.items to javafx.fxml;
}
