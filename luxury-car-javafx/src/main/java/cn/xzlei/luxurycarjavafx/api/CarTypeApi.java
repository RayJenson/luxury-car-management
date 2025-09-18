package cn.xzlei.luxurycarjavafx.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xzlei.luxurycarjavafx.constant.Constant;
import cn.xzlei.luxurycarjavafx.entity.CarType;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class CarTypeApi {
    public static final String URI = "/cartype";
    public static List<CarType> getCarTypeList(){
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            String res = HttpRequest.get(Constant.REQUEST_URL + CarTypeApi.URI +"/list").execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            if (data.getInt("code") == 200) {
                return JSONUtil.toList(data.getJSONArray("data"), CarType.class);
            } else {
                return new ArrayList<>();
            }
        }, null);
    }

    public static JSONObject addCarType(CarType carType){
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            String res = HttpRequest.post(Constant.REQUEST_URL + CarTypeApi.URI).body(JSONUtil.toJsonStr(carType)).execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            return data;
        }, null);
    }

    public static JSONObject updateCarType(CarType carType){
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            String res = HttpRequest.put(Constant.REQUEST_URL + CarTypeApi.URI).body(JSONUtil.toJsonStr(carType)).execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            return data;
        }, null);
    }

    public static boolean deleteCarType(Integer id){
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            String res = HttpRequest.delete(Constant.REQUEST_URL + CarTypeApi.URI + "?id=" + id).execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            return data.getInt("code") == 200;
        }, false);
    }
}
