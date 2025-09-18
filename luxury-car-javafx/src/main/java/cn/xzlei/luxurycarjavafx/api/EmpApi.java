package cn.xzlei.luxurycarjavafx.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xzlei.luxurycarjavafx.constant.Constant;
import cn.xzlei.luxurycarjavafx.dto.EmpQueryParamDTO;
import cn.xzlei.luxurycarjavafx.entity.Emp;
import cn.xzlei.luxurycarjavafx.handler.NetworkExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class EmpApi {
    public static JSONObject login(String account, String password, Integer deptId){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String body = String.format("{\"account\":\"%s\",\"password\":\"%s\",\"deptId\":%d}",account,password,deptId);
            String res = HttpRequest.post(Constant.REQUEST_URL + "/emp/login").body(body).execute().body();
            return JSONUtil.parseObj(res);
        },new JSONObject());
    }
    public static List<Emp> getEmpList(EmpQueryParamDTO empQueryParamDTO){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String params = "";
            JSONObject parse = JSONUtil.parseObj(empQueryParamDTO);
            for (String key : parse.keySet()) {
                params += "&" + key + "=" + parse.get(key);
            }
            String res = HttpRequest.get(Constant.REQUEST_URL + "/emp/list?"+params).header("token", CommonApi.getUserToken()).execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            if (data.getInt("code") == 200) {
                return JSONUtil.toList(data.getJSONArray("data"), Emp.class);
            } else {
                return new ArrayList<>();
            }
        },List.of());
    }


    public static JSONObject addEmp(Emp emp){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String body = JSONUtil.toJsonStr(emp);
            String res = HttpRequest.post(Constant.REQUEST_URL + "/emp").header("token", CommonApi.getUserToken()).body(body).execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            return data;
        },new JSONObject());
    }

    public static JSONObject updateEmp(Emp emp){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String body = JSONUtil.toJsonStr(emp);
            String res = HttpRequest.put(Constant.REQUEST_URL + "/emp").header("token", CommonApi.getUserToken()).body(body).execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            return data;
        },new JSONObject());
    }

    public static JSONObject deleteEmp(Integer id){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.delete(Constant.REQUEST_URL + "/emp?ids=" + id).header("token", CommonApi.getUserToken()).execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            return data;
        },new JSONObject());
    }

    public static JSONObject resetPwd(Integer id){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.put(Constant.REQUEST_URL + "/emp/resetPwd?id=" + id).header("token", CommonApi.getUserToken()).execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            return data;
        },new JSONObject());
    }

    public static JSONObject getUserInfo(){
        return NetworkExceptionHandler.handleNetworkCall(()->{
            String res = HttpRequest.get(Constant.REQUEST_URL + "/emp/userInfo").header("token", CommonApi.getUserToken()).execute().body();
            JSONObject data = JSONUtil.parseObj(res);
            return data;
        },new JSONObject());
    }
}
