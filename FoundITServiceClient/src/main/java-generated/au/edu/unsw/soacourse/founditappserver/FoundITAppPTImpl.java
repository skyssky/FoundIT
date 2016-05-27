package au.edu.unsw.soacourse.founditappserver;

import au.edu.unsw.soacourse.founditappdefinitions.InputType;
import au.edu.unsw.soacourse.founditappdefinitions.ObjectFactory;

import javax.jws.WebService;

@WebService(endpointInterface = "au.edu.unsw.soacourse.founditappserver.FoundITAppPT")
public class FoundITAppPTImpl implements FoundITAppPT {

	ObjectFactory objectFactory = new ObjectFactory();
	
	@Override
	public InputType check(InputType inputreq) {
		
		InputType response = objectFactory.createInputType();
		response.setInput(inputreq.getInput());
		
		return response;
	}

}

