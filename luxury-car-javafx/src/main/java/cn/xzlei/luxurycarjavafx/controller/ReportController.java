package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.ReportApi;
import cn.xzlei.luxurycarjavafx.entity.PurchaseOrder;
import cn.xzlei.luxurycarjavafx.entity.SalesRecord;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;
import java.util.List;

public class ReportController {
    @FXML
    public TableView<PurchaseOrder> purchaseOrderTable;
    @FXML
    public TableView<SalesRecord> salesRecordTable;
    @FXML
    public Pagination purchaseOrderPagination;
    @FXML
    public Pagination salesRecordPagination;
    private Integer purchaseOrderTotal = 0;
    private Integer salesRecordTotal = 0;
    private Integer pageSize = 3;
    private Integer purchaseOrderPage = 1;
    private Integer salesRecordPage = 1;

    public void initialize() {
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

        TableColumn<SalesRecord, String> payMethod2 = new TableColumn<>("付款方式");
        payMethod2.setCellValueFactory(new PropertyValueFactory<>("payMethod"));
        salesRecordTable.getColumns().add(payMethod2);

        TableColumn<SalesRecord, String> totalAmount = new TableColumn<>("销售金额");
        totalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        salesRecordTable.getColumns().add(totalAmount);

        TableColumn<SalesRecord, String> status = new TableColumn<>("销售状态");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        salesRecordTable.getColumns().add(status);

        TableColumn<SalesRecord, String> address = new TableColumn<>("地址");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        salesRecordTable.getColumns().add(address);

        TableColumn<SalesRecord, String> note2 = new TableColumn<>("备注");
        note2.setCellValueFactory(new PropertyValueFactory<>("note"));
        salesRecordTable.getColumns().add(note2);

        queryPurchaseOrder(null);
        querySalesRecord(null);

        purchaseOrderPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            purchaseOrderPage = newValue.intValue()+1;
            queryPurchaseOrder(null);
        });
        salesRecordPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            salesRecordPage = newValue.intValue()+1;
            querySalesRecord(null);
        });
    }

    private void querySalesRecord(Object o) {
        JSONObject res = ReportApi.querySalesRecord(salesRecordPage, pageSize);
        if(res.getInt("code") == 200){
            salesRecordTotal = res.getJSONObject("data").getInt("total");
            salesRecordPagination.setPageCount(Math.ceilDiv(salesRecordTotal, pageSize));
            salesRecordTable.getItems().clear();
            List<SalesRecord> list = res.getJSONObject("data").getJSONArray("data").toList(SalesRecord.class);
            salesRecordTable.setItems(FXCollections.observableArrayList(list));
        }else{
            NetworkExceptionHandler.showError("查询失败",res.getStr("message"));
        }
    }

    public void queryPurchaseOrder(ActionEvent actionEvent) {
        JSONObject res = ReportApi.queryPurchaseOrder(purchaseOrderPage, pageSize);
        if(res.getInt("code") == 200){
            purchaseOrderTotal = res.getJSONObject("data").getInt("total");
            purchaseOrderPagination.setPageCount(Math.ceilDiv(purchaseOrderTotal, pageSize));
            purchaseOrderTable.getItems().clear();
            List<PurchaseOrder> list = res.getJSONObject("data").getJSONArray("data").toList(PurchaseOrder.class);
            purchaseOrderTable.setItems(FXCollections.observableArrayList(list));
        }else{
            NetworkExceptionHandler.showError("查询失败",res.getStr("message"));
        }
    }

    public void purchaseStats(javafx.event.ActionEvent actionEvent) {
        // 弹出进货入库统计数量框
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("进货入库统计数量");
        alert.setHeaderText("进货入库统计数量");
        alert.setContentText("进货入库统计："+purchaseOrderTotal);
        alert.showAndWait();
    }

    public void salesRecordStats(javafx.event.ActionEvent actionEvent) {
        // 弹出销售出库统计数量框
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("销售出库统计数量");
        alert.setHeaderText("销售出库统计数量");
        alert.setContentText("销售出库统计："+salesRecordTotal);
        alert.showAndWait();
    }
}
