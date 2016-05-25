package au.edu.unsw.soacourse.auxiliary;

public class Paths {
	
	private final String root = System.getProperty("catalina.home") + "/webapps/server-database/";
	private final String appPath = root + "application/";
	private final String teamPath = root + "team/";
	private final String jobPath = root + "job/";
	private final String companyPath = root + "company/";
	private final String reviewPath = root + "review/";
	private final String userPath = root + "user/";
    
	public Paths() {
		
    }

    public String getAppPath() {
        return this.appPath;
    }
    
    public String getTeamPath() {
        return this.teamPath;
    }
    
    public String getJobPath() {
        return this.jobPath;
    }
    
    public String getCompanyPath() {
        return this.companyPath;
    }
    
    public String getReviewPath() {
        return this.reviewPath;
    }
    
    public String getUserPath() {
        return this.userPath;
    }
}
