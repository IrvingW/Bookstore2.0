package aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

public class Aop {
    private final Logger logger = Logger.getLogger("OperateLog");

    public void begin(int bookID, int amount) {
        logger.info("开始事务,图书ID=" + bookID + "数量=" + amount);
    }

    public void after(int bookID, int amount) {
        logger.info("提交事务,图书ID=" + bookID + "数量=" + amount);
    }

    public void afterReturning() {
        logger.info("afterReturning()");
    }

    public void afterThrowing() {
        logger.info("afterThrowing()");
    }

    public Object around(ProceedingJoinPoint point) throws Throwable {
        logger.info("环绕前……");
        Object res = point.proceed();
        logger.info("环绕后……");
        return res;
    }
}
