package au.edu.soacourse.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

public class RegisterDAO {
	String DBPathString;
	public RegisterDAO(String string){
		this.DBPathString = string;
	}
	
	public void init(){
		File DB = new File(DBPathString);
		DB.mkdirs();
	}
	
	public boolean createAccount(String userNameString, String pwdString, String roleString) {
		try{
			String userName = userNameString;
			String pwd = pwdString;
			String role = roleString;
			String userId = UUID.randomUUID().toString();
			String userDBPath = DBPathString+"users.json";
			File userDB = new File(userDBPath);				
			userDB.createNewFile();
			List<String> lines = Files.readAllLines(userDB.toPath(), Charset.defaultCharset());
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				sb.append(line+"\n");
			}
			JSONObject users = new JSONObject(sb.toString());
			JSONObject user = new JSONObject();
			user.put("userID", userId);
			user.put("userName", userName);
			user.put("pwd", pwd);
			user.put("role", role);
			users.append("users", user);
			BufferedWriter userDBBufferedWriter = new BufferedWriter(new FileWriter(userDB));
			userDBBufferedWriter.write(users.toString(4));
			userDBBufferedWriter.flush();
			userDBBufferedWriter.close();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
