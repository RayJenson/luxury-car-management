package cn.xzlei.luxurycarjavafx.controller;

import cn.xzlei.luxurycarjavafx.api.CarTypeApi;
import cn.xzlei.luxurycarjavafx.api.ProductApi;
import cn.xzlei.luxurycarjavafx.entity.Product;
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

import java.util.*;

public class ProductController {
    @FXML
    public TextField productNumberInput;
    @FXML
    public TextField productNameInput;
    @FXML
    public Button queryButton;
    @FXML
    public Button resetButton;
    @FXML
    public TableView<Product> productTable;
    @FXML
    public TableColumn<Product, Void> actionColumn;
    @FXML
    public ComboBox<String> typeComboBox;
    private final Map<String, Integer> typeMap = new HashMap<>(); // 存储名称到ID的映射

    public void queryProduct(ActionEvent actionEvent) {
        try {
            Integer typeId = typeMap.get(typeComboBox.getValue());
            List<Product> products = ProductApi.queryProduct(
                    Objects.equals(productNumberInput.getText(), "") ? null : Integer.parseInt(productNumberInput.getText())
                    , Objects.equals(productNameInput.getText(), "") ? null : productNameInput.getText()
                    , typeId);
            productTable.setItems(FXCollections.observableArrayList(products));
            addActionButtonsToTable();

            CarTypeApi.getCarTypeList().forEach(carType -> {
                String typeName = carType.getTypeName();
                int typeId2 = carType.getId();
                typeComboBox.getItems().add(typeName);
                typeMap.put(typeName, typeId2);
            });
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setContentText("请检查参数是否填写正确");
            alert.showAndWait();
        }
    }

    public void reset(ActionEvent actionEvent) {
        productNumberInput.setText("");
        productNameInput.setText("");
        typeComboBox.setValue("请选择商品品牌");
        queryProduct(actionEvent);
        addActionButtonsToTable();
    }

    public void openAddProductDialog(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/product/addProductView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("新增产品信息");
            ButtonType okButtonType = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            // 使用自定义按钮处理逻辑
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                OperationProductController controller = loader.getController();
                boolean success = false;
                success = controller.addProduct();
                if (!success) {
                    // 如果添加失败，消耗事件，阻止对话框关闭
                    event.consume();
                }
            });

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButtonType) {
                System.out.println("点击了确定按钮");
                // 刷新供应商列表
                queryProduct(actionEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        CarTypeApi.getCarTypeList().forEach(carType -> {
            String typeName = carType.getTypeName();
            int typeId = carType.getId();
            typeComboBox.getItems().add(typeName);
            typeMap.put(typeName, typeId);
        });


        addActionButtonsToTable();
        productTable.getColumns().clear();


        TableColumn<Product, String> productNumber = new TableColumn<>("产品编号");
        productNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
        productTable.getColumns().add(productNumber);

        TableColumn<Product, String> productName = new TableColumn<>("产品名称");
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productTable.getColumns().add(productName);

        TableColumn<Product, String> typeName = new TableColumn<>("产品类型");
        typeName.setCellValueFactory(new PropertyValueFactory<>("carId"));
        productTable.getColumns().add(typeName);

        TableColumn<Product, String> color = new TableColumn<>("颜色");
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        productTable.getColumns().add(color);

        TableColumn<Product, String> price = new TableColumn<>("价格");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.getColumns().add(price);

        TableColumn<Product, String> purchasePrice = new TableColumn<>("采购价格");
        purchasePrice.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        productTable.getColumns().add(purchasePrice);

        TableColumn<Product, String> originalQuantity = new TableColumn<>("原始库存");
        originalQuantity.setCellValueFactory(new PropertyValueFactory<>("originalQuantity"));
        productTable.getColumns().add(originalQuantity);


        TableColumn<Product, String> minQuantity = new TableColumn<>("最小库存");
        minQuantity.setCellValueFactory(new PropertyValueFactory<>("minQuantity"));
        productTable.getColumns().add(minQuantity);

        TableColumn<Product, String> maxQuantity = new TableColumn<>("最大库存");
        maxQuantity.setCellValueFactory(new PropertyValueFactory<>("maxQuantity"));
        productTable.getColumns().add(maxQuantity);

        TableColumn<Product, String> carDesc = new TableColumn<>("描述");
        carDesc.setCellValueFactory(new PropertyValueFactory<>("carDesc"));
        productTable.getColumns().add(carDesc);

        productTable.getColumns().add(actionColumn);

        List<Product> products = ProductApi.queryProduct(null, "", null);
        productTable.setItems(FXCollections.observableArrayList(products));

    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                        final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
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
                                    Product product = getTableView().getItems().get(getIndex());
                                    handleEditAction(product);
                                });

                                deleteButton.setOnAction(event -> {
                                    Product product = getTableView().getItems().get(getIndex());
                                    handleDeleteAction(product);
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

    private void handleDeleteAction(Product product) {
// 弹出确认删除对话框
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setContentText("确定要删除该商品信息吗吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean res = ProductApi.deleteProduct(product.getId());
            if (res) {
                queryProduct(null);
                // 使用tooltips 提示删除成功，5秒后自动隐藏
                Tooltip tooltip = new Tooltip("删除成功");
                tooltip.setText("删除成功");
                // 设置背景颜色
                tooltip.setStyle("-fx-background-color: #4CAF50;");
                tooltip.setHideDelay(Duration.millis(3000));
                Tooltip.install(productTable, tooltip);
                tooltip.show(productTable.getParent().getScene().getWindow());
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
                        Tooltip.uninstall(productTable, tooltip);
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

    private void handleEditAction(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/product/addProductView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("更新产品信息");
            ButtonType okButtonType = new ButtonType("更新", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            OperationProductController controller = loader.getController();
            controller.initData(product);

            // 使用自定义按钮处理逻辑
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                boolean success = false;
                success = controller.updateProduct(product.getId());
                if (!success) {
                    // 如果添加失败，消耗事件，阻止对话框关闭
                    event.consume();
                }
            });

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButtonType) {
                queryProduct(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
