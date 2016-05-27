package au.edu.unsw.soacourse.auxiliary;

public class Paths {
	
	private final String rootRestServer = System.getProperty("catalina.home") + "/webapps/ROOT/FoundIT/RestServer/";
	private final String appPath = rootRestServer + "application/";
	private final String teamPath = rootRestServer + "team/";
	private final String jobPath = rootRestServer + "job/";
	private final String companyPath = rootRestServer + "company/";
	private final String reviewPath = rootRestServer + "review/";
	private final String userPath = rootRestServer + "user/";
	private final String idcounterPath = rootRestServer + "idCounter/";
	
	private final String rootDataService = System.getProperty("catalina.home") + "/webapps/ROOT/FoundIT/DataService/";
	private final String jobalertPath = rootDataService;
    
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
    
    public String getJobalertPath() {
        return this.jobalertPath;
    }
    
    public String getIdcounterPath() {
        return this.idcounterPath;
    }

}
