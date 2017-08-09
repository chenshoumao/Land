package com.solar.utils;

/**
 * Created by Administrator on 2015/10/20.
 * webApp的相关信息
 */

public class WebApp {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 运行状态 "成功" or "停止"
     */
    private String status;






    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}