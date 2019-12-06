package vn.fit.hcmus.truyenfulldata.config;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.apache.thrift.transport.TTransportException;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import thrift.generated.TruyenFullData;
import vn.fit.hcmus.truyenfulldata.controller.DataController;

@Configuration
public class TruyenFullServer {
    @Bean
    public TProtocolFactory tProtocolFactory() throws TTransportException {
        return new TBinaryProtocol.Factory();
    }

    @Bean
    public ServletRegistrationBean thriftBookServlet(TProtocolFactory protocolFactory, DataController controller) throws  TTransportException{
        TServlet tServlet = new TServlet(new TruyenFullData.Processor<>(controller),protocolFactory);
        return new ServletRegistrationBean(tServlet,"/TruyenFullData");
    }
}
