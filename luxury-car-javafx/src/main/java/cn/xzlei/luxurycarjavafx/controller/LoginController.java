package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.DeptApi;
import cn.xzlei.luxurycarjavafx.api.EmpApi;
import cn.xzlei.luxurycarjavafx.application.AdminApplication;
import cn.xzlei.luxurycarjavafx.application.LogisticsApplication;
import cn.xzlei.luxurycarjavafx.application.SalesApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.prefs.Preferences;

public class LoginController {
    @FXML
    public HBox customTitleBar;
    public ComboBox<String> deptComboBox;
    public Button loginButton;
    public TextField account;
    public PasswordField password;
    @FXML
    private BorderPane loginVBox;

    private double x = 0.00;
    private double y = 0.00;
    private double width = 0.00;
    private double height = 0.00;
    private boolean isMax = false;
    private boolean isRight;// 是否处于右边界调整窗口状态
    private boolean isBottomRight;// 是否处于右下角调整窗口状态
    private boolean isBottom;// 是否处于下边界调整窗口状态
    private double RESIZE_WIDTH = 5.00;
    private double MIN_WIDTH = 400.00;
    private double MIN_HEIGHT = 300.00;
    private double xOffset = 0, yOffset = 0;//自定义dialog移动横纵坐标

    public void initialize(){
        Image img = new Image(getClass().getResource("/cn/xzlei/luxurycarjavafx/bgimg.png").toString()); // 替换为实际路径
        BackgroundImage bImg = new BackgroundImage(
                img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );
        loginVBox.setBackground(new Background(bImg));

        // 获取部门列表
        JSONArray deptList = DeptApi.getDeptList();
        deptList.forEach(dept->{
            JSONObject dept1 = (JSONObject) dept;
            deptComboBox.getItems().add(dept1.getStr("name"));
        });

        //点击登录按钮后执行登录操作
        loginButton.setOnAction(event -> {
            // 获取用户输入的账号和密码
            String name = account.getText();
            String pwd = password.getText();
            // 校验账号和密码格式
            String nameReg = "^[a-zA-Z0-9]{4,16}$";
            if (!name.matches(nameReg)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText("账号格式错误");
                alert.setContentText("账号只能包含字母和数字，长度为4-16个字符");
                alert.showAndWait();
                return;
            }
            String pwd_reg = "^[a-zA-Z0-9_@#]{4,16}$";
            if (!pwd.matches(pwd_reg)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText("密码格式错误");
                alert.setContentText("密码只能包含字母、数字、下划线、@、#，长度为4-16个字符");
                alert.showAndWait();
                return;
            }
            if(deptComboBox.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText("部门不能为空");
                alert.setContentText("请选择部门");
                alert.showAndWait();
                return;
            }

            // 发送登录请求
            //获取部门ID
            AtomicReference<Integer> deptId = new AtomicReference<>();
            deptList.forEach(dept -> {
                JSONObject o = (JSONObject) dept;
                if(o.getStr("name").equals(deptComboBox.getValue())){
                    deptId.set(o.getInt("deptId"));
                }
            });
            JSONObject res = EmpApi.login(name, pwd, deptId.get());
            System.out.println(res);
            if(res.getInt("code") == 200){
                // 登录成功...
                // 如果登录的角色中包含超管或管理员，则跳转到管理界面
                JSONArray roles = res.getJSONObject("data").getJSONArray("roles");
                // 保存用户登录信息
                Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
                preferences.put("username", name);
                preferences.put("token", res.getJSONObject("data").getStr("token"));
                if(roles.contains("admin") || roles.contains("super-admin")){
                    try {
                        new AdminApplication().start(new Stage());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Stage stage = (Stage) loginVBox.getScene().getWindow();
                    stage.close();
                }else if(roles.contains("sale")){
                    try {
                        new SalesApplication().start(new Stage());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Stage stage = (Stage) loginVBox.getScene().getWindow();
                    stage.close();
                }else if(roles.contains("logistics")){
                    try {
                        new LogisticsApplication().start(new Stage());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Stage stage = (Stage) loginVBox.getScene().getWindow();
                    stage.close();
                }
                System.out.println(res);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("登录失败");
                alert.setContentText(res.getStr("message"));
                alert.showAndWait();
            }
        });



        // 获取当前窗口的Stage
        AtomicReference<Stage> primaryStage = new AtomicReference<>();
        customTitleBar.setOnMouseDragged(event -> {
            if(primaryStage.get() == null){
                primaryStage.set((Stage) customTitleBar.getScene().getWindow());
            }
            //根据鼠标的横纵坐标移动dialog位置
            event.consume();
//            primaryStage.setX(event.getScreenX() - primaryStage.getX());
            if (yOffset != 0 ) {
                primaryStage.get().setX(event.getScreenX() - xOffset);
                if (event.getScreenY() - yOffset < 0) {
                    primaryStage.get().setY(0);
                } else {
                    primaryStage.get().setY(event.getScreenY() - yOffset);
                }
            }

            double x = event.getSceneX();
            double y = event.getSceneY();
            // 保存窗口改变后的x、y坐标和宽度、高度，用于预判是否会小于最小宽度、最小高度
            double nextX = primaryStage.get().getX();
            double nextY = primaryStage.get().getY();
            // 最后统一改变窗口的x、y坐标和宽度、高度，可以防止刷新频繁出现的屏闪情况
            primaryStage.get().setX(nextX);
            primaryStage.get().setY(nextY);
        });

        //鼠标点击获取横纵坐标
        customTitleBar.setOnMousePressed(event -> {
            event.consume();
            xOffset = event.getSceneX();
            if (event.getSceneY() > 46) {
                yOffset = 0;
            } else {
                yOffset = event.getSceneY();
            }
        });
    }

    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) loginVBox.getScene().getWindow();
        stage.close();
    }
}
