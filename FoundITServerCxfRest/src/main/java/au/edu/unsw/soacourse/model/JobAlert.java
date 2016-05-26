package au.edu.unsw.soacourse.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "jobAlert")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobAlert {
	
//	private String guid;		// job id
	
	@XmlElement(name = "position")
	private String position;		// position type
	
	@XmlElement(name = "detail")
	private String detail;	// job details/detail

//	private String link;
//	private String pubDate;

	public JobAlert() {
		
    }
	
	public JobAlert(String position, String detail) {
		this.position = position;
		this.detail = detail;
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
 
}
