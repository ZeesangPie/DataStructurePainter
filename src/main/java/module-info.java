module com.paint.paint {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    requires javafx.graphicsEmpty;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
//    requires jfxrt;
//    requires rt;

    opens com.paint.paint to javafx.fxml;
    exports com.paint.paint;
    opens com.paint.paint.Item to javafx.fxml;
    exports com.paint.paint.Item;
}