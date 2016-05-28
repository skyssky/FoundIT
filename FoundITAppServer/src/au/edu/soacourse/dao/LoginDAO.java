package au.edu.soacourse.dao;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginDAO {
	String DBPathString;
	public LoginDAO(String string){
		this.DBPathString = string;
	}
	
	public void init(){
		File DB = new File(DBPathString);
		DB.mkdirs();
	}
	
	public HashMap<String,String> login(String userNameString, String pwdString) {
		String userName = userNameString;
		String pwd = pwdString;
		String role = "";
		String userID = "";
		boolean authorization = false;
		String userDBPath = DBPathString+"users.json";
		HashMap<String, String> meta = new HashMap<String, String>();
		try{
			File userDB = new File(userDBPath);				
			userDB.createNewFile();
			List<String> lines = Files.readAllLines(userDB.toPath(), Charset.defaultCharset());
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				sb.append(line+"\n");
			}
			JSONObject users = new JSONObject(sb.toString());
			JSONArray usersArray = users.getJSONArray("users");
			for(int i = 0; i < usersArray.length(); i++){
				JSONObject user = usersArray.getJSONObject(i);
				String userName_ = user.getString("userName");
				String pwd_ = user.getString("pwd");
				//System.out.println(userName_+" "+pwd_);
				if(!authorization && userName.equals(userName_) && pwd.equals(pwd_)){
					authorization = true;
					role = user.getString("role");
					userID = user.getString("userID");
					meta.put("userName", userName);
					meta.put("role", role);
					meta.put("userID", userID);
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		if(authorization){
			return meta;
		}
		return null;
	}
}
