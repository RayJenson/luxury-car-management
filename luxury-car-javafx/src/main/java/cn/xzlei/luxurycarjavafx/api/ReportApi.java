package cn.xzlei.luxurycarjavafx.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xzlei.luxurycarjavafx.constant.Constant;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;

public class ReportApi {
    private static final String URI = "/report";

    public static JSONObject queryPurchaseOrder(Integer page, Integer size){
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            String res = HttpRequest.get(Constant.REQUEST_URL + URI + "/purchase?page="+page+"&pageSize="+size).header("token", CommonApi.getUserToken()).execute().body();
            return JSONUtil.parseObj(res);
        }, null);
    }

    public static JSONObject querySalesRecord(Integer page, Integer size) {
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            String res = HttpRequest.get(Constant.REQUEST_URL + URI + "/sales?page="+page+"&pageSize="+size).header("token", CommonApi.getUserToken()).execute().body();
            return JSONUtil.parseObj(res);
        }, null);
    }
}
