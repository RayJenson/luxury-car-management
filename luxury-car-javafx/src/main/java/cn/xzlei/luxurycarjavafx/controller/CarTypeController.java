package cn.xzlei.luxurycarjavafx.controller;

import cn.xzlei.luxurycarjavafx.api.CarTypeApi;
import cn.xzlei.luxurycarjavafx.entity.CarType;
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

public class CarTypeController {
    public TableView<CarType> carTypeTable;
    public TableColumn<CarType, Void> actionColumn;

    public void initialize() {
        // 其他初始化代码...
        addActionButtonsToTable();
        carTypeTable.getColumns().clear();
        carTypeTable.setPrefHeight(50000);

        TableColumn<CarType, Integer> id = new TableColumn<>("汽车类型ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        carTypeTable.getColumns().add(id);

        TableColumn<CarType, String> typeName = new TableColumn<>("汽车品牌名");
        typeName.setCellValueFactory(new PropertyValueFactory<>("typeName"));
        carTypeTable.getColumns().add(typeName);

        carTypeTable.getColumns().add(actionColumn);

        queryCarType();
    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<CarType, Void>, TableCell<CarType, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<CarType, Void> call(final TableColumn<CarType, Void> param) {
                        final TableCell<CarType, Void> cell = new TableCell<>() {
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
                                    CarType carType = getTableView().getItems().get(getIndex());
                                    handleEditAction(carType);
                                });

                                deleteButton.setOnAction(event -> {
                                    CarType carType = getTableView().getItems().get(getIndex());
                                    handleDeleteAction(carType);
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

    private void handleDeleteAction(CarType carType) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除确认");
        alert.setContentText("确定要删除吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean res = CarTypeApi.deleteCarType(carType.getId());
            if (res) {
                queryCarType();
                // 使用tooltips 提示删除成功，5秒后自动隐藏
                Tooltip tooltip = new Tooltip("删除成功");
                tooltip.setText("删除成功");
                // 设置背景颜色
                tooltip.setStyle("-fx-background-color: #4CAF50;");
                tooltip.setHideDelay(Duration.millis(3000));
                Tooltip.install(carTypeTable, tooltip);
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
                        Tooltip.uninstall(carTypeTable, tooltip);
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

    private void queryCarType() {
        List<CarType> carTypeList = CarTypeApi.getCarTypeList();
        carTypeTable.setItems(FXCollections.observableArrayList(carTypeList));
    }

    private void handleEditAction(CarType carType) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/cartype/editCarTypeView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("更新汽车类型信息");
            ButtonType okButtonType = new ButtonType("更新", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            // 使用自定义按钮处理逻辑
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                EditCarTypeController controller = loader.getController();
                boolean success = false;
                try {
                    success = controller.updateCarType(carType);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (!success) {
                    // 如果添加失败，消耗事件，阻止对话框关闭
                    event.consume();
                }
            });

            // 初始化数据
            EditCarTypeController controller = loader.getController();
            controller.updateData(carType);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButtonType) {
                queryCarType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryAll(ActionEvent actionEvent) {
        queryCarType();
    }

    public void openAddCarTypeDialog(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cn/xzlei/luxurycarjavafx/cartype/addCarTypeView.fxml"));
            DialogPane dialogPane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("新增汽车品牌");
            ButtonType okButtonType = new ButtonType("新增", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
            // 使用自定义按钮处理逻辑
            dialogPane.lookupButton(okButtonType).addEventFilter(ActionEvent.ACTION, event -> {
                EditCarTypeController controller = loader.getController();
                boolean success = false;
                success = controller.addCarType();
                if (!success) {
                    // 如果添加失败，消耗事件，阻止对话框关闭
                    event.consume();
                }
            });

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == okButtonType) {
                queryCarType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
