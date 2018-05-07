package kafka;

import com.opensymphony.xwork2.ActionContext;
import com.sun.javafx.collections.MappingChange;
import common.constants.OrderStatus;
import common.utils.AESUtil;
import common.utils.PasswordUtil;
import dao.BookDao;
import dao.OrderDao;
import dao.OrderItemDao;
import dao.UserDao;
import dao.impl.BookDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.OrderItemDaoImpl;
import dao.impl.UserDaoImpl;
import model.Book;
import model.Order;
import model.OrderItem;
import model.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.struts2.components.Bean;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import service.OrderService;
import service.imp.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class ReceiverDemo {
    /*
    private OrderService orderService;

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    */


    //public ReceiverDemo(){}

    public void receive() throws Exception{
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        //props.put("session.timeout.ms", "3000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "kafka.KafkaDecoder");
        KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("testJms2"));

        boolean result = false;
        while(true){
            System.out.println("poll start");
            ConsumerRecords<String, Object> consumerRecords = consumer.poll(1000);
            int count = consumerRecords.count();
            System.out.println("numbers of topic" + count);
            for(ConsumerRecord<String, Object> consumerRecord : consumerRecords){
                Checkout checkout= (Checkout) consumerRecord.value();
                System.out.println(checkout.getAddress() + "," + checkout.getPassword() + "," + checkout.getUserID());
                String address = checkout.getAddress();
                String password = checkout.getPassword();
                int userID = checkout.getUserID();
                List<Map<String,Object>> cartList = checkout.getCartList();

                ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
                OrderService orderService = (OrderService) context.getBean("orderService");
                result = orderService.kafkaCreateOrder(userID,address,password,cartList);

            }
            /*
            if(result){
                consumer.close();
                break;
            }
            */
        }
    }




    public static void main(String[] args) throws Exception{
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        //props.put("session.timeout.ms", "3000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "kafka.KafkaDecoder");
        KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("testJms3"));

        boolean result = false;
        while(true){
            System.out.println("poll start");
            ConsumerRecords<String, Object> consumerRecords = consumer.poll(1000);
            int count = consumerRecords.count();
            System.out.println("numbers of topic" + count);
            for(ConsumerRecord<String, Object> consumerRecord : consumerRecords){
                Checkout checkout= (Checkout) consumerRecord.value();
                System.out.println(checkout.getAddress() + "," + checkout.getPassword() + "," + checkout.getUserID());
                String address = checkout.getAddress();
                String password = checkout.getPassword();
                int userID = checkout.getUserID();
                List<Map<String,Object>> cartList = checkout.getCartList();

                ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
                OrderService orderService = (OrderService) context.getBean("orderService");
                result = orderService.kafkaCreateOrder(userID,address,password,cartList);

            }
            /*
            if(result){
                consumer.close();
                break;
            }
            */
        }
    }



}
