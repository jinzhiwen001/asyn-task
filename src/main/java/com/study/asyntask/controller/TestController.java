package com.study.asyntask.controller;

import com.study.asyntask.client.AsynTaskClient;
import com.study.asyntask.core.AsynPorcess;
import com.study.asyntask.vo.AsynTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * jzw
 * 2021/8/1
 **/
@RestController
@RequestMapping("/asyn-task")
public class TestController {

    @Autowired
    AsynTaskClient client;

    @GetMapping(value = "/task/add/{id}")
    public String addTask(@PathVariable("id")String id){
        AsynTask task = new AsynTask();
        task.setSerial(UUID.randomUUID().toString());
        task.setTaskName("测试任务");

        client.submit(task, new AsynPorcess<String>() {
            @Override
            public String process() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return "success";
            }
        });
        return null;
    }
}
