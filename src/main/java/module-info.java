module com.tms.transportmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires mysql.connector.java;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;

    opens com.tms.transportmanagementsystem to javafx.fxml;
    exports com.tms.transportmanagementsystem;
    exports com.tms.transportmanagementsystem.fxControllers to javafx.fxml;
    opens com.tms.transportmanagementsystem.fxControllers to javafx.fxml;
    opens com.tms.transportmanagementsystem.model to org.hibernate.orm.core;
}