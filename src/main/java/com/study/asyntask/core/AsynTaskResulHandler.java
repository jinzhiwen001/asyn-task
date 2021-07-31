package com.study.asyntask.core;

import com.study.asyntask.vo.AsynTask;

import java.util.concurrent.Future;

/**
 * @author jinzhiwen
 * @name AsynTaskResulHandle
 * @date 2021-07-30 16:28
 */
public class AsynTaskResulHandler<T> {

    private Future<T> future;
    private AsynProcessor<T> processor;
    private AsynTask asynTask;


    public AsynTaskResulHandler(Future<T> future, AsynProcessor<T> processor) {
        this.future = future;
        this.processor = processor;
        this.asynTask = processor.getAsynTask();
    }


    public Future<T> getFuture() {
        return future;
    }

    public AsynProcessor<T> getProcessor() {
        return processor;
    }

    public AsynTask getAsynTask() {
        return asynTask;
    }

    public void stop(){
        processor.stop();
    }

}
