module cn.xzlei.luxurycarjavafx {
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires atlantafx.base;
    requires java.desktop;
    requires static lombok;
    requires cn.hutool;
    requires java.prefs;
    requires java.sql;
    requires javafx.controls;
    requires java.net.http;
    requires java.base;
    requires darklaf.core;
    requires darklaf.theme;

    opens cn.xzlei.luxurycarjavafx to cn.hutool,javafx.controls;
    exports cn.xzlei.luxurycarjavafx to javafx.controls;
    exports cn.xzlei.luxurycarjavafx.controller to javafx.fxml;  // 关键修复点
    opens cn.xzlei.luxurycarjavafx.controller to javafx.fxml;
    exports cn.xzlei.luxurycarjavafx.application;
    opens cn.xzlei.luxurycarjavafx.application to javafx.fxml;
    opens cn.xzlei.luxurycarjavafx.entity to javafx.base, cn.hutool;
    opens cn.xzlei.luxurycarjavafx.api to java.sql, java.base,java.net.http,cn.hutool;
    exports cn.xzlei.luxurycarjavafx.dto to cn.hutool;
    exports cn.xzlei.luxurycarjavafx.entity to cn.hutool;
}