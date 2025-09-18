package cn.xzlei.luxurycarjavafx.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xzlei.luxurycarjavafx.constant.Constant;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;

public class DeptApi {
    public static JSONArray getDeptList(){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.get(Constant.REQUEST_URL + "/dept/list").execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            return data.getJSONArray("data");
        }, new JSONArray());

    }
}
