module org.openjfx.Trader_Platform {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires json;

    opens org.jfx.Trader_Platform to javafx.fxml;
    exports org.jfx.Trader_Platform;
}