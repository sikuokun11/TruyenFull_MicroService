package vn.fit.hcmus.truyenfull_api.config;

import generated.TruyenFullService;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Component;

@Component
public class TruyenFullClientCrawler extends TruyenFullService.Client {
    public TruyenFullClientCrawler(TProtocolFactory tProtocolFactory) throws TTransportException {
        super(tProtocolFactory.getProtocol(new THttpClient("http://localhost:8081/TruyenFull")));
    }
}
