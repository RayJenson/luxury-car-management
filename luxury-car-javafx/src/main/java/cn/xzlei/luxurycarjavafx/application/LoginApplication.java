package cn.xzlei.luxurycarjavafx.application;

import atlantafx.base.theme.PrimerDark;
import cn.xzlei.luxurycarjavafx.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/login-view.fxml"));
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(HelloApplication.class.getResource("login.css").toString());
        Image icon = new Image(getClass().getResourceAsStream("/cn/xzlei/luxurycarjavafx/logo.png"));
        // 设置窗口图标
        stage.getIcons().add(icon);
        stage.setTitle("豪车管理系统v1.0 - 员工登录");
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }
}
