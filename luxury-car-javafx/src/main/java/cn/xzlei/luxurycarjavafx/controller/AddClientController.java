package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.CustomerApi;
import cn.xzlei.luxurycarjavafx.entity.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddClientController {
    @FXML
    public TextField customerNameField;
    @FXML
    public TextField customerPhoneField;
    @FXML
    public TextField customerEmailField;
    @FXML
    public TextField workplaceField;
    @FXML
    public TextField nickNameField;
    @FXML
    public ComboBox<String> genderComboBox;
    @FXML
    public TextField addressField;

    public boolean addCustomer() {
        Customer customer = Customer.builder()
                .customerName(customerNameField.getText())
                .phone(customerPhoneField.getText())
                .email(customerEmailField.getText())
                .gender(genderComboBox.getValue())
                .workplace(workplaceField.getText())
                .nickname(nickNameField.getText())
                .address(addressField.getText())
                .build();

        JSONObject res = CustomerApi.addCustomer(customer);
        if(res.getInt("code") == 200){
            // 显示成功信息
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("添加客户成功");
            alert.setContentText("客户添加成功");
            alert.showAndWait();
            return true;
        }else{
            // 显示错误信息
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("添加客户失败");
            alert.setContentText(res.getStr("message"));
            alert.showAndWait();
            return false;
        }
    }
}
