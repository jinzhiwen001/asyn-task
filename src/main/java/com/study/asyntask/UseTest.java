package com.study.asyntask;

import com.study.asyntask.client.AsynTaskClient;
import com.study.asyntask.core.AsynPorcess;
import com.study.asyntask.vo.AsynTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * jzw
 * 2021/7/31
 **/
@Component
public class UseTest {

    @Autowired
    private AsynTaskClient client;

    public void asynDo(){
        AsynTask task = new AsynTask();
        task.setTaskName("测试任务");
        task.setSerial(UUID.randomUUID().toString());

        client.submit(task, new AsynPorcess<String>() {
            @Override
            public <T> T process() {
                //do something
                return null;
            }
        });
    }

}
