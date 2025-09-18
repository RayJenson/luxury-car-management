package cn.xzlei.luxurycarjavafx.handler;

import javafx.scene.control.Alert;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class NetworkExceptionHandler {
    public static <T> T handleNetworkCall(NetworkCall<T> call, T defaultValue){
        try {
            return call.execute();
        } catch (Exception e) {
            // 先检查特定的网络异常（通过 instanceof）
            if (e instanceof ConnectException ||
                    (e.getCause() != null && e.getCause() instanceof ConnectException)) {
                System.err.println("连接错误: 无法连接到服务器");
                showError("连接错误", "无法连接到服务器，请检查网络连接！");
            } else if (e instanceof UnknownHostException ||
                    (e.getCause() != null && e.getCause() instanceof UnknownHostException)) {
                System.err.println("网络错误: 无法解析服务器地址");
                showError("网络错误", "无法解析服务器地址，请检查网络连接！");
            } else if (e instanceof SocketTimeoutException ||
                    (e.getCause() != null && e.getCause() instanceof SocketTimeoutException)) {
                System.err.println("超时错误: 请求超时");
                showError("超时错误", "请求超时，请稍后重试！");
            } else {
                System.err.println("未知错误: " + e.getMessage());
                showError("未知错误", "未知错误: " + e.getMessage());
            }
        }
        return defaultValue;
    }

    @FunctionalInterface
    public interface NetworkCall<T> {
        T execute() throws Exception;
    }

    public static String test(){
        return "";
    }
    
    public static void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}
