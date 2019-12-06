package vn.fit.hcmus.truyenfulldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.fit.hcmus.truyenfulldata.model.Comic;

import java.util.List;

@Repository
public interface ComicRepository extends JpaRepository<Comic,Long>, CrudRepository<Comic,Long> {

    Comic findByName(String name);

    // TIM TOP TRUYEN RATING CAO
    @Query(value = "select *  from comic c ORDER BY  c.rate DESC  Limit 0, 1",nativeQuery = true)
    List<Comic> findByTop1();


    List<Comic> findByAuthor(String name);

    // TIM NHUNG TRUYEN UNG VOI TRANG THAI
    @Query(value = "select *  from comic c where c.status = ?1 ",nativeQuery = true)
    List<Comic> findByStatus(String name);

    // QUERY INSERT Comic
    @Modifying
    @Query(value = "insert into comic(author,name,rate,source,status,urlname)  VALUES (:author,:name,:rate,:source,:status,:urlname)",nativeQuery = true)
    @Transactional
    void insertComic(@Param("author") String author,@Param("name") String name, @Param("rate") Float rate, @Param("source") String source, @Param("status") String status, @Param("urlname") String urlname);


    // QUERY DELETE Comic
    @Modifying
    @Query(value = "delete from comic c where c.name = ?1",nativeQuery = true)
    @Transactional
    void deleteComicByName(String name);

    // QUERY UPDATE Comic
    @Modifying
    @Query(value = "update comic c set c.name =:name where c.name =:nem",nativeQuery = true)
    @Transactional
    void updateComicName(@Param("nem") String nem,@Param("name") String name);







}
