package com.example.rwthforum;

public class ThreadRowData {
	public final static String TAG = "ThreadRowData";

	
	private int ID;
	private String Body;
	private String Title;
	public ThreadRowData(int iD, String body, String title) {
		ID = iD;
		Body = body;
		Title = title;
	}
	public ThreadRowData() {
		ID = 0;
		Body = null;
		Title =null;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getBody() {
		return Body;
	}
	public void setBody(String body) {
		Body = body;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}

	
}
