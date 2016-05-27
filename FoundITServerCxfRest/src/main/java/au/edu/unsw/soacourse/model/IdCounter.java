package au.edu.unsw.soacourse.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdCounter {

	private int id;
	
	public IdCounter() {
		
	}
	
	public int getId() {
		return this.id;
	}
	
	@XmlElement(name = "id")
	public void setId(int id) {
		this.id = id;
	}
	
	
//	@XmlElement(name = "nextAppId")
//	public int getNextAppId() {
////		this.applicationCounter++;
//		setNextAppId(applicationCounter);
//		return this.applicationCounter;
//	}
//	
//	public void setNextAppId(int applicationCounter) {
//		this.applicationCounter = applicationCounter;
//	}
//	
//	@XmlElement(name = "nextCompanyId")
//	public int getNextCompanyId() {
////		this.companyCounter++;
//		setNextAppId(companyCounter);
//		return this.companyCounter;
//	}
//	
//	public void setNextCompanyId(int companyCounter) {
//		this.companyCounter = companyCounter;
//	}
//	
//	@XmlElement(name = "nextTeamId")
//	public int getNextTeamId() {
////		this.teamCounter++;
//		setNextAppId(teamCounter);
//		return this.teamCounter;
//	}
//	
//	public void setNextTeamId(int teamCounter) {
//		this.teamCounter = teamCounter;
//	}
//	
//	@XmlElement(name = "nextJobId")
//	public int getNextJobId() {
////		this.jobCounter++;
//		setNextAppId(jobCounter);
//		return this.jobCounter;
//	}
//	
//	public void setNextJobId(int jobCounter) {
//		this.jobCounter = jobCounter;
//	}
//	
//	@XmlElement(name = "nextJobalertId")
//	public int getNextJobalertId() {
////		this.jobalertCounter++;
//		setNextAppId(jobalertCounter);
//		return this.jobalertCounter;
//	}
//	
//	public void setNextJobalertId(int jobalertCounter) {
//		this.jobalertCounter = jobalertCounter;
//	}
//	
//	@XmlElement(name = "nextReviewId")
//	public int getNextReviewId() {
////		this.reviewCounter++;
//		setNextAppId(reviewCounter);
//		return this.reviewCounter;
//	}
//	
//	public void setNextReviewId(int reviewCounter) {
//		this.reviewCounter = reviewCounter;
//	}
//	
//	@XmlElement(name = "nextUserId")
//	public int getNextUserId() {
////		this.userCounter++;
//		setNextAppId(userCounter);
//		return this.userCounter;
//	}
//	
//	public void setNextUserId(int userCounter) {
//		this.userCounter = userCounter;
//	}
}
