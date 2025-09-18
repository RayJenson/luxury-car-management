package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.EmpApi;
import cn.xzlei.luxurycarjavafx.application.LoginApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;


public class AdminController {
    public Label userNameText;
    @FXML
    private VBox contentArea;
    @FXML
    private ImageView userAvatar;

    @FXML
    private VBox navArea;

    private void loadContent(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(AdminController.class.getResource(fxmlFile));
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearNavButtonClass(){
        for (Node node : navArea.getChildren()) {
            if (node instanceof Button button) {
                button.getStyleClass().setAll("navButton");
            }
        }
    }

    /**
     * 加载供应商视图
     * @param actionEvent
     */
    public void loadVendorView(ActionEvent actionEvent) {
        loadContent("/cn/xzlei/luxurycarjavafx/vendor/contentVendorView.fxml");
        Button source = (Button) actionEvent.getSource();
        // 先清除nav导航的所有的样式
        clearNavButtonClass();
        // 为当前点击的按钮添加样式
        source.getStyleClass().setAll("navButton","action");
    }

    /**
     * 加载客户视图
     * @param actionEvent
     */
    public void loadClientView(ActionEvent actionEvent) {
        loadContent("/cn/xzlei/luxurycarjavafx/client/contentClientView.fxml");
        Button source = (Button) actionEvent.getSource();
        // 先清除nav导航的所有的样式
        clearNavButtonClass();
        // 为当前点击的按钮添加样式
        source.getStyleClass().setAll("navButton","action");
    }

    /**
     * 加载产品视图
     * @param actionEvent
     */
    public void loadProductView(ActionEvent actionEvent) {
        loadContent("/cn/xzlei/luxurycarjavafx/product/contentProductView.fxml");
        Button source = (Button) actionEvent.getSource();
        // 先清除nav导航的所有的样式
        clearNavButtonClass();
        // 为当前点击的按钮添加样式
        source.getStyleClass().setAll("navButton","action");
    }

    /**
     * 加载采购视图
     * @param actionEvent
     */
    public void loadPurchaseView(ActionEvent actionEvent) {
        loadContent("/cn/xzlei/luxurycarjavafx/purchase/contentPurchaseView.fxml");
        Button source = (Button) actionEvent.getSource();
        // 先清除nav导航的所有的样式
        clearNavButtonClass();
        // 为当前点击的按钮添加样式
        source.getStyleClass().setAll("navButton","action");
    }

    /**
     * 加载车辆类型视图
     * @param actionEvent
     */
    public void loadCarTypeView(ActionEvent actionEvent) {
        loadContent("/cn/xzlei/luxurycarjavafx/cartype/contentCarTypeView.fxml");
        Button source = (Button) actionEvent.getSource();
        // 先清除nav导航的所有的样式
        clearNavButtonClass();
        // 为当前点击的按钮添加样式
        source.getStyleClass().setAll("navButton","action");
    }

    /**
     * 加载销售视图
     * @param actionEvent
     */
    public void loadSaleView(ActionEvent actionEvent) {
        loadContent("/cn/xzlei/luxurycarjavafx/sale/contentSaleView.fxml");
        Button source = (Button) actionEvent.getSource();
        // 先清除nav导航的所有的样式
        clearNavButtonClass();
        // 为当前点击的按钮添加样式
        source.getStyleClass().setAll("navButton","action");
    }

    /**
     * 加载员工视图
     * @param actionEvent
     */
    public void loadEmpView(ActionEvent actionEvent) {
        loadContent("/cn/xzlei/luxurycarjavafx/emp/contentEmpView.fxml");
        Button source = (Button) actionEvent.getSource();
        // 先清除nav导航的所有的样式
        clearNavButtonClass();
        // 为当前点击的按钮添加样式
        source.getStyleClass().setAll("navButton","action");
    }

    public void initialize() {
        // 初始化时默认加载供应商视图
        loadContent("/cn/xzlei/luxurycarjavafx/vendor/contentVendorView.fxml");
        this.clearNavButtonClass();
        this.navArea.getChildren().getFirst().getStyleClass().setAll("navButton","action");
        Image image = new Image(getClass().getResourceAsStream("/cn/xzlei/luxurycarjavafx/media/default_user_avatar.png"));
        userAvatar.setImage(image);
        userAvatar.setStyle("-fx-border-radius: 50");
        userAvatar.setClip(new javafx.scene.shape.Circle(userAvatar.getFitWidth() / 2, userAvatar.getFitHeight() / 2, Math.min(userAvatar.getFitWidth(), userAvatar.getFitHeight()) / 2));

        // 获取用户信息
        JSONObject userInfo = EmpApi.getUserInfo();
        if(userInfo.getInt("code") == 200){
            userNameText.setText(userInfo.getJSONObject("data").getStr("name"));
        }

    }


    public void loadInventoryView(ActionEvent actionEvent) {
        loadContent("/cn/xzlei/luxurycarjavafx/inventory/contentInventoryView.fxml");
        Button source = (Button) actionEvent.getSource();
        // 先清除nav导航的所有的样式
        clearNavButtonClass();
        // 为当前点击的按钮添加样式
        source.getStyleClass().setAll("navButton","action");
    }

    public void loadReportView(ActionEvent actionEvent) {
        loadContent("/cn/xzlei/luxurycarjavafx/report/contentReportView.fxml");
        Button source = (Button) actionEvent.getSource();
        // 先清除nav导航的所有的样式
        clearNavButtonClass();
        // 为当前点击的按钮添加样式
        source.getStyleClass().setAll("navButton","action");
    }

    public void logout(ActionEvent actionEvent) {
        // 弹出确认登出对话框
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认登出");
        alert.setContentText("确定要登出吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // 获取主窗口，获取当前窗口的Stage
            Stage primaryStage = (Stage) contentArea.getScene().getWindow();
            primaryStage.close();

            // 弹出登录窗口
            Platform.runLater(() -> {
                try {
                    new LoginApplication().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
