package vn.fit.hcmus.truyenfullservice.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.fit.hcmus.truyenfullservice.db.ServiceRepository;
import vn.fit.hcmus.truyenfullservice.thrift.generated.Comic;
import vn.fit.hcmus.truyenfullservice.thrift.generated.TruyenFullService;

@Component
public class ServiceController implements TruyenFullService.Iface {
    private static final Logger LOGGER = LogManager.getLogger(ServiceController.class);
    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public Comic addComic(Comic comic) throws TException {
        try {
            vn.fit.hcmus.truyenfullservice.ent.Comic comic1 = new vn.fit.hcmus.truyenfullservice.ent.Comic();
            comic1.setId(comic.getId());
            comic1.setAuthor(comic.getAuthor());
            comic1.setName(comic.getName());
            comic1.setRate((float) comic.getRate());
            comic1.setStatus(comic.getStatus());
            comic1.setSource(comic.getSource());
            serviceRepository.save(comic1);


        }catch (Exception e){
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }

        return null;
    }

    @Override
    public Comic getComic(long id) throws TException {
        return null;
    }
}
