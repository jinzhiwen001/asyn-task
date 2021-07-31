package com.study.asyntask.client;


import com.study.asyntask.core.AsynPorcess;
import com.study.asyntask.core.AsynProcessor;
import com.study.asyntask.core.AsynTaskResulHandler;
import com.study.asyntask.core.TaskContainer;
import com.study.asyntask.vo.AsynTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.*;


@Component
public class AsynTaskClient {
    private  ExecutorService executorService;

    private TaskContainer taskContainer;

    @Autowired
    private RedisTemplate redisTemplate;


    @PostConstruct
    public void init(){
        executorService = new ThreadPoolExecutor(10, 20, 30L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),new ThreadFactory(){
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("SyncThreadPoll-"+ UUID.randomUUID());
                return t;
            }
        });
        taskContainer = new TaskContainer(redisTemplate);
        taskContainer.startMonitor();
    }

    /**
     * return handler for user change the task status. eg:stop
     * @param asynTask
     * @param porcess
     * @param <T>
     * @return
     */
    public <T> AsynTaskResulHandler<T> submit(AsynTask asynTask, AsynPorcess<T> porcess){
        AsynProcessor<T> processor = new AsynProcessor<>(asynTask,porcess);
        Future<T> future = executorService.submit(processor);
        AsynTaskResulHandler<T> handler = new AsynTaskResulHandler<>(future,processor);
        taskContainer.addTask(handler);
        return handler;
    }

}
