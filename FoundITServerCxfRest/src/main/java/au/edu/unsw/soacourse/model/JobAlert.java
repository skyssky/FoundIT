package au.edu.unsw.soacourse.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "jobAlert")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobAlert {
	
	@XmlElement(name = "guid")
	private String guid;		// job id
	
	@XmlElement(name = "position")
	private String position;		// position type
	
	@XmlElement(name = "detail")
	private String detail;	// job details/detail

	@XmlElement(name = "link")
	private String link;
	
	@XmlElement(name = "pubDate")
	private String pubDate;

	public JobAlert() {
		
    }
	
	public JobAlert(String position, String detail, String guid, String link, String pubDate) {
		this.position = position;
		this.detail = detail;
		this.guid = guid;
		this.link = link;
		this.pubDate = pubDate; 
    }
    
    public String getPosition() {
        return this.position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
 
    public String getDetail() {
        return this.detail;
    }
    
    public void setDetail(String detail) {
        this.detail = detail;
    }
 
    public String getGuid() {
        return this.guid;
    }
    
    public void setGuid(String guid) {
        this.position = guid;
    }
    
    public String getLink() {
        return this.link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
    
    public String getPubDate() {
        return this.pubDate;
    }
    
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
