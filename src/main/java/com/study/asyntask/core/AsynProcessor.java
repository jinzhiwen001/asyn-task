package com.study.asyntask.core;

import com.study.asyntask.vo.AsynRunStatus;
import com.study.asyntask.vo.AsynTask;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author jinzhiwen
 * @name SyncProcessor
 * @date 2021-07-30 15:42
 */
public class AsynProcessor<T> implements Callable<T> {

    private AsynPorcess<T> syncPorcess;
    private AsynTask asynTask;

    private Thread thread;

    private AtomicBoolean run = new AtomicBoolean(false);

    public AsynProcessor(AsynTask asynTask, AsynPorcess<T> syncPorcess){
        this.syncPorcess = syncPorcess;
        this.asynTask = asynTask;
    }

    @Override
    public T call() throws Exception {
        T t = null;
        if(run.compareAndSet(false,true)){
            thread = Thread.currentThread();
            runChangeStatus(AsynRunStatus.RUNNING);
            try {
                t = syncPorcess.process();
                runChangeStatus(AsynRunStatus.SUCCESS);
            }catch (Exception e){
                runChangeStatus(AsynRunStatus.FAILED);
            }finally {

            }

        }

        return t;
    }

    //advice web site like RestTemplate call a url for web socket skill
    private void runChangeStatus(AsynRunStatus success) {
        switch (success){
            case RUNNING: {
                //do RUNNING
            }
            case STOPPED: {
                //do STOPPED
            }
            case FAILED:{
                //do FAILED
            }
            case SUCCESS:{
                //do SUCCESS
            }

        }
    }


    public AsynPorcess<T> getSyncPorcess() {
        return syncPorcess;
    }

    public AsynTask getAsynTask() {
        return asynTask;
    }


    public void stop() {
        if(run.compareAndSet(true,false)){
            thread.interrupt();
        }

    }
}
