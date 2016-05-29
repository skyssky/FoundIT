package au.edu.unsw.soacourse.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	
	private String userId;
	private String skill;
	private String experience;
	private String name;
	private String position;
	private String education;
	private String address;
	private String license;
	
	public User() {
		
    }
	
	public User(String userId, String skill, String experience, String name, String position, String education, String address, String license) {
		this.userId = userId;
		this.skill = skill;
		this.experience = experience;
		this.name = name;
		this.position = position;
		this.education = education;
		this.address = address;
		this.license = license;
    }
	
	public String getUserId() {
        return this.userId;
    }
	
	@XmlElement
    public void setUserId(String userId) {
        this.userId = userId;
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
    
    
    
    public String getName() {
        return this.name;
    }
    
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPosition() {
        return this.position;
    }
    
    @XmlElement
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getEducation() {
        return this.education;
    }
    
    @XmlElement
    public void setEducation(String education) {
        this.education = education;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    @XmlElement
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getLicense() {
        return this.license;
    }
    
    @XmlElement
    public void setLicense(String license) {
        this.license = license;
    }
}
