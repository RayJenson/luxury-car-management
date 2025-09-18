package cn.xzlei.luxurycarjavafx.api;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import cn.xzlei.luxurycarjavafx.constant.Constant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.prefs.Preferences;

public class CommonApi {
    static final Preferences prefs = Preferences.userNodeForPackage(CommonApi.class);
    public static boolean checkLoginStatus(){
        try{
            String res = HttpRequest.post(Constant.REQUEST_URL + "/check/login/status").header("token",CommonApi.getUserToken()).execute().body();
            int status = JSONUtil.parseObj(res).getInt("status");
            return status==1;
        }catch (IORuntimeException err){
            return false;
        }
    }

    public static boolean isDarkMode() {
        // macOS
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            try {
                Process process = Runtime.getRuntime().exec("defaults read -g AppleInterfaceStyle");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String result = reader.readLine();
                return "Dark".equals(result);
            } catch (Exception e) {
                // 默认返回false
            }
        }

        // Windows 10/11
        if (osName.contains("win")) {
            try {
                // 检查注册表项
                Process process = Runtime.getRuntime().exec(
                        "reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize\" /v AppsUseLightTheme"
                );
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("AppsUseLightTheme")) {
                        return line.contains("0x0");
                    }
                }
            } catch (Exception e) {
                // 默认返回false
            }
        }

        return false;
    }


    public static String getUserToken(){
        return prefs.get("token","");
    }
}
