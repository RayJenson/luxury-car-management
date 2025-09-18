package cn.xzlei.luxurycarjavafx.application;

import cn.hutool.jwt.JWTUtil;
import cn.xzlei.luxurycarjavafx.api.CommonApi;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        boolean loginStatus = CommonApi.checkLoginStatus();
        if(loginStatus){
            // 读取token中jwt中存储的role
            System.out.println("token：" + CommonApi.getUserToken());
            String roles = JWTUtil.parseToken(CommonApi.getUserToken()).getPayload().getClaim("role").toString();
            Image icon = new Image(getClass().getResourceAsStream("/cn/xzlei/luxurycarjavafx/logo.png"));
            // 设置窗口图标
            primaryStage.getIcons().add(icon);
            if(roles.contains("admin") || roles.contains("super-admin")){
                try {
                    new AdminApplication().start(new Stage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else if(roles.contains("sale")){
                try {
                    new SalesApplication().start(new Stage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else if(roles.contains("logistics")){

            }
        }else{
            System.out.println("请先登录");
            Platform.runLater(() -> {
                try {
                    new LoginApplication().start(new Stage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }
}
