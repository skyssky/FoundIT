package au.edu.unsw.soacourse.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Job {
	
	public enum JobStatus { CREATED, OPEN, INREVIEW, PROCESSED, SENTINVITATION };	// status of this job posting
	
	private String jobId;		// job id
	private String link;		// a link to company profile
	private int salary;			// salary rate
	private String position;	// position type
	private String location;	// location
	private String detail;	// job details/detail
	private JobStatus status;

	public Job() {
		
    }
	
	public Job(String jobId, String link, int salary, String position, String location, String detail, JobStatus status) {
		this.jobId = jobId;
		this.link = link;
		this.salary = salary;
		this.position = position;
		this.location = location;
		this.detail = detail;
		this.status = status;
    }

    public String getJobId() {
        return this.jobId;
    }
    
    @XmlElement
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
    
    public String getLink() {
        return this.link;
    }
    
    @XmlElement
    public void setLink(String link) {
        this.link = link;
    }
    
    public int getSalary() {
        return this.salary;
    }
    
    @XmlElement
    public void setSalary(int salary) {
        this.salary = salary;
    }
    
    public String getPosition() {
        return this.position;
    }
    
    @XmlElement
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    @XmlElement
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getDetail() {
        return this.detail;
    }
    
    @XmlElement
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public JobStatus getStatus() {
        return this.status;
    }
    
    @XmlElement
    public void setStatus(JobStatus status) {
        this.status = status;
    }
}
