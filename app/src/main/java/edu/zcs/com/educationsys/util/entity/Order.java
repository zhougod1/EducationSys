package edu.zcs.com.educationsys.util.entity;

public class Order {

    private String oid;
    private String aid;
    private String otitle;
    private String ocourse;
    private String oarea;
    private String aphone;
    private String oinfo;
    private String oaddress;
    private String ocycle;
    private String olevel;
    private String otime;
    private String odate;
    private int pay;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getOtitle() {
        return otitle;
    }

    public void setOtitle(String otitle) {
        this.otitle = otitle;
    }

    public String getOcourse() {
        return ocourse;
    }

    public void setOcourse(String ocourse) {
        this.ocourse = ocourse;
    }

    public String getOarea() {
        return oarea;
    }

    public void setOarea(String oarea) {
        this.oarea = oarea;
    }

    public String getAphone() {
        return aphone;
    }

    public void setAphone(String aphone) {
        this.aphone = aphone;
    }

    public String getOinfo() {
        return oinfo;
    }

    public void setOinfo(String oinfo) {
        this.oinfo = oinfo;
    }

    public String getOaddress() {
        return oaddress;
    }

    public void setOaddress(String oaddress) {
        this.oaddress = oaddress;
    }

    public String getOcycle() {
        return ocycle;
    }

    public void setOcycle(String ocycle) {
        this.ocycle = ocycle;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public String getOlevel() {return olevel;}

    public void setOlevel(String olevel) {this.olevel = olevel; }

    public String getOtime() {
        return otime;
    }

    public void setOtime(String otime) {
        this.otime = otime;
    }

    public String getOdate() {return odate;}

    public void setOdate(String odate) {this.odate = odate;}
}
