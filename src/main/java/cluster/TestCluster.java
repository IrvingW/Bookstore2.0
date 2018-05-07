package cluster;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestCluster {
    /*
    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
        TestDaoW testDaoW = (TestDaoW) context.getBean("TestDaoW");
        TestDaoR testDaoR = (TestDaoR) context.getBean("TestDaoR");
        testUser testUser = new testUser();

        testUser.setName("test3");
        testUser.setUserID(3);

        testDaoW.save(testUser);

        testUser testUser1 = new testUser();
        testUser1 = testDaoR.getUser(3);
        System.out.println(testUser1.getName());

    }
    */
}
