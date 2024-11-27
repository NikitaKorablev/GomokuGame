module com.gomoku.gomoku_game_rmi {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires java.rmi;
    requires io.reactivex.rxjava3;
    requires java.desktop;

    // Base Gomoku Game
    opens com.gomoku.gomoku_game_rmi to javafx.fxml;
    exports com.gomoku.gomoku_game_rmi;
    exports com.gomoku.gomoku_game_rmi.global_objects;
    exports com.gomoku.gomoku_game_rmi.global_objects.intefaces;
    opens com.gomoku.gomoku_game_rmi.global_objects.intefaces to javafx.fxml;
    opens com.gomoku.gomoku_game_rmi.client to javafx.fxml;
    exports com.gomoku.gomoku_game_rmi.client;
    exports com.gomoku.gomoku_game_rmi.global_objects.enums;

    // Gomoku Game on RMI
    opens com.gomoku.gomoku_game_grpc to javafx.fxml;
    exports com.gomoku.gomoku_game_grpc;
    exports com.gomoku.gomoku_game_grpc.global_objects;
    exports com.gomoku.gomoku_game_grpc.global_objects.intefaces;
    opens com.gomoku.gomoku_game_grpc.global_objects.intefaces to javafx.fxml;
    opens com.gomoku.gomoku_game_grpc.client to javafx.fxml;
    exports com.gomoku.gomoku_game_grpc.client;
    exports com.gomoku.gomoku_game_grpc.global_objects.enums;
}