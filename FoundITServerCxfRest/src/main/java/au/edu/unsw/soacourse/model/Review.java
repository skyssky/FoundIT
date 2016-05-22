package au.edu.unsw.soacourse.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Review {
	private String reviewId;
	private String appId;
	private String reviewerId;
	private String comment;
	private String decision;
	
	public Review() {
		
    }

	public Review(String reviewId, String appId, String reviewerId, String comment, String decision) {
		this.reviewId = reviewId;
		this.appId = appId;
		this.reviewerId = reviewerId;
		this.comment = comment;
		this.decision = decision;
    }
	
	public String getReviewId() {
        return this.reviewId;
    }
	
	@XmlElement
    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }
    
    public String getAppId() {
        return this.appId;
    }
    
    @XmlElement
    public void setAppId(String appId) {
        this.appId = appId;
    }
    
    public String getReviewerId() {
        return this.reviewerId;
    }
    
    @XmlElement
    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }
    
    public String getComment() {
        return this.comment;
    }
    
    @XmlElement
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getDecision() {
        return this.decision;
    }
    
    @XmlElement
    public void setDecision(String decision) {
        this.decision = decision;
    }
}
