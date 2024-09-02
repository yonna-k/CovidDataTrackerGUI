module CovidGUI.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;

    opens org.example to javafx.fxml;
    exports org.example;
}