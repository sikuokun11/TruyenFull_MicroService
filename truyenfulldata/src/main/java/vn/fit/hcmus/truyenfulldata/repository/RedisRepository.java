package vn.fit.hcmus.truyenfulldata.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vn.fit.hcmus.truyenfulldata.model.Comic;

import java.util.Map;

@Repository
public class RedisRepository {
    private static final String KEY = "Comic";
    //private static final String KEY2 = "Chapterlist";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void add(Comic comic) {
        redisTemplate.opsForHash().putIfAbsent(KEY, comic.getId(), comic);
    }

    public String findStory(int id) {
        return redisTemplate.opsForHash().get(KEY, id).toString();
    }

    public Map<Object, Object> findAllStory() {
        return redisTemplate.opsForHash().entries(KEY);
    }

}
