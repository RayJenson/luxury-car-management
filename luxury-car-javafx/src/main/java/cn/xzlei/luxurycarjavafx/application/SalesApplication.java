package cn.xzlei.luxurycarjavafx.application;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import cn.xzlei.luxurycarjavafx.HelloApplication;
import cn.xzlei.luxurycarjavafx.api.CommonApi;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class SalesApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/product/contentProductView.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("/cn/xzlei/luxurycarjavafx/logo.png"));
        // 设置窗口图标
        stage.getIcons().add(icon);
        if(CommonApi.isDarkMode()){
            Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        }else{
            Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        }
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(HelloApplication.class.getResource("base.css").toString());
        stage.setTitle("豪车管理系统v1.0 - 销售管理[当前权限：sale]");
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setResizable(true);
        stage.setMaximized( true);
        stage.show();
    }
}
