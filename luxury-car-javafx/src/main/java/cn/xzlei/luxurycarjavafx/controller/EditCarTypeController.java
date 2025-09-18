package cn.xzlei.luxurycarjavafx.controller;

import cn.hutool.json.JSONObject;
import cn.xzlei.luxurycarjavafx.api.CarTypeApi;
import cn.xzlei.luxurycarjavafx.entity.CarType;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class EditCarTypeController {
    @FXML
    public DialogPane dialog;
    @FXML
    public TextField carTypeTextField;

    public boolean updateCarType(CarType carType) {
        carType.setTypeName(carTypeTextField.getText());
        return CarTypeApi.updateCarType(carType).getInt("code") == 200;
    }

    public void updateData(CarType carType) {
        carTypeTextField.setText(carType.getTypeName());
    }

    public boolean addCarType() {
        CarType car = CarType.builder().typeName(carTypeTextField.getText()).build();
        JSONObject res = CarTypeApi.addCarType(car);
        if(res.getInt("code") == 200){
            return true;
        }else{
            NetworkExceptionHandler.showError("添加车辆类型失败",res.getStr("message"));
            return false;
        }
    }
}
