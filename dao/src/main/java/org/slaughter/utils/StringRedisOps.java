package main.java.org.slaughter.utils;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2024年01月30日 15:49
 */
public class StringRedisOps extends StringRedisTemplate {


    /**
     * @param key
     * @param value
     * @return
     */
    public Boolean set(String key, String value) {
        byte[] rawKey = getStringSerializer().serialize(key);
        byte[] rawVal = getStringSerializer().serialize(value);
        return execute((RedisCallback<Boolean>) connection -> connection.set(rawKey, rawVal));
    }

    /**
     * @param key
     * @param value
     * @param timeout 超时时间(秒)
     */
    public void set(String key, String value, long timeout) {
        opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public <T> T parseObject(String key,Class<T> objectClass){
        return JSONObject.parseObject(opsForValue().get(key),objectClass);
    }


    /**
     * @param key
     */
    public String get(String key) {
        return opsForValue().get(key);
    }


    /**
     * @param key
     * @return
     */
    public Long getLong(String key) {
        String val = opsForValue().get(key);
        if (val == null) {
            return null;
        }
        return Long.valueOf(val);
    }

    /**
     * @param key
     * @param timeout 超时时间(秒)
     * @return
     */
    public Boolean expire(String key, long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     *
     * @param key
     * @param value
     * @return
     */
    public Long lPush(String key, String... value) {
        return opsForList().leftPushAll(key, value);
    }

    /**
     * 将一个 插入到列表 key 的表头
     *
     * @param key
     * @param value
     * @return
     */
    public Long lPush(String key, String value) {
        return opsForList().leftPush(key, value);
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     *
     * @param key
     * @param value
     * @return
     */
    public Long lPushAll(String key, Collection value) {
        return opsForList().leftPushAll(key, value);
    }

    /**
     * 移除并返回列表 key 的尾元素
     *
     * @param key
     * @return
     */
    public String rPop(String key) {
        return (String) opsForList().rightPop(key);
    }

    /**
     * 移除并返回列表 key 的尾元素
     *
     * @param key
     * @return
     */
    public Object rightPop(String key) {
        return opsForList().rightPop(key);
    }

    /**
     * 移除并返回列表 key 的尾元素
     *
     * @param key
     * @return
     */
    public Object leftPop(String key) {
        return opsForList().leftPop(key);
    }

    /**
     * 返回列表 key 的长度
     *
     * @param key
     * @return
     */
    public Long lLen(String key) {
        return opsForList().size(key);
    }

    /**
     * 返回列表 key 中指定区间内的元素
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lRange(String key, long start, long end) {
        return opsForList().range(key, start, end);
    }

    /**
     * 将 key 中储存的数字值增一
     *
     * @param key
     * @return 执行++之后的value
     */
    public Long incr(String key) {
        byte[] rawKey = getStringSerializer().serialize(key);
        return execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incr(rawKey);
            }

        });
    }

