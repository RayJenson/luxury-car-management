package cn.xzlei.luxurycarjavafx.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xzlei.luxurycarjavafx.constant.Constant;
import cn.xzlei.luxurycarjavafx.entity.SalesRecord;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class SalesRecordApi {

    private static final String URI = "/salesRecord";

    public static List<SalesRecord> querySalesRecord(){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.get(Constant.REQUEST_URL + URI + "/list").header("token", CommonApi.getUserToken()).execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(res);
            System.out.println("用户token："+CommonApi.getUserToken());
            System.out.println("销售记录："+res);
            if(jsonObject.getInt("code") == 200){
                return JSONUtil.toList(jsonObject.getJSONArray("data"), SalesRecord.class);
            }else{
                return new ArrayList<>();
            }
        }, new ArrayList<>());
    }

    public static JSONObject addSalesRecord(SalesRecord salesRecord){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.post(Constant.REQUEST_URL + URI).header("token", CommonApi.getUserToken()).body(JSONUtil.toJsonStr(salesRecord)).execute().body();
            return JSONUtil.parseObj(res);
        }, new JSONObject());
    }

    public static JSONObject updateSalesRecord(SalesRecord salesRecord){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.put(Constant.REQUEST_URL + URI).header("token", CommonApi.getUserToken()).body(JSONUtil.toJsonStr(salesRecord)).execute().body();
            return JSONUtil.parseObj(res);
        }, new JSONObject());
    }

    public static boolean deleteSalesRecord(Integer id){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.delete(Constant.REQUEST_URL + URI + "?id=" + id).header("token", CommonApi.getUserToken()).execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(res);
            return jsonObject.getInt("code") == 200;
        }, false);
    }
}
