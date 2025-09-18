package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.CustomerApi;
import cn.xzlei.luxurycarjavafx.api.EmpApi;
import cn.xzlei.luxurycarjavafx.api.SalesRecordApi;
import cn.xzlei.luxurycarjavafx.dto.EmpQueryParamDTO;
import cn.xzlei.luxurycarjavafx.entity.Customer;
import cn.xzlei.luxurycarjavafx.entity.Emp;
import cn.xzlei.luxurycarjavafx.entity.SalesRecord;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OperationSalesRecordController {
    @FXML
    private ComboBox<String> customerIdComboBox;

    @FXML
    private ComboBox<String> empIdComboBox;

    @FXML
    private ComboBox<String> payMethodComboBox;

    @FXML
    private DatePicker saleDatePicker;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TextField totalAmountField;

    @FXML
    private TextField addressField;

    @FXML
    private TextArea noteField;

    private SalesRecord salesRecord;

    public boolean addSalesRecord() {
        // 校验每个下拉框是否都有值
        if (customerIdComboBox.getValue() == null || empIdComboBox.getValue() == null || payMethodComboBox.getValue() == null || statusComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("请完善表单中的下拉框数据！");
            alert.showAndWait();
            return false;
        }
        String payMethod = payMethodComboBox.getValue();
        String money = totalAmountField.getText();
        if (money != null && !money.isEmpty()) {
            try {
                Double.parseDouble(money);
                LocalDate date = saleDatePicker.getValue();

                JSONObject res = SalesRecordApi.addSalesRecord(
                        SalesRecord.builder()
                                .customerId(Integer.parseInt(customerIdComboBox.getValue()))
                                .empId(Integer.parseInt(empIdComboBox.getValue()))
                                .saleDate(date.atStartOfDay())
                                .totalAmount(BigDecimal.valueOf(Double.parseDouble(money)))
                                .payMethod(payMethod)
                                .status(statusComboBox.getValue())
                                .address(addressField.getText())
                                .note(noteField.getText())
                                .build()
                );

                if (res.getInt("code") == 200) {
                    return true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误");
                    alert.setHeaderText(res.getStr("message"));
                    alert.showAndWait();
                    return false;
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText("金额格式错误！");
                alert.showAndWait();
                return false;
            }
        }
        return false;
    }

    public void initialize() {
        // 获取客户 ID
        List<Customer> customers = CustomerApi.getCustomerList(null, null, null);
        customers.forEach(customer -> customerIdComboBox.getItems().add(customer.getId().toString()));

        // 获取员工 ID
        EmpQueryParamDTO pa = new EmpQueryParamDTO();
        pa.setDeptId(null);
        pa.setRole("");
        pa.setName("");
        pa.setGender("");
        List<Emp> employees = EmpApi.getEmpList(pa);
        employees.forEach(employee -> empIdComboBox.getItems().add(employee.getEmpId().toString()));
    }

    public void initData(SalesRecord salesRecord) {
        customerIdComboBox.setValue(salesRecord.getCustomerId().toString());
        empIdComboBox.setValue(salesRecord.getEmpId().toString());
        payMethodComboBox.setValue(salesRecord.getPayMethod());
        saleDatePicker.setValue(salesRecord.getSaleDate().toLocalDate());
        totalAmountField.setText(salesRecord.getTotalAmount().toString());
        statusComboBox.setValue(salesRecord.getStatus());
        addressField.setText(salesRecord.getAddress());
        noteField.setText(salesRecord.getNote());
        this.salesRecord = salesRecord;
    }

    public boolean updateSalesRecord() {
        if (customerIdComboBox.getValue() == null || empIdComboBox.getValue() == null || payMethodComboBox.getValue() == null || statusComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("请完善表单中的下拉框数据！");
            alert.showAndWait();
            return false;
        }
        String payMethod = payMethodComboBox.getValue();
        String money = totalAmountField.getText();
        if (money != null && !money.isEmpty()) {
            try {
                JSONObject res = SalesRecordApi.updateSalesRecord(
                        SalesRecord.builder()
                                .salesId(this.salesRecord.getSalesId())
                                .customerId(Integer.parseInt(customerIdComboBox.getValue()))
                                .empId(Integer.parseInt(empIdComboBox.getValue()))
                                .saleDate(saleDatePicker.getValue().atStartOfDay())
                                .totalAmount(BigDecimal.valueOf(Double.parseDouble(money)))
                                .status(statusComboBox.getValue())
                                .address(addressField.getText())
                                .note(noteField.getText())
                                .payMethod(payMethod).build()
                );

                if (res.getInt("code") == 200) {
                    return true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误");
                    alert.setHeaderText(res.getStr("message"));
                    alert.showAndWait();
                    return false;
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText("金额格式错误！");
                alert.showAndWait();
                return false;
            }
        }
        return false;
    }
}
