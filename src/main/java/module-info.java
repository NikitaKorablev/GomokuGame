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
}