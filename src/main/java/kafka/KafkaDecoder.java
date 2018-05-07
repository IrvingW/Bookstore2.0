package kafka;

import common.utils.BeanUtil;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class KafkaDecoder implements Deserializer<Object> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Object deserialize(String topic, byte[] bytes) {
        return BeanUtil.BytesToObject(bytes);
    }

    @Override
    public void close() {
        System.out.println("KafkaDecoder closes");
    }
}
