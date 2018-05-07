package webSocket;

import webSocket.decoders.MessageDecoder;
import webSocket.encoders.*;
import webSocket.messages.*;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(
        value = "/websocket",
        decoders = { MessageDecoder.class },
        encoders = { JoinMessageEncoder.class, ChatMessageEncoder.class,
                InfoMessageEncoder.class, UsersMessageEncoder.class })
public class WebSocketTest {
    private static int onlineCount = 0;

    private static final String GUEST_PREFIX = "user";
    private static final AtomicInteger connectionIds = new AtomicInteger(0);

    private static CopyOnWriteArraySet<WebSocketTest> webSocketTests = new CopyOnWriteArraySet<WebSocketTest>();

    private Session session;
    private String nickName;
    private static Set<String> onlineUsers = new CopyOnWriteArraySet<>();
    //private static Map<String,String> onlineUsers = new HashMap<>();

    public static Set<String> getOnlineUsers() {
        return onlineUsers;
    }

    public WebSocketTest(){
        nickName = GUEST_PREFIX + connectionIds.getAndDecrement();
    }

    @OnOpen
    public void onOpen(Session session){

        this.session = session;
        webSocketTests.add(this);

        addOnlineCount();
        System.out.println("current online number:" + getOnlineCount());

    }

    @OnClose
    public void onClose(Session session){


        subOnlineCount();
        webSocketTests.remove(this);
        for(WebSocketTest webSocketTest : webSocketTests){
            webSocketTest.sendAll(new InfoMessage("当前聊天室人数：" + getOnlineCount() + "人"));
        }
        System.out.println("1 socket closed,current online number:" + getOnlineCount());
    }

    @OnError
    public void onError(Session session,Throwable throwable){
        System.out.println("Error occurred");
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Message message, Session session){
        System.out.println("message: " + message);

        for(WebSocketTest webSocketTest : webSocketTests){
            webSocketTest.sendMsg(message,webSocketTest);
        }

    }

    public synchronized void sendAll(Object msg) {
        try {
            this.session.getBasicRemote().sendObject(msg);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    public List<String> getUserList() {
        List<String> users = new ArrayList<>();
        for(String str : getOnlineUsers()){
            users.add(str);
        }
        return users;
    }


    public void sendMsg(Message message,WebSocketTest webSocketTest) {
        if(message instanceof JoinMessage){
            JoinMessage jmsg = (JoinMessage) message;
            addOnlineUsers(jmsg.getName());
            sendAll(new InfoMessage(jmsg.getName() + " 加入了聊天室"));
            sendAll(new InfoMessage("当前聊天室人数：" + getOnlineCount() + "人"));
            sendAll(new ChatMessage("陈昊鹏", jmsg.getName(), "欢迎加入!!"));
            sendAll(new UsersMessage(getUserList()));
        }
        else if(message instanceof ChatMessage){
            final ChatMessage cmsg = (ChatMessage) message;
            sendAll(cmsg);
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }

    public static synchronized void addOnlineUsers(String user) {
        onlineUsers.add(user);
    }

    public static synchronized void deleteOnlineUsers(String user) {
        onlineUsers.remove(user);
    }
}
