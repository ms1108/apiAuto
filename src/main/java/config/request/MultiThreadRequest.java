package config.request;

import api.RequestData;
import config.asserts.SuccessAssertDefault;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import utils.ReportUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static io.restassured.RestAssured.given;

public class MultiThreadRequest implements Runnable, IRequest {
    private RequestData requestData;
    private List<Response> responses = new ArrayList<>();
    private Lock lock = new ReentrantLock();//锁要在属性中new，不能再run方法中new，不然锁无效，因为多个线程就new出了多个锁不合理

    public MultiThreadRequest() {
    }

    public MultiThreadRequest(RequestData requestData) {
        this.requestData = requestData;
    }

    @SneakyThrows
    @Override
    public void run() {
        //重新创建specification的目的：子线程发送请求时都会用到given()，会出现死锁问题，所以每个线程都创建一份独立的specification
        RequestSpecification specification = given();

        Map<String, Object> headers = requestData.getHeaders().getHeaders(requestData);
        specification.headers(headers);

        specification = requestData.getMethodAndRequestType().getParamMethod().paramMethodBuild(specification, requestData);

        //发送请求
        Response response = specification.request(requestData.getMethodAndRequestType().getApiMethod(), requestData.getUri());

        //存储响应时加锁
        lock.lock();
        this.responses.add(response);
        lock.unlock();
    }

    @SneakyThrows
    @Override
    public Response requestMethod(RequestSpecification specification, RequestData requestData) {
        int multiThreadNum = requestData.getMultiThreadNum();
        MultiThreadRequest multiThreadRequest = new MultiThreadRequest(requestData);
        List<Thread> threads = new ArrayList<>();
        //通过起多线程同时快速的向后端发起多个相同参数的请求
        for (int i = 0; i < multiThreadNum; i++) {
            Thread thread = new Thread(multiThreadRequest);
            thread.start();
            threads.add(thread);
        }
        threads.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        if (multiThreadRequest.responses.size() > 1) {
            for (int i = 1; i < multiThreadRequest.responses.size(); i++) {
                Response response = multiThreadRequest.responses.get(i);
                ReportUtil.log("res       :" + response.getBody().asString());
                //对非第一个完成请求的response做默认成功断言
                if (requestData.isOpenAssert()) {
                    new SuccessAssertDefault().assets(requestData, response);
                }

            }
        }
        return multiThreadRequest.responses.get(0);
    }
}
