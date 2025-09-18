package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.CarTypeApi;
import cn.xzlei.luxurycarjavafx.api.ProductApi;
import cn.xzlei.luxurycarjavafx.entity.Product;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OperationProductController {
    public TextField productNameField;
    public TextField colorField;
    public TextField priceField;
    public TextField purchasePriceField;
    public TextField originalStockField;
    public TextField minStockField;
    public TextField maxStockField;
    public TextArea descriptionField;
    public ComboBox<String> typeComboBox;
    private Map<String, Integer> typeMap = new HashMap<>();

    public boolean addProduct() {
        try {
            double price = Double.parseDouble(priceField.getText());
            double purchasePrice = Double.parseDouble(purchasePriceField.getText());
            Integer originalStock = Integer.parseInt(originalStockField.getText());
            Integer minStock = Integer.parseInt(minStockField.getText());
            Integer maxStock = Integer.parseInt(maxStockField.getText());

            Product product = Product.builder()
                    .productName(productNameField.getText())
                    .color(colorField.getText())
                    .price(BigDecimal.valueOf(price))
                    .purchasePrice(BigDecimal.valueOf(purchasePrice))
                    .originalQuantity(originalStock)
                    .minQuantity(minStock)
                    .maxQuantity(maxStock)
                    .carDesc(descriptionField.getText())
                    .carId(typeMap.get(typeComboBox.getValue()))
                    .build();

            JSONObject res = ProductApi.addProduct(product);
            if (res.getInt("code") == 200) {
                return true;
            }else{
                System.out.println(res.getStr("message"));
                NetworkExceptionHandler.showError("添加商品信息失败",res.getStr("message"));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            NetworkExceptionHandler.showError("添加商品信息失败",e.getMessage());
        }
        return false;
    }

    public void initialize() {
        CarTypeApi.getCarTypeList().forEach(carType -> {
            String typeName = carType.getTypeName();
            int typeId = carType.getId();
            typeComboBox.getItems().add(typeName);
            typeMap.put(typeName, typeId);
        });
    }

    public void initData(Product product) {
        CarTypeApi.getCarTypeList().forEach(carType -> {
            String typeName = carType.getTypeName();
            int typeId = carType.getId();
            typeComboBox.getItems().add(typeName);
            typeMap.put(typeName, typeId);
        });
        productNameField.setText(product.getProductName());
        colorField.setText(product.getColor());
        priceField.setText(product.getPrice().toString());
        purchasePriceField.setText(product.getPurchasePrice().toString());
        originalStockField.setText(product.getOriginalQuantity().toString());
        minStockField.setText(product.getMinQuantity().toString());
        maxStockField.setText(product.getMaxQuantity().toString());
        descriptionField.setText(product.getCarDesc());
        typeMap.forEach((typeName, typeId) -> {
            if (typeId.equals(product.getCarId())) {
                typeComboBox.setValue(typeName);
            }
        });
    }

    public boolean updateProduct(Integer id) {
        try {
            double price = Double.parseDouble(priceField.getText());
            double purchasePrice = Double.parseDouble(purchasePriceField.getText());
            Integer originalStock = Integer.parseInt(originalStockField.getText());
            Integer minStock = Integer.parseInt(minStockField.getText());
            Integer maxStock = Integer.parseInt(maxStockField.getText());

            Product product = Product.builder()
                    .productName(productNameField.getText())
                    .color(colorField.getText())
                    .price(BigDecimal.valueOf(price))
                    .purchasePrice(BigDecimal.valueOf(purchasePrice))
                    .originalQuantity(originalStock)
                    .minQuantity(minStock)
                    .maxQuantity(maxStock)
                    .carDesc(descriptionField.getText())
                    .carId(typeMap.get(typeComboBox.getValue()))
                    .id(id)
                    .build();

            JSONObject res = ProductApi.updateProduct(product);
            if (res.getInt("code") == 200) {
                return true;
            }else{
                NetworkExceptionHandler.showError("更新商品信息失败",res.getStr("message"));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
