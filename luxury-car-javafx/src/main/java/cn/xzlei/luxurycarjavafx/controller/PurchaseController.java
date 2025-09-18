package cn.xzlei.luxurycarjavafx.controller;

import cn.xzlei.luxurycarjavafx.api.PurchaseApi;
import cn.xzlei.luxurycarjavafx.entity.PurchaseOrder;
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

public class PurchaseController {
    @FXML
    public TableView<PurchaseOrder> purchaseOrderTable;
    @FXML
    public TableColumn<PurchaseOrder,Void> actionColumn;

    public void queryAll(ActionEvent actionEvent) {
        List<PurchaseOrder> purchaseOrders = PurchaseApi.queryOrder();
        purchaseOrderTable.setItems(FXCollections.observableArrayList(purchaseOrders));
    }

    public void openAddPurchaseOrderDialog(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/purchase/operationPurchaseView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("新增进货信息");
            ButtonType okButtonType = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            // 使用自定义按钮处理逻辑
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                OperationPurchaseController controller = loader.getController();
                boolean success = false;
                success = controller.addPurchaseOrder();
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
        purchaseOrderTable.getColumns().clear();
        purchaseOrderTable.setPrefHeight(5000);

        TableColumn<PurchaseOrder, String> orderId = new TableColumn<>("订货编号");
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        purchaseOrderTable.getColumns().add(orderId);

        TableColumn<PurchaseOrder, String> productId = new TableColumn<>("商品编号");
        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        purchaseOrderTable.getColumns().add(productId);

        TableColumn<PurchaseOrder, String> supplierId = new TableColumn<>("供应商编号");
        supplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        purchaseOrderTable.getColumns().add(supplierId);

        TableColumn<PurchaseOrder, String> orderQuantity = new TableColumn<>("订货数量");
        orderQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        purchaseOrderTable.getColumns().add(orderQuantity);

        TableColumn<PurchaseOrder, String> orderDate = new TableColumn<>("订货日期");
        orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        purchaseOrderTable.getColumns().add(orderDate);

        TableColumn<PurchaseOrder, String> payMethod = new TableColumn<>("付款方式");
        payMethod.setCellValueFactory(new PropertyValueFactory<>("payMethod"));
        purchaseOrderTable.getColumns().add(payMethod);

        TableColumn<PurchaseOrder, String> note = new TableColumn<>("备注");
        note.setCellValueFactory(new PropertyValueFactory<>("note"));
        purchaseOrderTable.getColumns().add(note);

        TableColumn<PurchaseOrder, String> isPurchase = new TableColumn<>("是否进货");
        isPurchase.setCellValueFactory(new PropertyValueFactory<>("isPurchase"));
        purchaseOrderTable.getColumns().add(isPurchase);

        purchaseOrderTable.getColumns().add(actionColumn);

        List<PurchaseOrder> purchaseOrders = PurchaseApi.queryOrder();
        purchaseOrderTable.setItems(FXCollections.observableArrayList(purchaseOrders));
    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<PurchaseOrder, Void>, TableCell<PurchaseOrder, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<PurchaseOrder, Void> call(final TableColumn<PurchaseOrder, Void> param) {
                        final TableCell<PurchaseOrder, Void> cell = new TableCell<>() {
                            private final Button deleteButton = new Button("删除");
                            {
                                // 设置按钮样式（可选）
                                deleteButton.getStyleClass().add("dangerButton");
                                deleteButton.setGraphic(new FontIcon("fas-trash"));
                                deleteButton.setOnAction(event -> {
                                    PurchaseOrder purchaseOrder = getTableView().getItems().get(getIndex());
                                    handleDeleteAction(purchaseOrder);
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    HBox buttonsPane = new HBox(5); // 5为按钮间距
                                    buttonsPane.getChildren().addAll(deleteButton);
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

    private void handleDeleteAction(PurchaseOrder product) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setContentText("确定要删除该订单信息吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean res = PurchaseApi.deleteOrder(product.getId());
            if (res) {
                queryAll(null);
                // 使用tooltips 提示删除成功，5秒后自动隐藏
                Tooltip tooltip = new Tooltip("删除成功");
                tooltip.setText("删除成功");
                // 设置背景颜色
                tooltip.setStyle("-fx-background-color: #4CAF50;");
                tooltip.setHideDelay(Duration.millis(3000));
                Tooltip.install(purchaseOrderTable, tooltip);
                tooltip.show(purchaseOrderTable.getParent().getScene().getWindow());
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
                        Tooltip.uninstall(purchaseOrderTable, tooltip);
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
