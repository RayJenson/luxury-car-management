package cn.xzlei.luxurycarjavafx.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xzlei.luxurycarjavafx.constant.Constant;
import cn.xzlei.luxurycarjavafx.entity.Product;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class ProductApi {
    static final String URI = "/product";

    public static List<Product> queryProduct(Integer productNumber, String productName, Integer typeId) {
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String sb = Constant.REQUEST_URL + URI +
                    "/list?id=" + (productNumber == null ? "" : productNumber) +
                    "&productName=" + (productName == null ? "" : productName) +
                    "&carId=" + (typeId == null ? "" : typeId);
            String res = HttpRequest.get(sb).header("token", CommonApi.getUserToken())
                    .execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            System.out.println( data);

            if(data.getInt("code") == 200){
                return JSONUtil.toList(data.getJSONArray("data"), Product.class);
            }else{
                return new ArrayList<>();
            }
        }, new ArrayList<>());
    }

    public static JSONObject addProduct(Product product) {
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.post(Constant.REQUEST_URL+ProductApi.URI).header("token", CommonApi.getUserToken())
                    .body(JSONUtil.toJsonStr(product)).execute().body();
            return JSONUtil.parseObj(res);
        }, new JSONObject());
    }

    public static JSONObject updateProduct(Product product) {
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.put(Constant.REQUEST_URL+ProductApi.URI).header("token", CommonApi.getUserToken())
                    .body(JSONUtil.toJsonStr(product)).execute().body();
            return JSONUtil.parseObj(res);
        }, new JSONObject());
    }

    public static boolean deleteProduct(Integer id) {
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.delete(Constant.REQUEST_URL+URI+"?ids="+id).header("token", CommonApi.getUserToken())
                    .execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            if(data.getInt("code") == 200){
                return true;
            }else{
                return false;
            }
        }, false);
    }
}