    /**
     * 将 key 中储存的数字加上指定的增量值
     *
     * @param key
     * @return 执行++之后的value
     */
    public Long incrBy(String key, long value) {
        byte[] rawKey = getStringSerializer().serialize(key);
        return execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incrBy(rawKey, value);
            }

        });
    }

    /**
     * 将 key 中储存的数字值减一
     *
     * @param key
     * @return 执行--之后的value
     */
    public Long decr(String key) {
        byte[] rawKey = getStringSerializer().serialize(key);
        return execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.decr(rawKey);
            }

        });
    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     *
     * @param key
     * @param value
     * @param expirationTime 过期时间(秒)
     * @return
     */
    public Boolean setNX(String key, long value, long expirationTime) {
        byte[] rawKey = getStringSerializer().serialize(key);
        byte[] rawVal = getStringSerializer().serialize(Long.toString(value));
        return execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.set(rawKey, rawVal, Expiration.seconds(expirationTime), RedisStringCommands.SetOption.ifAbsent());
            }

        });
    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean setNX(String key, String value) {
        return opsForValue().setIfAbsent(key, value);
    }

    /**
     * 向有序集合添加一个成员
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Boolean zadd(String key, String value, double score) {
        return opsForZSet().add(key, value, score);
    }

    /**
     * 向有序集合添加多个成员
     *
     * @param key
     * @param tuples
     * @return
     */
    public Long zadd(String key, Set<ZSetOperations.TypedTuple<String>> tuples) {
        return opsForZSet().add(key, tuples);
    }

    /**
     * 移除有序集合中的一个或多个成员
     *
     * @param key
     * @param values
     * @return
     */
    public Long zrem(String key, Object... values) {
        return opsForZSet().remove(key, values);
    }

    /**
     * 返回有序集中指定区间内的成员，通过索引，分数从高到底
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> revrange(String key, long start, long end) {
        return opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 返回有序集中指定区间内的成员，通过索引，分数从底到高
     * 兼容O2O保存方式
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<Object> zRangeByO2O(String key, long start, long end) {
        byte[] rawKey = getStringSerializer().serialize(key);
        Set<byte[]> rawValues = execute(connection -> connection.zRange(rawKey, start, end), true);
        return (Set<Object>) SerializationUtils.deserialize(rawValues, getDefaultSerializer());
    }

    /**
     * 增加地理位置
     *
     * @param key
     * @param value
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    public Long geoAdd(String key, String value, double longitude, double latitude) {
        return opsForGeo().add(key, new Point(longitude, latitude), value);
    }

    /**
     * 返回范围内List
     *
     * @param key
     * @param longitude
     * @param latitude
     * @param radius    距离 米
     * @return null 无
     */
    public List<GeoResult<RedisGeoCommands.GeoLocation<String>>> geoRadiusWithDistList(String key, double longitude, double latitude, double radius) {
        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = geoRadiusWithDist(key, longitude, latitude, radius);
        return geoResults.getContent();
    }

    /**
     * 返回范围内GeoResults
     *
     * @param key
     * @param longitude
     * @param latitude
     * @param radius    距离 m
     * @return null 无
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> geoRadiusWithDist(String key, double longitude, double latitude, double radius) {
        return opsForGeo().radius(key,
                new Circle(new Point(longitude, latitude), new Distance(radius, RedisGeoCommands.DistanceUnit.METERS)),
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().sortAscending());
    }

    /**
     * 判断 member 元素是否集合 key 的成员。
     *
     * @param key
     * @param value
     * @return
     */
    public boolean sIsMember(String key, String value) {
        byte[] rawKey = getStringSerializer().serialize(key);
        byte[] rawVal = getStringSerializer().serialize(value);
        return execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.sIsMember(rawKey, rawVal);
            }

        });
    }

    /**
     * Set增加值
     *
     * @param key
     * @param value
     * @return
     */
    public Long sAdd(String key, String... value) {
        return opsForSet().add(key, value);
    }

    /**
     * 移除Set对应值
     *
     * @param key
     * @param value
     * @return
     */
    public Long sRem(String key, String value) {
        return opsForSet().remove(key, value);
    }

    /**
     * 迭代SET集合中的元素
     *
     * @param key
     * @param count
     * @param pattern
     * @return
     */
    public Cursor<String> sscan(String key, long count, String pattern) {
        ScanOptions so = ScanOptions.scanOptions().count(count).match(pattern).build();
        return opsForSet().scan(key, so);
    }

    /**
     * 将哈希表 key 中的字段 field 的值设为 value 。
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hSet(String key, Object hashKey, Object value) {
        opsForHash().put("", hashKey, value);
    }

    /**
     * 获取存储在哈希表中指定字段的值。
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hGet(String key, Object hashKey) {
        return opsForHash().get(key, hashKey);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 delta 。
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public Long hIncr(String key, Object hashKey, long delta) {
        return opsForHash().increment(key, hashKey, delta);
    }

    /**
     * getset命令自动将key对应到value并且返回原来key对应的value。如果key存在但是对应的value不是字符串，返回错误。
     * @param key
     * @param value
     * @return
     */
    public String getAndSet(final String key, String value) {
        String oldValue = null;
        try {
            oldValue = opsForValue().getAndSet(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oldValue;
    }

    public void remove(final String key, long count, String value) {
        opsForList().remove(key, count, value);
    }

    /**
     * 删除指定key
     * @param key key
     */
    public Boolean remove(final String key){
        return delete(key);
    }


}
