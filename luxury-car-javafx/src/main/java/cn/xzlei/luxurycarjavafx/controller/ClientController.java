package cn.xzlei.luxurycarjavafx.controller;

import cn.xzlei.luxurycarjavafx.api.CustomerApi;
import cn.xzlei.luxurycarjavafx.entity.Customer;
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

public class ClientController {
    @FXML
    public TextField customerNameInput;
    @FXML
    public TextField customerPhoneNumberInput;
    @FXML
    public TextField customerGender;
    @FXML
    public Button queryButton;
    @FXML
    public Button resetButton;
    @FXML
    public TableView<Customer> customerTable;
    @FXML
    public Button addButton;
    @FXML
    private TableColumn<Customer, Void> actionColumn;

    public void initialize() {
        // 其他初始化代码...
        addActionButtonsToTable();
        customerTable.getColumns().clear();
        customerTable.setPrefHeight(5000);

        TableColumn<Customer, Integer> id = new TableColumn<>("客户ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerTable.getColumns().add(id);

        TableColumn<Customer, String> customerName = new TableColumn<>("客户姓名");
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTable.getColumns().add(customerName);

        TableColumn<Customer, String> phone = new TableColumn<>("手机号码");
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerTable.getColumns().add(phone);

        TableColumn<Customer, String> email = new TableColumn<>("电子邮箱");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerTable.getColumns().add(email);

        TableColumn<Customer, String> address = new TableColumn<>("地址");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerTable.getColumns().add(address);

        TableColumn<Customer, String> gender = new TableColumn<>("性别");
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        customerTable.getColumns().add(gender);

        TableColumn<Customer, String> workplace = new TableColumn<>("工作单位");
        workplace.setCellValueFactory(new PropertyValueFactory<>("workplace"));
        customerTable.getColumns().add(workplace);


        customerTable.getColumns().add(actionColumn);

        // 请求供应商列表
        List<Customer> vendors = CustomerApi.getCustomerList(null,null,null);

        customerTable.setItems(FXCollections.observableArrayList(vendors));


    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
                        final TableCell<Customer, Void> cell = new TableCell<>() {
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
                                    Customer customer = getTableView().getItems().get(getIndex());
                                    handleEditAction(customer);
                                });

                                deleteButton.setOnAction(event -> {
                                    Customer customer = getTableView().getItems().get(getIndex());
                                    handleDeleteAction(customer);
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

    private void handleDeleteAction(Customer customer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除确认");
        alert.setContentText("确定要删除吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean res = CustomerApi.deleteCustomer(customer.getId());
            if (res) {
                queryCustomer(null);

                // 使用tooltips 提示删除成功，5秒后自动隐藏
                Tooltip tooltip = new Tooltip("删除成功");
                tooltip.setText("删除成功");
                // 设置背景颜色
                tooltip.setStyle("-fx-background-color: #4CAF50;");
                tooltip.setHideDelay(Duration.millis(3000));
                Tooltip.install(customerTable, tooltip);
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
                        Tooltip.uninstall(customerTable, tooltip);
                    });
                }).start();
            }
            else{
                Alert errAlert = new Alert(Alert.AlertType.ERROR);
                errAlert.setTitle("删除失败");
                errAlert.setContentText("删除失败");
                errAlert.showAndWait();
            }
        }
    }

    private void handleEditAction(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/client/editClientView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("更新客户信息");
            ButtonType okButtonType = new ButtonType("更新", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            // 使用自定义按钮处理逻辑
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                EditClientController controller = loader.getController();
                boolean success = false;
                try {
                    success = controller.updateCustomer(customer);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (!success) {
                    // 如果添加失败，消耗事件，阻止对话框关闭
                    event.consume();
                }
            });

            // 初始化数据
            EditClientController controller = loader.getController();
            controller.updateData(customer);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButtonType) {
                // 刷新供应商列表
                queryCustomer(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void queryCustomer(ActionEvent actionEvent) {
        List<Customer> customers = CustomerApi.getCustomerList(customerNameInput.getText(), customerPhoneNumberInput.getText(), customerGender.getText());
        customerTable.setItems(FXCollections.observableArrayList(customers));
    }

    public void reset(ActionEvent actionEvent) {
        customerNameInput.setText("");
        customerPhoneNumberInput.setText("");
        customerGender.setText("");
        queryCustomer(actionEvent);
        addActionButtonsToTable();
    }

    public void openAddCustomerDialog(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/client/addClientView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("新建客户信息");
            ButtonType okButtonType = new ButtonType("新增", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            // 使用自定义按钮处理逻辑
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                AddClientController controller = loader.getController();
                boolean success = false;
                success = controller.addCustomer();
                if (!success) {
                    // 如果添加失败，消耗事件，阻止对话框关闭
                    event.consume();
                }
            });

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButtonType) {
                // 刷新供应商列表
                queryCustomer(actionEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
