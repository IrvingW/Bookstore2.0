package kafka;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestConsumer {
    public static void main(String[] args) throws Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ReceiverDemo receiverDemo = (ReceiverDemo)context.getBean("consumer");
        //ReceiverDemo receiverDemo = new ReceiverDemo();
        receiverDemo.receive();
    }
}
