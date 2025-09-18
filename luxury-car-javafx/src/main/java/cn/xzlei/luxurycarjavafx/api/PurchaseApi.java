package cn.xzlei.luxurycarjavafx.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xzlei.luxurycarjavafx.constant.Constant;
import cn.xzlei.luxurycarjavafx.entity.PurchaseOrder;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseApi {
    private static final String URI = "/purchase/order";
    public static List<PurchaseOrder> queryOrder(){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.get(Constant.REQUEST_URL + URI + "/list").header("token", CommonApi.getUserToken()).execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(res);
            System.out.println(jsonObject);
            if(jsonObject.getInt("code") == 200){
                return JSONUtil.toList(jsonObject.getJSONArray("data"), PurchaseOrder.class);
            }else{
                return new ArrayList<>();
            }
        }, new ArrayList<>());
    }

    public static JSONObject addOrder(PurchaseOrder purchaseOrder){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("productId",purchaseOrder.getProductId());
            paramMap.put("supplierId",purchaseOrder.getSupplierId());
            paramMap.put("orderQuantity",purchaseOrder.getOrderQuantity());
            paramMap.put("orderDate", purchaseOrder.getOrderDateByDate().atZone(ZoneId.systemDefault()).toEpochSecond());
            paramMap.put("payMethod",purchaseOrder.getPayMethod());
            paramMap.put("isPurchase",purchaseOrder.getIsPurchase()?1:0);
            paramMap.put("note",purchaseOrder.getNote());
            String res = HttpRequest.post(Constant.REQUEST_URL + URI).header("token", CommonApi.getUserToken()).body(JSONUtil.toJsonStr(paramMap)).execute().body();
            return JSONUtil.parseObj(res);
        }, new JSONObject());
    }

    public static JSONObject updateOrder(PurchaseOrder purchaseOrder){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.put(Constant.REQUEST_URL + URI).header("token", CommonApi.getUserToken()).body(JSONUtil.toJsonStr(purchaseOrder)).execute().body();
            return JSONUtil.parseObj(res);
        }, new JSONObject());
    }

    public static boolean deleteOrder(Integer id){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.delete(Constant.REQUEST_URL + URI + "?id=" + id).header("token", CommonApi.getUserToken()).execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(res);
            return jsonObject.getInt("code") == 200;
        }, false);
    }
}
