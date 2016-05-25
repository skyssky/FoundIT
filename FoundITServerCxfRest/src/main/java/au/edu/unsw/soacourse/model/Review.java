package au.edu.unsw.soacourse.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Review {
	
	public enum ReviewDecision {YES, NO};	// shortlist or not
	
	private String reviewId;
	private String appId;
	private String reviewerId;
	private String comment;
	private ReviewDecision decision;
	
	public Review() {
		
    }

	public Review(String reviewId, String appId, String reviewerId, String comment, ReviewDecision decision) {
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
    
    public ReviewDecision getDecision() {
        return this.decision;
    }
    
    @XmlElement
    public void setDecision(ReviewDecision decision) {
        this.decision = decision;
    }
}
