package au.edu.unsw.soacourse.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;


@XmlRootElement(name = "jobAlerts")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobAlerts {

	@XmlElement(name="jobAlert")
    private List<JobAlert> jobAlerts = new ArrayList<JobAlert>();

	public JobAlerts() {
		
	}
	
	public JobAlerts(List<JobAlert> jobAlerts) {
		this.jobAlerts = jobAlerts;
	}
	
	public List<JobAlert> getJobAlerts() {
        return this.jobAlerts;
    }

	public void setJobAlerts(List<JobAlert> jobAlerts) {
        this.jobAlerts = jobAlerts;
    }
}
