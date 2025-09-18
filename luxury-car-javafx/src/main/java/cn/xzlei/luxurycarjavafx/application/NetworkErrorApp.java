package cn.xzlei.luxurycarjavafx.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class NetworkErrorApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private boolean isNetworkException(Throwable throwable) {
        return throwable instanceof java.net.SocketException ||
                throwable instanceof java.net.UnknownHostException ||
                throwable instanceof java.net.SocketTimeoutException;
    }

    private void handleNetworkError(Throwable throwable) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("网络连接错误");
            alert.setHeaderText("网络连接失败");
            alert.setContentText("无法连接到服务器，请检查网络连接。");
            alert.showAndWait();
        });
    }
    @Override
    public void start(Stage primaryStage) {
// 设置全局未捕获异常处理器
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            if (isNetworkException(throwable)) {
                handleNetworkError(throwable);
            }
        });

        // JavaFX Application Thread异常处理
        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
            if (isNetworkException(throwable)) {
                handleNetworkError(throwable);
            }
        });
    }
}
