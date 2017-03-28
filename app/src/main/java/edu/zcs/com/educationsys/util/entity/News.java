package edu.zcs.com.educationsys.util.entity;

/**
 * 
 * 用户消息表
 *
 */
public class News {
	private String n_id;
	private String n_typle;//消息类型：订单消息，还是问答消息
	private String aid;
	private String n_title;
	private String n_uid;
	private String n_static;
	private String n_sid;//消息来源账单
	private String n_time;
	private String oid;
	
	public String getN_id() {
		return n_id;
	}
	public void setN_id(String n_id) {
		this.n_id = n_id;
	}
	public String getN_typle() {
		return n_typle;
	}
	public void setN_typle(String n_typle) {
		this.n_typle = n_typle;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getN_uid() {
		return n_uid;
	}
	public void setN_uid(String n_uid) {this.n_uid = n_uid;}
	public String getN_static() {
		return n_static;
	}
	public void setN_static(String n_static) {
		this.n_static = n_static;
	}
	public String getN_sid() {
		return n_sid;
	}
	public void setN_sid(String n_sid) {
		this.n_sid = n_sid;
	}
	public String getN_time() {
		return n_time;
	}
	public void setN_time(String n_time) {
		this.n_time = n_time;
	}
	public String getN_title() {return n_title;}
	public void setN_title(String n_title) {this.n_title = n_title;}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
}
