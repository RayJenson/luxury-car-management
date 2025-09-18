package cn.xzlei.luxurycarjavafx.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xzlei.luxurycarjavafx.constant.Constant;
import cn.xzlei.luxurycarjavafx.entity.Customer;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerApi {
    private static final String PATH = "/customer";

    public static List<Customer> getCustomerList(String name,String phoneNumber,String gender){
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            Map<String,Object> param = Map.of("customerName",name==null?"":name,"phone",(phoneNumber==null?"":phoneNumber),"gender",gender==null?"":gender);
            String url = HttpUtil.urlWithForm(Constant.REQUEST_URL+PATH+"/list", param, Charset.defaultCharset(),false);
            String res = HttpRequest.get(url)
                    .header("token",CommonApi.getUserToken())
                    .execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            if(data.getInt("code") == 200){
                return JSONUtil.toList(data.getJSONArray("data"), Customer.class);
            }else{
                return new ArrayList<>();
            }
        }, new ArrayList<>());
    }

    public static JSONObject addCustomer(Customer customer){
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("customerName",customer.getCustomerName());
            paramMap.put("phone",customer.getPhone());
            paramMap.put("email",customer.getEmail());
            paramMap.put("address",customer.getAddress());
            paramMap.put("gender",customer.getGender());
            paramMap.put("workplace",customer.getWorkplace());
            paramMap.put("nickname",customer.getNickname());
            String jsonStr = JSONUtil.toJsonStr(paramMap);

            String res = HttpRequest.post(Constant.REQUEST_URL + CustomerApi.PATH)
                    .header("token",CommonApi.getUserToken())
                    .body(jsonStr)
                    .execute().body();

            return JSONUtil.parseObj(res);
        }, new JSONObject());
    }

    public static JSONObject updateCustomer(Customer customer){
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("customerName",customer.getCustomerName());
            paramMap.put("phone",customer.getPhone());
            paramMap.put("email",customer.getEmail());
            paramMap.put("address",customer.getAddress());
            paramMap.put("gender",customer.getGender());
            paramMap.put("workplace",customer.getWorkplace());
            paramMap.put("nickname",customer.getNickname());
            paramMap.put("id",customer.getId());
            String jsonStr = JSONUtil.toJsonStr(paramMap);

            String res = HttpRequest.put(Constant.REQUEST_URL + CustomerApi.PATH)
                    .header("token",CommonApi.getUserToken())
                    .body(jsonStr)
                    .execute().body();

            return JSONUtil.parseObj(res);
        }, new JSONObject());
    }

    public static boolean deleteCustomer(Integer id){
        return NetworkExceptionHandler.handleNetworkCall(() -> {
            String res = HttpRequest.delete(Constant.REQUEST_URL + CustomerApi.PATH + "/?ids=" + id)
                    .header("token",CommonApi.getUserToken())
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
