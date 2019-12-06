package vn.fit.hcmus.truyenfull_api.config;

import generated.TruyenFullData;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Component;

@Component
public class TruyenFullDataClient extends TruyenFullData.Client {
    public TruyenFullDataClient(TProtocolFactory tProtocolFactory) throws TTransportException {
        super(tProtocolFactory.getProtocol(new THttpClient("http://localhost:3000/TruyenFullData")));
    }
}
