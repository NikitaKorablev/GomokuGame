module com.gomoku.gomokugame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires java.rmi;
    requires io.reactivex.rxjava3;
    requires java.desktop;

    opens com.gomoku.gomokugame to javafx.fxml;
    exports com.gomoku.gomokugame;
    exports com.gomoku.gomokugame.global_objects;
    exports com.gomoku.gomokugame.global_objects.intefaces;
    opens com.gomoku.gomokugame.global_objects.intefaces to javafx.fxml;
    opens com.gomoku.gomokugame.client to javafx.fxml;
    exports com.gomoku.gomokugame.client;
    exports com.gomoku.gomokugame.global_objects.enums;
}