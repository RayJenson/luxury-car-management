package cn.xzlei.luxurycarjavafx.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xzlei.luxurycarjavafx.constant.Constant;
import cn.xzlei.luxurycarjavafx.entity.Supplier;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierApi {

    private static String PATH = "/supplier";
    public static List<Supplier> getSupplierList(Integer id,String name,String address) {
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            // 拼接URL参数
            StringBuilder url = new StringBuilder();
            url.append("?supplierId=").append(id==null?"":id);
            url.append("&supplierName=").append(name);
            url.append("&address=").append(address);
            String res = HttpRequest.get(Constant.REQUEST_URL + "/supplier/list/"+url)
                    .header("token",CommonApi.getUserToken())
                    .execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            System.out.println( data);
            if(data.getInt("code") == 200){
                return JSONUtil.toList(data.getJSONArray("data"), Supplier.class);
            }else{
                return new ArrayList<>();
            }
        }, new ArrayList<>());
    }

    public static JSONObject addSupplier(Supplier supplier){
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("supplierName",supplier.getSupplierName());
            paramMap.put("contactName",supplier.getContactName());
            paramMap.put("contactPhone",supplier.getContactPhone());
            paramMap.put("address",supplier.getAddress());
            paramMap.put("account",supplier.getAccount());
            paramMap.put("email",supplier.getEmail());
            String jsonStr = JSONUtil.toJsonStr(paramMap);

            String res = HttpRequest.post(Constant.REQUEST_URL + SupplierApi.PATH)
                    .header("token",CommonApi.getUserToken())
                    .body(jsonStr)
                    .execute().body();

            JSONObject data = JSONUtil.parseObj(res);
            System.out.println(data);
            return data;
        }, new JSONObject());
    }

    public static boolean deleteSupplier(Integer id){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.delete(Constant.REQUEST_URL + SupplierApi.PATH + "?ids=" + id)
                    .header("token",CommonApi.getUserToken())
                    .execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            System.out.println(data);
            return data.getInt("code") == 200;
        },  false);
    }

    public static JSONObject updateVendor(Supplier supplier){
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("supplierId",supplier.getSupplierId());
            jsonObject.put("supplierName",supplier.getSupplierName());
            jsonObject.put("contactName",supplier.getContactName());
            jsonObject.put("contactPhone",supplier.getContactPhone());
            jsonObject.put("address",supplier.getAddress());
            jsonObject.put("account",supplier.getAccount());
            jsonObject.put("email",supplier.getEmail());
            String res = HttpRequest.put(Constant.REQUEST_URL+SupplierApi.PATH)
                    .header("token", CommonApi.getUserToken())
                    .body(jsonObject.toString())
                    .execute().body();
            return JSONUtil.parseObj(res);
        }, new JSONObject());
    }
}
