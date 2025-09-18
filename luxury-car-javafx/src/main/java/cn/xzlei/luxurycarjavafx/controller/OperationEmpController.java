package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.DeptApi;
import cn.xzlei.luxurycarjavafx.api.EmpApi;
import cn.xzlei.luxurycarjavafx.entity.Emp;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.concurrent.atomic.AtomicReference;

public class OperationEmpController {
    private static JSONArray depts;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private TextField accountField;
    @FXML
    private ComboBox<String> deptComboBox;
    @FXML
    private TextField jobField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField addressField;

    public boolean addEmp() {
        if (nameField.getText().isEmpty() || genderComboBox.getValue().isEmpty() || accountField.getText().isEmpty() || deptComboBox.getValue().isEmpty() || jobField.getText().isEmpty() || phoneField.getText().isEmpty() || salaryField.getText().isEmpty() || addressField.getText().isEmpty()) {
            return false;
        }
        try {
            AtomicReference<Integer> deptId = new AtomicReference<>();
            depts.forEach(dept -> {
                JSONObject o = (JSONObject) dept;
                if (o.getStr("name").equals(deptComboBox.getValue())) {
                    deptId.set(o.getInt("deptId"));
                }
            });
            JSONObject res = EmpApi.addEmp(Emp.builder()
                    .name(nameField.getText())
                    .gender(genderComboBox.getValue())
                    .account(accountField.getText())
                    .deptId(deptId.get())
                    .job(jobField.getText())
                    .address(addressField.getText())
                    .salary(Double.parseDouble(salaryField.getText()))
                    .phone(phoneField.getText()).build()
            );
            if(res == null){
                NetworkExceptionHandler.showError("添加员工失败","请完善表单信息");
                return false;
            }
            if (res.getInt("code") == 200) {
                return true;
            } else {
                NetworkExceptionHandler.showError("添加员工失败", res.getStr("message"));
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        // 获取部门列表
        depts = DeptApi.getDeptList();
        depts.forEach(d -> {
            JSONObject d2 = (JSONObject) d;
            deptComboBox.getItems().add(d2.getStr("name"));
        });

    }

    public boolean updateEmp(Emp emp) {
        if (nameField.getText().isEmpty() || genderComboBox.getValue().isEmpty() || accountField.getText().isEmpty() || deptComboBox.getValue().isEmpty() || jobField.getText().isEmpty() || phoneField.getText().isEmpty() || salaryField.getText().isEmpty() || addressField.getText().isEmpty()) {
            return false;
        }
        try {
            AtomicReference<Integer> deptId = new AtomicReference<>();
            depts.forEach(dept -> {
                JSONObject o = (JSONObject) dept;
                if (o.getStr("name").equals(deptComboBox.getValue())) {
                    deptId.set(o.getInt("deptId"));
                }
            });
            JSONObject res = EmpApi.updateEmp(Emp.builder()
                    .empId(emp.getEmpId())
                    .name(nameField.getText())
                    .gender(genderComboBox.getValue())
                    .account(accountField.getText())
                    .deptId(deptId.get())
                    .job(jobField.getText())
                    .address(addressField.getText())
                    .salary(Double.parseDouble(salaryField.getText()))
                    .phone(phoneField.getText())
                    .build()
            );
            if (res.getInt("code") == 200) {
                return true;
            } else {
                NetworkExceptionHandler.showError("更新员工信息失败", res.getStr("message"));
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateData(Emp emp) {
        nameField.setText(emp.getName());
        genderComboBox.setValue(emp.getGender());
        accountField.setText(emp.getAccount());
        depts.forEach(dept -> {
            JSONObject d2 = (JSONObject) dept;
            if (d2.getInt("deptId").equals(emp.getDeptId())) {
                deptComboBox.setValue(d2.getStr("name"));
            }
        });
        jobField.setText(emp.getJob());
        phoneField.setText(emp.getPhone());
        salaryField.setText(emp.getSalary().toString());
        addressField.setText(emp.getAddress());
    }
}
