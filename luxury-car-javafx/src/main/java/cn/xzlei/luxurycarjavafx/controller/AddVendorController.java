package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.SupplierApi;
import cn.xzlei.luxurycarjavafx.entity.Supplier;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class AddVendorController {
    @FXML
    public DialogPane dialog;
    @FXML
    public TextField vendorNameField;
    @FXML
    public TextField contactNameField;
    @FXML
    public TextField contactPhoneField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField accountField;
    @FXML
    public TextField emailField;

    public boolean addVendor() throws InterruptedException {
        JSONObject res = SupplierApi.addSupplier(Supplier.builder()
                .supplierName(vendorNameField.getText())
                .contactName(contactNameField.getText())
                .contactPhone(contactPhoneField.getText())
                .address(addressField.getText())
                .account(accountField.getText())
                .email(emailField.getText())
                .build());

        if (res.getInt("code") == 200) {
            return true;
        }else{
            // 显示错误信息
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("添加供应商失败");
            alert.setContentText(res.getStr("message"));
            alert.showAndWait();
            return false;
        }
    }

}
