package cn.xzlei.luxurycarjavafx.controller;

import cn.xzlei.luxurycarjavafx.api.SupplierApi;
import cn.xzlei.luxurycarjavafx.entity.Supplier;
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

public class VendorController {
    public TextField vendorNumberInput;
    public TextField vendorNameInput;
    public TextField vendorAddressInput;
    public Button queryButton;
    @FXML
    private TableColumn<Supplier, Void> actionColumn;

    @FXML
    private TableView<Supplier> vendorTable;

    public void initialize() {
        // 其他初始化代码...
        addActionButtonsToTable();
        vendorTable.getColumns().clear();
        vendorTable.setPrefHeight(5000);

        TableColumn<Supplier, Integer> id = new TableColumn<>("供应商ID");
        id.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        vendorTable.getColumns().add(id);

        TableColumn<Supplier, String> vendorName = new TableColumn<>("供应商名称");
        vendorName.setCellValueFactory(new PropertyValueFactory<>("supplierName")); // 修复：使用vendorName变量
        vendorTable.getColumns().add(vendorName);

        TableColumn<Supplier, String> contactName = new TableColumn<>("联系人");
        contactName.setCellValueFactory(new PropertyValueFactory<>("contactName")); // 修复：使用contactName变量
        vendorTable.getColumns().add(contactName);

        TableColumn<Supplier, String> contactPhone = new TableColumn<>("联系电话");
        contactPhone.setCellValueFactory(new PropertyValueFactory<>("contactPhone")); // 修复：使用contactPhone变量
        vendorTable.getColumns().add(contactPhone);

        TableColumn<Supplier, String> address = new TableColumn<>("地址");
        address.setCellValueFactory(new PropertyValueFactory<>("address")); // 修复：使用address变量
        vendorTable.getColumns().add(address);

        TableColumn<Supplier, String> account = new TableColumn<>("银行卡账号");
        account.setCellValueFactory(new PropertyValueFactory<>("account")); // 修复：使用account变量
        vendorTable.getColumns().add(account);

        TableColumn<Supplier, String> email = new TableColumn<>("邮箱");
        email.setCellValueFactory(new PropertyValueFactory<>("email")); // 修复：使用email变量
        vendorTable.getColumns().add(email);

//        TableColumn<Supplier, LocalDateTime> createTime = new TableColumn<>("创建时间");
//        createTime.setCellValueFactory(new PropertyValueFactory<>("createTime")); // 修复：使用createTime变量
//        vendorTable.getColumns().add(createTime);
//
//        TableColumn<Supplier, LocalDateTime> updateTime = new TableColumn<>("更新时间");
//        updateTime.setCellValueFactory(new PropertyValueFactory<>("updateTime")); // 修复：使用updateTime变量
//        vendorTable.getColumns().add(updateTime);

        vendorTable.getColumns().add(actionColumn);

        // 请求供应商列表
        List<Supplier> vendors = SupplierApi.getSupplierList(null, "", "");

        vendorTable.setItems(FXCollections.observableArrayList(vendors));


    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<Supplier, Void>, TableCell<Supplier, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Supplier, Void> call(final TableColumn<Supplier, Void> param) {
                        final TableCell<Supplier, Void> cell = new TableCell<Supplier, Void>() {
                            private final Button editButton = new Button("编辑");
                            private final Button deleteButton = new Button("删除");

                            {
                                // 设置按钮样式（可选）
                                editButton.getStyleClass().add("primaryButton");
                                editButton.setGraphic(new FontIcon("fas-edit"));
                                deleteButton.getStyleClass().add("dangerButton");
                                deleteButton.setGraphic(new FontIcon("fas-trash"));

                                // 添加事件处理
                                editButton.setOnAction(event -> {
                                    Supplier vendor = getTableView().getItems().get(getIndex());
                                    handleEditAction(vendor);
                                });

                                deleteButton.setOnAction(event -> {
                                    Supplier vendor = getTableView().getItems().get(getIndex());
                                    handleDeleteAction(vendor);
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    HBox buttonsPane = new HBox(5); // 5为按钮间距
                                    buttonsPane.getChildren().addAll(editButton, deleteButton);
                                    buttonsPane.setAlignment(Pos.CENTER);
                                    setGraphic(buttonsPane);
                                }
                            }
                        };
                        return cell;
                    }
                };

        actionColumn.setCellFactory(cellFactory);
    }

    private void handleEditAction(Supplier vendor) {
        // 实现编辑逻辑
        openEditSupplierDialog(vendor);
    }

    private void handleDeleteAction(Supplier vendor) {
        // 弹出确认删除对话框
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setContentText("确定要删除吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean res = SupplierApi.deleteSupplier(vendor.getSupplierId());
            if (res) {
                querySupplier(null);

                // 使用tooltips 提示删除成功，5秒后自动隐藏
                Tooltip tooltip = new Tooltip("删除成功");
                tooltip.setText("删除成功");
                // 设置背景颜色
                tooltip.setStyle("-fx-background-color: #4CAF50;");
                tooltip.setHideDelay(Duration.millis(3000));
                Tooltip.install(vendorTable, tooltip);
                tooltip.show(vendorTable.getParent().getScene().getWindow());
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
                        Tooltip.uninstall(vendorTable, tooltip);
                    });
                }).start();

            } else {
                Alert errAlert = new Alert(Alert.AlertType.ERROR);
                errAlert.setTitle("删除失败");
                errAlert.setContentText("删除失败");
                errAlert.showAndWait();
            }
        }
    }

    public void querySupplier(ActionEvent actionEvent) {
        Integer id = null;
        if (!vendorNumberInput.getText().isEmpty()) {
            try{
                id = Integer.parseInt(vendorNumberInput.getText());
            } catch (NumberFormatException e) {
                // 弹出错误提示信息
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setContentText("请输入正确的供应商编号");
                alert.showAndWait();
                return;
            }
        }
        List<Supplier> supplierList = SupplierApi.getSupplierList(id, this.vendorNameInput.getText(), this.vendorAddressInput.getText());
        this.vendorTable.setItems(FXCollections.observableArrayList(supplierList));
        //添加操作按钮
        addActionButtonsToTable();
    }

    public void reset(ActionEvent actionEvent) {
        vendorNumberInput.setText("");
        vendorNameInput.setText("");
        vendorAddressInput.setText("");
        querySupplier(actionEvent);
    }

    // 打开添加供应商的对话框
    public void openAddSupplierDialog(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/vendor/addVendorView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("新建供应商");
            ButtonType okButtonType = new ButtonType("新增", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            // 使用自定义按钮处理逻辑
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                AddVendorController controller = loader.getController();
                boolean success = false;
                try {
                    success = controller.addVendor();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (!success) {
                    // 如果添加失败，消耗事件，阻止对话框关闭
                    event.consume();
                }
            });

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButtonType) {
                System.out.println("点击了确定按钮");
                // 刷新供应商列表
                querySupplier(actionEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 打开编辑供应商窗口
    public void openEditSupplierDialog(Supplier supplier) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/vendor/editVendorView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("编辑供应商");
            ButtonType okButtonType = new ButtonType("编辑", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            // 使用自定义按钮处理逻辑
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                EditVendorController controller = loader.getController();
                boolean success = false;
                try {
                    success = controller.updateVendor(supplier);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (!success) {
                    // 如果添加失败，消耗事件，阻止对话框关闭
                    event.consume();
                }
            });

            // 初始化数据
            EditVendorController controller = loader.getController();
            controller.updateData(supplier);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButtonType) {
                System.out.println("点击了确定按钮");
                // 刷新供应商列表
                querySupplier(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
