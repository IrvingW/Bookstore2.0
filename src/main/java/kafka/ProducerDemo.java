package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;

public class ProducerDemo {
    public ProducerDemo(){}
    /*
    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    */

    public void sendMsg(String address, String password, int userID, List cartList){
        Properties props = new Properties();
        props.put("bootstrap.servers","localhost:9092");
        //props.put("metadata.broker.list", "localhost:9092");
        props.put("acks", "1");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "kafka.KafkaEncoder");
        KafkaProducer<String,Object> producer = new KafkaProducer<String, Object>(props);

        String topic = "testJms3";
        String message = "address,password";
        Checkout checkout = new Checkout(address,password,userID,cartList);
        ProducerRecord<String,Object> record = new ProducerRecord<String, Object>(topic,checkout);

        producer.send(record);
        System.out.println("send message over");
        producer.close();
    }

    public static void main(String[] args) throws Exception {
        ProducerDemo producerDemo = new ProducerDemo();
        //producerDemo.sendMsg("address","password");
    }
}
