package com.example.rwthforum;

public class CourseRowData {
	public final static String TAG = "CourseRowData";

	private String CampusId;
	private String CourseType;
	private String FullUrl;
	private String ID;
	private String MasterId;
	private String Semester;
	private String Status;
	private String Title;

	public CourseRowData(String CampusId, String CourseType, String FullUrl,
			String ID, String MasterId, String Semester, String Status,
			String Title) {
		this.CampusId = CampusId;
		this.CourseType = CourseType;
		this.FullUrl = FullUrl;
		this.ID = ID;
		this.MasterId = MasterId;
		this.Semester = Semester;
		this.Status = Status;
		this.Title = Title;
	}
	public CourseRowData() {
		// TODO Auto-generated constructor stub
		this.CampusId = null;
		this.CourseType = null;
		this.FullUrl = null;
		this.ID = null;
		this.MasterId = null;
		this.Semester = null;
		this.Status =null;
		this.Title = null;
	}

	public String getCampusId() {
		return CampusId;
	}

	public void setCampusId(String campusId) {
		CampusId = campusId;
	}

	public String getCourseType() {
		return CourseType;
	}

	public void setCourseType(String courseType) {
		CourseType = courseType;
	}

	public String getFullUrl() {
		return FullUrl;
	}

	public void setFullUrl(String fullUrl) {
		FullUrl = fullUrl;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getMasterId() {
		return MasterId;
	}

	public void setMasterId(String masterId) {
		MasterId = masterId;
	}

	public String getSemester() {
		return Semester;
	}

	public void setSemester(String semester) {
		Semester = semester;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}
}
