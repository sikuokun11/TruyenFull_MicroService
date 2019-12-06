/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.fit.hcmus.truyenfullservice.db;

import io.ebean.*;
import io.ebean.Query;
import io.ebean.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;


@Repository
public class ServiceRepository {

    @Autowired
    private EbeanServer server;


    public <T> Stream<T> findAll(Class<T> clazz) {
        return server.find(clazz).findList().stream();
    }

    public <T> T findById(Class<T> clazz, long id) {
        return server.find(clazz, id);
    }

    public <T> T findOneBy(Class<T> clazz, String column, Object val) {
        return server.find(clazz).where().eq(column, val).findOne();
    }

    public <T> Query<T> query(Class<T> clazz) {
        return server.find(clazz);
    }

    @Transactional
    public <T> void save(T bean) {
        server.save(bean);
    }

    @Transactional
    public <T> void update(T bean) {
        server.update(bean);
    }

    @Transactional
    public <T> void saveAll(Collection<T> beans) {
        server.saveAll(beans);
    }

    @Transactional
    public <T> void delete(T bean) {
        server.delete(bean);
    }

    @Transactional
    public <T> void deleteById(Class<T> clazz, int id) {
        server.delete(clazz, id);
    }

    @Transactional
    public <T> void deleteAll(Collection<T> beans) {
        server.deleteAll(beans);
    }

    @Transactional
    public <T> void deleteAllById(Class<T> clazz, List<Integer> ids) {
        server.deleteAll(clazz, ids);
    }
}
