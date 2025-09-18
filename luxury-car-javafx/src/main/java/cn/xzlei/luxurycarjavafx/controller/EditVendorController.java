package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.SupplierApi;
import cn.xzlei.luxurycarjavafx.entity.Supplier;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class EditVendorController {
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

    // 初始化操作
    public void initialize() {
    }

    public void updateData(Supplier supplier) {
        vendorNameField.setText(supplier.getSupplierName());
        contactNameField.setText(supplier.getContactName());
        contactPhoneField.setText(supplier.getContactPhone());
        addressField.setText(supplier.getAddress());
        accountField.setText(supplier.getAccount());
        emailField.setText(supplier.getEmail());
    }

    public boolean updateVendor(Supplier supplier) {
        JSONObject res = SupplierApi.updateVendor(Supplier.builder()
                .supplierId(supplier.getSupplierId())
                .supplierName(this.vendorNameField.getText())
                .contactName(this.contactNameField.getText())
                .contactPhone(this.contactPhoneField.getText())
                .address(this.addressField.getText())
                .email(this.emailField.getText())
                .account(this.accountField.getText())
                .build());

        if (res.getInt("code") == 200) {
            // 显示成功信息
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("修改供应商成功");
            alert.setContentText("供应商信息更新成功");
            alert.showAndWait();
            return true;
        } else {
            // 显示错误信息
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("修改供应商失败");
            alert.setContentText(res.getStr("message"));
            alert.showAndWait();
            return false;
        }
    }

}
