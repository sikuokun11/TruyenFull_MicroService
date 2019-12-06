package vn.fit.hcmus.truyenfulldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.fit.hcmus.truyenfulldata.model.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter,Long> {
    Chapter findByName(String name);
}
