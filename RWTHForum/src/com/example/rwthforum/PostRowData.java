package com.example.rwthforum;

public class PostRowData {
public final static String TAG = "PostRowData";

	
	private int ID,ParentThreadId;
	private String Body;
	private String ThreadIndex;
	public PostRowData(int iD, int parentThreadId, String body,
			String threadIndex) {
		ID = iD;
		ParentThreadId = parentThreadId;
		Body = body;
		ThreadIndex = threadIndex;
	}
	public PostRowData() {
		ID = 0;
		ParentThreadId = 0;
		Body = null;
		ThreadIndex = null;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getParentThreadId() {
		return ParentThreadId;
	}
	public void setParentThreadId(int parentThreadId) {
		ParentThreadId = parentThreadId;
	}
	public String getBody() {
		return Body;
	}
	public void setBody(String body) {
		Body = body;
	}
	public String getThreadIndex() {
		return ThreadIndex;
	}
	public void setThreadIndex(String threadIndex) {
		ThreadIndex = threadIndex;
	}
	

}
