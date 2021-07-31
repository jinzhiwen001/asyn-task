package com.study.asyntask.vo;

import java.util.Date;

/**
 * @author jinzhiwen
 * @name TaskMonitor
 * @date 2021-07-30 17:46
 */
public class TaskMonitor {
    private String serial;
    private String taskName;
    private String userNmae;
    private Date createDate;
    private Boolean stop;

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

    public String getUserNmae() {
        return userNmae;
    }

    public void setUserNmae(String userNmae) {
        this.userNmae = userNmae;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getStop() {
        return stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }
}
