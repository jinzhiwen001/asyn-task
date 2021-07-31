package com.study.asyntask.vo;

/**
 * @author jinzhiwen
 * @name AsynTask
 * @date 2021-07-30 15:13
 */
public class AsynTask {
    private String serial;
    private String taskName;
    private Boolean stop;

    private Boolean completed = false;

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getStop() {
        return stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


}
