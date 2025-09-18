package cn.xzlei.luxurycarjavafx.controller;

import cn.xzlei.luxurycarjavafx.api.InventoryApi;
import cn.xzlei.luxurycarjavafx.entity.InventoryStatsVO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class InventoryController {

    @FXML
    public TableView<InventoryStatsVO> inventoryTable;

    public void initialize(){
        inventoryTable.getColumns().clear();
        inventoryTable.setPrefHeight(5000);

        TableColumn<InventoryStatsVO, Integer> carTypeId = new TableColumn<>("库存ID");
        carTypeId.setCellValueFactory(new PropertyValueFactory<>("carTypeId"));
        inventoryTable.getColumns().add(carTypeId);

        TableColumn<InventoryStatsVO, String> carTypeName = new TableColumn<>("库存名称");
        carTypeName.setCellValueFactory(new PropertyValueFactory<>("carTypeName"));
        inventoryTable.getColumns().add(carTypeName);

        TableColumn<InventoryStatsVO, Integer> carTypeCount = new TableColumn<>("库存总数");
        carTypeCount.setCellValueFactory(new PropertyValueFactory<>("carTypeCount"));
        inventoryTable.getColumns().add(carTypeCount);

        List<InventoryStatsVO> res = InventoryApi.queryInventory();
        inventoryTable.setItems(FXCollections.observableArrayList(res));
    }

    public void queryAll(ActionEvent actionEvent) {
        List<InventoryStatsVO> res = InventoryApi.queryInventory();
        inventoryTable.setItems(FXCollections.observableArrayList(res));
    }
}
