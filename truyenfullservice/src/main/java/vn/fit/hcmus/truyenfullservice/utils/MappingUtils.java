package vn.fit.hcmus.truyenfullservice.utils;

import org.dozer.DozerBeanMapper;
import vn.fit.hcmus.truyenfullservice.thrift.generated.Comic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MappingUtils {



    public static Comic thrift2db(Comic thrift) {
        Comic ms = new Comic(thrift.getId(),thrift.getName(),thrift.getUrlname(),thrift.getAuthor(),thrift.getSource(),thrift.getStatus(),thrift.getRate());
        return ms;
    }

    private static DozerBeanMapper mapper = new DozerBeanMapper();

    public static <E, T> E convertObject(T input, Class<E> clazz) {
        return mapper.map(input, clazz);
    }

    public static <E, T> List<E> convertList(List<T> input, Class<E> clazz) {
        List<E> listModel = new ArrayList<>();
        for (T t : input) {
            listModel.add(mapper.map(t, clazz));
        }
        return listModel;
    }
}
