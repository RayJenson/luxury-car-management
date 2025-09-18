package cn.xzlei.luxurycarjavafx.controller;

import cn.xzlei.luxurycarjavafx.api.SalesRecordApi;
import cn.xzlei.luxurycarjavafx.entity.SalesRecord;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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

public class SalesRecordController {
    public TableView<SalesRecord> salesRecordTable;
    public TableColumn<SalesRecord, Void> actionColumn;

    public void queryAll(ActionEvent actionEvent) {
        List<SalesRecord> salesRecords = SalesRecordApi.querySalesRecord();
        salesRecordTable.setItems(FXCollections.observableArrayList(salesRecords));
    }

    public void openAddSalesRecordDialog(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/sale/operationSaleView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("新增销售订单信息");
            ButtonType okButtonType = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            // 使用自定义按钮处理逻辑
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                OperationSalesRecordController controller = loader.getController();
                boolean success = false;
                success = controller.addSalesRecord();
                if (!success) {
                    // 如果添加失败，消耗事件，阻止对话框关闭
                    event.consume();
                }
            });

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButtonType) {
                queryAll(actionEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        addActionButtonsToTable();
        salesRecordTable.getColumns().clear();
        salesRecordTable.setPrefHeight(5000);

        TableColumn<SalesRecord, Integer> id = new TableColumn<>("销售编号");
        id.setCellValueFactory(new PropertyValueFactory<>("salesId"));
        salesRecordTable.getColumns().add(id);

        TableColumn<SalesRecord, String> customerName = new TableColumn<>("客户编号");
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        salesRecordTable.getColumns().add(customerName);

        TableColumn<SalesRecord, String> employeeName = new TableColumn<>("员工编号");
        employeeName.setCellValueFactory(new PropertyValueFactory<>("empId"));
        salesRecordTable.getColumns().add(employeeName);

        TableColumn<SalesRecord, String> saleDate = new TableColumn<>("销售日期");
        saleDate.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        salesRecordTable.getColumns().add(saleDate);

        TableColumn<SalesRecord, String> payMethod = new TableColumn<>("付款方式");
        payMethod.setCellValueFactory(new PropertyValueFactory<>("payMethod"));
        salesRecordTable.getColumns().add(payMethod);

        TableColumn<SalesRecord, String> totalAmount = new TableColumn<>("销售金额");
        totalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        salesRecordTable.getColumns().add(totalAmount);

        TableColumn<SalesRecord, String> status = new TableColumn<>("销售状态");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        salesRecordTable.getColumns().add(status);

        TableColumn<SalesRecord, String> address = new TableColumn<>("地址");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        salesRecordTable.getColumns().add(address);

        TableColumn<SalesRecord, String> note = new TableColumn<>("备注");
        note.setCellValueFactory(new PropertyValueFactory<>("note"));
        salesRecordTable.getColumns().add(note);

        salesRecordTable.getColumns().add(actionColumn);

        List<SalesRecord> salesRecords = SalesRecordApi.querySalesRecord();
        salesRecordTable.setItems(FXCollections.observableArrayList(salesRecords));
    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<SalesRecord, Void>, TableCell<SalesRecord, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<SalesRecord, Void> call(final TableColumn<SalesRecord, Void> param) {
                        final TableCell<SalesRecord, Void> cell = new TableCell<>() {
                            private final Button deleteButton = new Button("删除");
                            private final Button editButton = new Button("编辑");
                            {
                                // 设置按钮样式（可选）
                                deleteButton.getStyleClass().add("dangerButton");
                                deleteButton.setGraphic(new FontIcon("fas-trash"));
                                editButton.getStyleClass().add("primaryButton");
                                editButton.setGraphic(new FontIcon("fas-edit"));


                                deleteButton.setOnAction(event -> {
                                    SalesRecord salesRecord = getTableView().getItems().get(getIndex());
                                    handleDeleteAction(salesRecord);
                                });
                                editButton.setOnAction(event -> {
                                    SalesRecord salesRecord = getTableView().getItems().get(getIndex());
                                    handleEditAction(salesRecord);
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    HBox buttonsPane = new HBox(5); // 5为按钮间距
                                    buttonsPane.getChildren().addAll(editButton,deleteButton);
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

    private void handleEditAction(SalesRecord salesRecord) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/sale/operationSaleView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("编辑销售订单信息");
            ButtonType okButtonType = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            // 使用自定义按钮处理逻辑
            OperationSalesRecordController controller = loader.getController();
            controller.initData(salesRecord);
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                boolean success = false;
                success = controller.updateSalesRecord();
                if (!success) {
                    // 如果添加失败，消耗事件，阻止对话框关闭
                    event.consume();
                }
            });

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButtonType) {
                queryAll(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteAction(SalesRecord salesRecord) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setContentText("确定要删除该销售信息吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean res = SalesRecordApi.deleteSalesRecord(salesRecord.getSalesId());
            if (res) {
                queryAll(null);
                // 使用tooltips 提示删除成功，5秒后自动隐藏
                Tooltip tooltip = new Tooltip("删除成功");
                tooltip.setText("删除成功");
                // 设置背景颜色
                tooltip.setStyle("-fx-background-color: #4CAF50;");
                tooltip.setHideDelay(Duration.millis(3000));
                Tooltip.install(salesRecordTable, tooltip);
                tooltip.show(salesRecordTable.getParent().getScene().getWindow());
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 移除提示节点
                    Platform.runLater(() -> {
                        System.out.println("隐藏提示");
                        tooltip.hide();
                        Tooltip.uninstall(salesRecordTable, tooltip);
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
}
