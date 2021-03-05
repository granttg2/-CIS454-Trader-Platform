module org.openjfx.Trader_Platform {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;

    opens org.openjfx.Trader_Platform to javafx.fxml;
    exports org.openjfx.Trader_Platform;
}