package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.DeptApi;
import cn.xzlei.luxurycarjavafx.api.EmpApi;
import cn.xzlei.luxurycarjavafx.dto.EmpQueryParamDTO;
import cn.xzlei.luxurycarjavafx.entity.Emp;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class EmpController {
    public TextField nameInput;
    public TextField empIdInput;
    public ComboBox<String> deptInput;
    private static JSONArray depts;
    public TableView<Emp> empTable;
    @FXML
    private TableColumn<Emp, Void> actionColumn;

    public void initialize(){
        addActionButtonsToTable();
        empTable.getColumns().clear();
        empTable.setPrefHeight(5000);

        TableColumn<Emp, String> empId = new TableColumn<>("员工ID");
        empId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        empTable.getColumns().add(empId);

        TableColumn<Emp, String> name = new TableColumn<>("员工姓名");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        empTable.getColumns().add(name);

        TableColumn<Emp, String> gender = new TableColumn<>("员工性别");
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        empTable.getColumns().add(gender);

        TableColumn<Emp, String> account = new TableColumn<>("账号名");
        account.setCellValueFactory(new PropertyValueFactory<>("account"));
        empTable.getColumns().add(account);

        TableColumn<Emp, String> dept = new TableColumn<>("部门");
        dept.setCellValueFactory(new PropertyValueFactory<>("deptId"));
        empTable.getColumns().add(dept);

        TableColumn<Emp, String> address = new TableColumn<>("员工住址");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        empTable.getColumns().add(address);

        TableColumn<Emp, String> phone = new TableColumn<>("手机号码");
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        empTable.getColumns().add(phone);

        TableColumn<Emp, String> job = new TableColumn<>("员工职位");
        job.setCellValueFactory(new PropertyValueFactory<>("job"));
        empTable.getColumns().add(job);

        TableColumn<Emp, String> salary = new TableColumn<>("员工薪资");
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        empTable.getColumns().add(salary);

        empTable.getColumns().add(actionColumn);

        // 获取部门列表
        depts = DeptApi.getDeptList();
        depts.forEach(d->{
            JSONObject d2 = (JSONObject) d;
            deptInput.getItems().add(d2.getStr("name"));
        });

        queryEmp();
    }

    public void openAddEmployeeDialog(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/emp/operationEmpView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("添加员工");
            ButtonType okButtonType = new ButtonType("添加", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            // 使用自定义按钮处理逻辑
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                OperationEmpController controller = loader.getController();
                boolean success = false;
                success = controller.addEmp();
                if (!success) {
                    // 如果添加失败，消耗事件，阻止对话框关闭
                    event.consume();
                }
            });

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButtonType) {
                queryEmp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<Emp, Void>, TableCell<Emp, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Emp, Void> call(final TableColumn<Emp, Void> param) {
                        final TableCell<Emp, Void> cell = new TableCell<>() {
                            private final Button editButton = new Button("");
                            private final Button deleteButton = new Button("");
                            private final Button resetPwdButton = new Button("重置密码");

                            {
                                // 设置按钮样式（可选）
                                editButton.getStyleClass().add("primaryButton");
                                editButton.setGraphic(new FontIcon("fas-edit"));
                                deleteButton.getStyleClass().add("dangerButton");
                                deleteButton.setGraphic(new FontIcon("fas-trash"));
                                resetPwdButton.getStyleClass().add("warningButton");
                                resetPwdButton.setGraphic(new FontIcon("fas-key"));

                                // 添加事件处理
                                editButton.setOnAction(event -> {
                                    Emp emp = getTableView().getItems().get(getIndex());
                                    handleEditAction(emp);
                                });

                                deleteButton.setOnAction(event -> {
                                    Emp emp = getTableView().getItems().get(getIndex());
                                    handleDeleteAction(emp);
                                });
                                resetPwdButton.setOnAction(event -> {
                                    Emp emp = getTableView().getItems().get(getIndex());
                                    handleResetPwdAction(emp);
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    HBox buttonsPane = new HBox(5); // 5为按钮间距
                                    buttonsPane.getChildren().addAll(editButton, deleteButton,resetPwdButton);
                                    buttonsPane.setAlignment(Pos.CENTER);
                                    setGraphic(buttonsPane);
                                }
                            }
                        };
                        return cell;
                    }
                };

        actionColumn.setCellFactory(cellFactory);
        actionColumn.setPrefWidth(200);
    }

    private void handleResetPwdAction(Emp emp) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("重置密码");
        alert.setContentText("你确认重置该员工的密码吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            JSONObject res = EmpApi.resetPwd(emp.getEmpId());
            if (res.getInt("code") == 200) {
                queryEmp();
                // 使用tooltips 提示删除成功，5秒后自动隐藏
                Tooltip tooltip = new Tooltip("重置密码成功");
                tooltip.setText("重置密码成功");
                // 设置背景颜色
                tooltip.setStyle("-fx-background-color: #4CAF50;");
                tooltip.setHideDelay(Duration.millis(3000));
                Tooltip.install(empTable, tooltip);
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 移除提示节点
                    Platform.runLater(()->{
                        System.out.println("隐藏提示");
                        tooltip.hide();
                        Tooltip.uninstall(empTable, tooltip);
                    });
                }).start();
            }
            else{
                Alert errAlert = new Alert(Alert.AlertType.ERROR);
                errAlert.setTitle("重置员工密码失败");
                errAlert.setContentText(res.getStr("message"));
                errAlert.showAndWait();
            }
        }
    }

    private void handleDeleteAction(Emp emp) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除确认");
        alert.setContentText("确定要删除吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            JSONObject res = EmpApi.deleteEmp(emp.getEmpId());
            if (res.getInt("code") == 200) {
                queryEmp();

                // 使用tooltips 提示删除成功，5秒后自动隐藏
                Tooltip tooltip = new Tooltip("删除成功");
                tooltip.setText("删除成功");
                // 设置背景颜色
                tooltip.setStyle("-fx-background-color: #4CAF50;");
                tooltip.setHideDelay(Duration.millis(3000));
                Tooltip.install(empTable, tooltip);
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 移除提示节点
                    Platform.runLater(()->{
                        System.out.println("隐藏提示");
                        tooltip.hide();
                        Tooltip.uninstall(empTable, tooltip);
                    });
                }).start();
            }
            else{
                Alert errAlert = new Alert(Alert.AlertType.ERROR);
                errAlert.setTitle("删除失败");
                errAlert.setContentText(res.getStr("message"));
                errAlert.showAndWait();
            }
        }
    }

    private void handleEditAction(Emp emp) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/emp/operationEmpView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("更新员工信息");
            ButtonType okButtonType = new ButtonType("更新", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            // 使用自定义按钮处理逻辑
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                OperationEmpController controller = loader.getController();
                boolean success = false;
                try {
                    success = controller.updateEmp(emp);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (!success) {
                    // 如果添加失败，消耗事件，阻止对话框关闭
                    event.consume();
                }
            });

            // 初始化数据
            OperationEmpController controller = loader.getController();
            controller.updateData(emp);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButtonType) {
                queryEmp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryEmp(){
        EmpQueryParamDTO empQueryParamDTO = new EmpQueryParamDTO();
        empQueryParamDTO.setName(nameInput.getText());
        AtomicReference<Integer> deptId = new AtomicReference<>();
        depts.forEach(dept -> {
            JSONObject o = (JSONObject) dept;
            if(o.getStr("name").equals(deptInput.getValue())){
                deptId.set(o.getInt("deptId"));
            }
        });
        empQueryParamDTO.setDeptId(deptId.get());

        List<Emp> res = EmpApi.getEmpList(empQueryParamDTO);
        empTable.getItems().clear();
        empTable.setItems(FXCollections.observableArrayList(res));
    }

    public void reset(ActionEvent actionEvent) {
        nameInput.setText("");
        deptInput.setValue("请选择");
        empIdInput.setText("");
        queryEmp();
    }
}
