package kafka;

import common.utils.BeanUtil;
import kafka.utils.VerifiableProperties;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class KafkaEncoder implements Serializer<Object> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String topic, Object o) {
        return BeanUtil.ObjectToBytes(o);
    }

    @Override
    public void close() {
        System.out.println("KafkaEncode is closed");
    }

}
