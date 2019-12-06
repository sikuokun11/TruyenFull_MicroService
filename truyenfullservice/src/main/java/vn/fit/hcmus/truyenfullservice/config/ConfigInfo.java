package vn.fit.hcmus.truyenfullservice.config;

import com.teso.framework.common.Config;

public class ConfigInfo {

    public static final String SPRING_BOOT_CONFIG = Config.getParam("spring_boot", "conf_path");
    public static final String STATIC_SERVER = Config.getParam("static_server", "root_url");

    public static final String EBEAN_GENERATE = Config.getParam("ebean", "dll_generate");
    public static final String EBEAN_RUN = Config.getParam("ebean", "dll_run");
    public static final String EBEAN_USERNAME = Config.getParam("ebean", "db_username");
    public static final String EBEAN_PASSWORD = Config.getParam("ebean", "db_password");
    public static final String EBEAN_URL = Config.getParam("ebean", "db_url");
    public static final String EBEAN_DRIVER = Config.getParam("ebean", "db_driver");
    public static final String EBEAN_ENT_PACKAGE = Config.getParam("ebean", "search_package");
    public static final int SOCKET_PORT = Integer.parseInt(Config.getParam("param", "socket_port"));
}
