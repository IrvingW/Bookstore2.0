package redis;

import common.utils.BeanUtil;
import model.Category1;
import model.Category2;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class RedisJava {
    public static void main(String[] args) {
        RedisClient redisClient = new RedisClient();
        Jedis jedis = redisClient.getJedis();
        Category2 category2 = new Category2();
        category2.setCategory2ID(1);
        category2.setCategory2Name("Fucker");
        List<Category2> category2s = new ArrayList<>();
        category2s.add(category2);
        Category1 category1 = new Category1();
        category1.setCategory1ID(2);
        category1.setCategory1Name("chp");
        category1.setCategory2List(category2s);

        jedis.del("categoryList".getBytes());
        //jedis.lpush("categoryList".getBytes(), BeanUtil.ObjectToBytes(category1));
        //System.out.println("List:" + jedis.lrange("categoryList".getBytes(), 0, -1) + "\n");
        List<byte[]> category = jedis.lrange("categoryList".getBytes(),0,-1);
        if(category == null){
            System.out.println("null");
        }
        System.out.println(jedis.llen("categoryList".getBytes()));
        for(byte[] bytes : category) {
            Category1 res = (Category1)BeanUtil.BytesToObject(bytes);
            System.out.println(res.getCategory1Name());
            List<Category2> category2List = res.getCategory2List();
            System.out.println(category2List.get(0).getCategory2Name());
        }
    }
}
