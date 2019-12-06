package vn.fit.hcmus.truyenfullservice.config;


import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static vn.fit.hcmus.truyenfullservice.config.ConfigInfo.*;

@Component
public class ConfigEbeanFactory implements FactoryBean<EbeanServer> {

    private static final Logger LOGGER = LogManager.getLogger(ConfigEbeanFactory.class);

    @Override
    public EbeanServer getObject() throws Exception {

	try {
	    ServerConfig cfg = new ServerConfig();
	    Properties properties = new Properties();
	    properties.put("ebean.db.ddl.generate", "true");
	    properties.put("ebean.db.ddl.run", "true");
	    properties.put("datasource.db.username", "root");
	    properties.put("datasource.db.password", "123456789");
	    properties.put("datasource.db.databaseUrl", "jdbc:mysql://localhost:3306/truyenfull_app?useUnicode=yes&characterEncoding=UTF-8&character_set_server=utf8mb4");
	    properties.put("datasource.db.databaseDriver", "com.mysql.jdbc.Driver");
	    properties.put("ebean.search.packages", "vn.fit.hcmus.truyenfullservice.ent");
	    cfg.loadFromProperties(properties);
	    return EbeanServerFactory.create(cfg);
	} catch (Exception e) {
	    LOGGER.error(ExceptionUtils.getStackTrace(e));
	    //SlackUtils.sendMessage(ExceptionUtils.getMessage(e), ExceptionUtils.getStackTrace(e));
	    return null;
	}
    }

    @Override
    public Class<?> getObjectType() {
	return EbeanServer.class;
    }

    @Override
    public boolean isSingleton() {
	return true;
    }
}
