package au.edu.unsw.soacourse.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HiringTeam {
	
	// In this project, we assume each hiring team will contain exactly two reviewers
	private String teamId;
	private String appId;
	private String reviewId1;
	private String reviewId2;
	
	public HiringTeam() {
		
    }
	
	public HiringTeam(String teamId, String reviewId1, String reviewId2) {
		this.teamId = teamId;
		this.reviewId1 = reviewId1;
		this.reviewId2 = reviewId2;
    }
	
	public String getTeamId() {
        return this.teamId;
    }
    
	@XmlElement
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
	
    public String getAppId() {
        return this.appId;
    }
    
    @XmlElement
    public void setAppId(String appId) {
        this.appId = appId;
    }
	
	public String getReviewId1() {
        return this.reviewId1;
    }
	
	@XmlElement
    public void setReviewId1(String reviewId1) {
        this.reviewId1 = reviewId1;
    }
	
	public String getReviewId2() {
        return this.reviewId2;
    }
	
	@XmlElement
    public void setReviewId2(String reviewId2) {
        this.reviewId2 = reviewId2;
    }
}
