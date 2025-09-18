package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.CustomerApi;
import cn.xzlei.luxurycarjavafx.entity.Customer;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class EditClientController {
    @FXML
    public DialogPane dialog;
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

    public boolean updateCustomer(Customer customer) {
        Customer customer1 = Customer.builder()
                .id(customer.getId())
                .customerName(customerNameField.getText())
                .phone(customerPhoneField.getText())
                .email(customerEmailField.getText())
                .address(addressField.getText())
                .gender(genderComboBox.getValue())
                .workplace(workplaceField.getText())
                .nickname(nickNameField.getText())
                .build();
        JSONObject res = CustomerApi.updateCustomer(customer1);
        System.out.println( res);
        if(res.getInt("code") == 200){
            return true;
        }else{
            NetworkExceptionHandler.showError("更新客户信息失败",res.getStr("message"));
            return false;
        }
    }

    public void updateData(Customer customer) {
        customerNameField.setText(customer.getCustomerName());
        customerPhoneField.setText(customer.getPhone());
        customerEmailField.setText(customer.getEmail());
        workplaceField.setText(customer.getWorkplace());
        nickNameField.setText(customer.getNickname());
        genderComboBox.setValue(customer.getGender());
        addressField.setText(customer.getAddress());
    }
}
