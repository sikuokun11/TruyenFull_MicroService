package vn.fit.hcmus.truyenfull_restapi.config;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.apache.thrift.transport.TTransportException;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import thrift.generated.TruyenFullService;
import vn.fit.hcmus.truyenfull_restapi.controller.CrawlerController;

import javax.servlet.ServletRegistration;

@Configuration
public class TruyenFullServer {
    @Bean
    public TProtocolFactory tProtocolFactory() throws TTransportException {
        return new TBinaryProtocol.Factory();
    }

    @Bean
    public ServletRegistrationBean thriftBookServlet(TProtocolFactory protocolFactory, CrawlerController controller) throws  TTransportException{
        TServlet tServlet = new TServlet(new TruyenFullService.Processor<>(controller),protocolFactory);
        return new ServletRegistrationBean(tServlet,"/TruyenFull");
    }
}
