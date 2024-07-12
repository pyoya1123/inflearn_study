package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }

    /*
    프로퍼티 세팅이 끝나면, 그니까 의존관계 주입이 끝나면 호출하겠다.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient.afterPropertiestSet");
        connect();
        call("초기화 연결 메시지");
    }

    /*
    빈이 종료될 때 호출될거임.
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient.destory");
        disconnect();
    }
}
