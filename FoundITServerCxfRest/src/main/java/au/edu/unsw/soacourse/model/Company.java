package au.edu.unsw.soacourse.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Company {
	private String profileId;
	private String managerId;
	private String name;
	private String type;
	private String detail;
	
	public Company() {
		
    }
	
	public Company(String profileId, String name, String type, String detail) {
		this.profileId = profileId;
		this.name = name;
		this.type = type;
		this.detail = detail;
    }
	
	public String getName() {
        return this.name;
    }
	
	@XmlElement
    public void setName(String name) {
        this.name = name;
    }
    
    public String getProfileId() {
        return this.profileId;
    }
    
    @XmlElement
    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
    
    public String getDetail() {
        return this.detail;
    }
    
    @XmlElement
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public String getType() {
        return this.type;
    }
    
    @XmlElement
    public void setType(String type) {
        this.type = type;
    }
}
