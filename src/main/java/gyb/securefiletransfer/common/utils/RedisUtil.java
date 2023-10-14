package gyb.securefiletransfer.common.utils;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Date 2023/10/14 16:51
 * @Author 郜宇博
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    public boolean existsKey(final Object key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey((String) key));
    }



    public void hmSet(Object key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put((String) key, hashKey, value);
    }

    public Object hmGet(Object key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get((String) key, hashKey);
    }



}
