package com.study.asyntask.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.asyntask.vo.TaskMonitor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author jinzhiwen
 * @name TaskContainer
 * @date 2021-07-30 16:40
 */
public class TaskContainer {

    private String taskKey = "synctask:%s";

    private RedisTemplate redisTemplate;

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private AtomicBoolean started = new AtomicBoolean(false);

    private ConcurrentHashMap<String,AsynTaskResulHandler> map = new ConcurrentHashMap<>();


    public void startMonitor(){
        if(started.compareAndSet(false,true)){
            service.scheduleAtFixedRate(
                    ()->{
                        if(!map.isEmpty()){
                            for(AsynTaskResulHandler handler:map.values()){
                                updateMonitorStatus(handler);
                            }
                        }

                    },1,2,TimeUnit.SECONDS);
        }
    }


    /**
     * if monitor's information was bend changed like stop, we should stop this task by taskHandler
     * TaskMonitor usually changed by user's operate in web side
     *
     * @param handler
     */
    private void updateMonitorStatus(AsynTaskResulHandler handler) {
        String sMonitor = (String)redisTemplate.opsForValue().get(String.format(taskKey,handler.getAsynTask().getSerial()));
        TaskMonitor monitor = JSONObject.parseObject(sMonitor, TaskMonitor.class);
        if(monitor.getStop().booleanValue()==true){
            handler.stop();
        }
        if(handler.getAsynTask().getCompleted()){
            map.remove(handler.getAsynTask().getSerial());
        }

    }

    public TaskContainer(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public <T> void addTask(AsynTaskResulHandler<T> handler){

        map.putIfAbsent(handler.getAsynTask().getSerial(),handler);
        TaskMonitor monitor = new TaskMonitor();
        BeanUtils.copyProperties(handler.getAsynTask(),monitor);
        monitor.setStop(false);

        redisTemplate.opsForValue().set(String.format(taskKey,handler.getAsynTask().getSerial()), JSON.toJSONString(monitor),2,TimeUnit.HOURS);
    }



}
