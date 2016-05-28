package au.edu.unsw.soacourse.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	
	private String username;
	private String profileId;
	private String detail;
	private String skill;
	private String experience;
	
	public User() {
		
    }
	
	public User(String username, String profileId, String detail, String skill, String experience) {
		this.username = username;
		this.profileId = profileId;
		this.detail = detail;
		this.skill = skill;
		this.experience = experience;
    }
	
	public String getUsername() {
        return this.username;
    }
	
	@XmlElement
    public void setUsername(String username) {
        this.username = username;
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
    
    public String getSkill() {
        return this.skill;
    }
    
    @XmlElement
    public void setSkill(String skill) {
        this.skill = skill;
    }
    
    public String getExperience() {
        return this.experience;
    }
    
    @XmlElement
    public void setExperience(String experience) {
        this.experience = experience;
    }
    
}
