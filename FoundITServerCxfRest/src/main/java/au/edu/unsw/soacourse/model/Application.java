package au.edu.unsw.soacourse.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Application {
	
	public enum Status { CREATED, OPEN, INREVIEW, PROCESSED, SENTINVITATION };	// status of this job posting
	
	private String appId;		// job id
	private String link;		// a link to user profile
	private String cover;		// cover letter
	private Status status;

	public Application() {
		
    }
	
	public Application(String appId, String link, String cover, Status status) {
		this.appId = appId;
		this.link = link;		// TODO should be URI link to user profile
		this.cover = cover;
		this.status = status;
    }

    public String getAppId() {
        return this.appId;
    }
    
    @XmlElement
    public void setAppId(String appId) {
        this.appId = appId;
    }
    
    public String getLink() {
        return this.link;
    }
    
    @XmlElement
    public void setLink(String link) {
        this.link = link;
    }
    
    public String getCover() {
        return this.cover;
    }
    
    @XmlElement
    public void setCover(String cover) {
        this.cover = cover;
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    @XmlElement
    public void setStatus(Status status) {
        this.status = status;
    }
}
