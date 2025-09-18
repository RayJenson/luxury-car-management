package cn.xzlei.luxurycarjavafx.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xzlei.luxurycarjavafx.constant.Constant;
import cn.xzlei.luxurycarjavafx.entity.InventoryStatsVO;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class InventoryApi {
    private static final String URI = "/inventory";
    public static List<InventoryStatsVO> queryInventory(){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.get(Constant.REQUEST_URL + URI + "/stats").header("token", CommonApi.getUserToken()).execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            if(data.getInt("code") == 200){
                return JSONUtil.toList(data.getJSONArray("data"), InventoryStatsVO.class);
            }else{
                return new ArrayList<>();
            }
        }, new ArrayList<>());
    }
}
