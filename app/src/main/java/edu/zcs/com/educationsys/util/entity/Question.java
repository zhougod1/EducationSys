package edu.zcs.com.educationsys.util.entity;

/**
 * 
 * 在线问题
 *
 */
public class Question {
	
	private String qid;
	private String aid;
	private String qtitle;
	private String qcontent;
	private String time;
	private String img;
	private String course;
	private int adopt;
	
	
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getQtitle() {
		return qtitle;
	}
	public void setQtitle(String qtitle) {
		this.qtitle = qtitle;
	}
	public String getQcontent() {
		return qcontent;
	}
	public void setQcontent(String qcontent) {
		this.qcontent = qcontent;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public int getAdopt() {
		return adopt;
	}
	public void setAdopt(int adopt) {
		this.adopt = adopt;
	}
	public String getImg() {return img;}
	public void setImg(String img) {this.img = img;}
}
