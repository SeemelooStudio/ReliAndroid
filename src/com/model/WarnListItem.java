package com.model;

import java.util.Date;

public class WarnListItem {

	private int warningId;
	private String warningTitle;
	private String warningContent;
	private Date reportedAt = new Date();
	private String strWarnOther;
	
	public int getWarningId() {
		return warningId;
	}
	public void setWarningId(int warningId) {
		this.warningId = warningId;
	}
	public String getWarningTitle() {
		return warningTitle;
	}
	public void setWarningTitle(String warningTitle) {
		this.warningTitle = warningTitle;
	}
	public String getWarningContent() {
		return warningContent;
	}
	public void setWarningContent(String warningContent) {
		this.warningContent = warningContent;
	}
	public Date getReportedAt() {
		return reportedAt;
	}
	public void setReportedAt(Date reportedAt) {
		this.reportedAt = reportedAt;
	}
	public String getStrWarnOther() {
		return strWarnOther;
	}
	public void setStrWarnOther(String warn_other) {
		this.strWarnOther = warn_other;
	}
}
