package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.ProductApi;
import cn.xzlei.luxurycarjavafx.api.PurchaseApi;
import cn.xzlei.luxurycarjavafx.api.SupplierApi;
import cn.xzlei.luxurycarjavafx.entity.Product;
import cn.xzlei.luxurycarjavafx.entity.PurchaseOrder;
import cn.xzlei.luxurycarjavafx.entity.Supplier;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class OperationPurchaseController {
    @FXML
    public TextField orderQuantityField;
    @FXML
    public TextArea noteField;
    @FXML
    public ComboBox<Integer> productIdComboBox;
    @FXML
    public ComboBox<String> payMethodComboBox;
    @FXML
    public DatePicker orderDatePicker;
    @FXML
    public TextField timeField;
    @FXML
    public ComboBox<String> isPurchaseComboBox;
    @FXML
    public ComboBox<Integer> supplierIdComboBox;

    public boolean addPurchaseOrder() {
        // 校验订单数量
        String orderQuantity = orderQuantityField.getText();
        if (!orderQuantity.matches("^[1-9]\\d*$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("订单数量格式错误");
            alert.setContentText("订单数量只能为正整数");
            alert.showAndWait();
            return false;
        }
        // 校验订单日期
        String orderDate = orderDatePicker.getValue().toString();
        if (!orderDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("订单日期格式错误");
            alert.setContentText("订单日期格式为年/月/日");
            alert.showAndWait();
            return false;
        }
        // 校验订单时间时分秒
        String time = timeField.getText();
        if (!time.matches("^([0-1]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("订单时间格式错误");
            alert.setContentText("订单时间格式为时分秒");
            alert.showAndWait();
            return false;
        }

        PurchaseOrder order = PurchaseOrder.builder()
                .orderQuantity(Integer.parseInt(orderQuantity))
                .orderDate(LocalDateTime.parse(orderDate + " " + time,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .payMethod(payMethodComboBox.getValue())
                .note(noteField.getText())
                .isPurchase(isPurchaseComboBox.getValue().equals("是"))
                .productId(productIdComboBox.getValue())
                .supplierId(supplierIdComboBox.getValue())
                .createTime(new Date())
                .updateTime(new Date())
                .orderId("")
                .id(0)
                .build();
        JSONObject res = PurchaseApi.addOrder(order);
        if (res.getInt("code") == 200) {
            return true;
        }else{
            System.out.println(res.getStr("message"));
            NetworkExceptionHandler.showError("添加订单失败",res.getStr("message"));
            return false;
        }
    }

    public void initialize() {
     // 请求商品ID
     List<Product> products = ProductApi.queryProduct(null, null, null);
     products.forEach(product -> productIdComboBox.getItems().add(product.getId()));

     // 请求供应商信息
        List<Supplier> suppliers = SupplierApi.getSupplierList(null, "","");
        suppliers.forEach(supplier -> supplierIdComboBox.getItems().add(supplier.getSupplierId()));
    }
}
