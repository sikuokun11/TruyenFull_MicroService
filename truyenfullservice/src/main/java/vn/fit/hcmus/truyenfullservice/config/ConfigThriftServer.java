package vn.fit.hcmus.truyenfullservice.config;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServlet;
import org.apache.thrift.transport.TTransportException;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.fit.hcmus.truyenfullservice.controller.ServiceController;
import vn.fit.hcmus.truyenfullservice.thrift.generated.TruyenFullService;

@Configuration
public class ConfigThriftServer {
    @Bean
    public TProtocolFactory tProtocolFactory() throws TTransportException {
        return new TBinaryProtocol.Factory();
    }

    @Bean
    public ServletRegistrationBean thriftBookServlet(TProtocolFactory protocolFactory, ServiceController controller) throws  TTransportException{
        TServlet tServlet = new TServlet(new TruyenFullService.Processor<>(controller),protocolFactory);
        return new ServletRegistrationBean(tServlet,"/TruyenFullService");
    }

}
