package cn.xzlei.luxurycarjavafx.application;
// 添加依赖后使用

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.HelloApplication;
import cn.xzlei.luxurycarjavafx.api.CommonApi;
import cn.xzlei.luxurycarjavafx.api.EmpApi;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/admin-view.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("/cn/xzlei/luxurycarjavafx/logo.png"));
        // 设置窗口图标
        stage.getIcons().add(icon);
        if(CommonApi.isDarkMode()){
            Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        }else{
            Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        }
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        scene.getStylesheets().add(HelloApplication.class.getResource("base.css").toString());
        JSONObject userInfo = EmpApi.getUserInfo();
        stage.setTitle("豪车管理系统v1.0 - 安院极客组");
        if(userInfo.getInt("code") == 200){
            stage.setTitle("豪车管理系统v1.0 - 当前权限:[" + userInfo.getJSONObject("data").getJSONArray("roles").get(0)+"]");
        }
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(400);

        // 最大化窗口
        stage.setMaximized(true);
        stage.show();
    }
}
