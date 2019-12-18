package com.hupubao.workbook.bean;

/**
 * <h1>数据表行</h1>
 * @author Seichii.wei
 * @date 2019-12-10 14:55:46
 */
public class DataRow {

    private Integer id;
    private String content;
    private String problem;
    private Wanchengdu wanchengdu;
    private DayfWeek dayfWeek;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Wanchengdu getWanchengdu() {
        return wanchengdu;
    }

    public void setWanchengdu(Wanchengdu wanchengdu) {
        this.wanchengdu = wanchengdu;
    }

    public DayfWeek getDayfWeek() {
        return dayfWeek;
    }

    public void setDayfWeek(DayfWeek dayfWeek) {
        this.dayfWeek = dayfWeek;
    }
}
