module org.openjfx.Trader_Platform {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx.Trader_Platform to javafx.fxml;
    exports org.openjfx.Trader_Platform;
}