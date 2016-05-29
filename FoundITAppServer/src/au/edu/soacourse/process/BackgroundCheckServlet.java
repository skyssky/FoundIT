package au.edu.soacourse.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class BackgroundCheckServlet
 */
@WebServlet("/BackgroundCheckServlet")
public class BackgroundCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BackgroundCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// IN : list of candidates in JSON string
		// OUT: reject candidates' applications who have not passed the AutoCheck 
		
//		String candidatesStr = request.getParameter("candidates");				// TODO might need to change
//		candidatesStr = "[{\"address\": null,\"name\": \"Boy\",\"userId\": \"052a2e06-c5d2-465e-9816-114a523206df\","
//		+ "\"skill\": \"Java\",\"experience\": \"No\",\"education\": \"UNSW BE\","
//		+ "\"position\": \"No One\",\"license\": null}, "
//		+ "{\"address\": null,\"name\": \"Boy\",\"userId\": \"052a2e06-c5d2-465e-9816-114a523206df\","
//		+ "\"skill\": \"Java\",\"experience\": \"No\",\"education\": \"UNSW BE\","
//		+ "\"position\": \"No One\",\"license\": null}]";
//		
//		boolean checkResult = false;
//		String license = null, address = null;
//		JSONObject candidatesJson = new JSONObject(candidatesStr);
//		JSONArray candidatesIdentified = new JSONArray();
//		JSONArray candidatesArray = candidatesJson.getJSONArray("0");
//		for (int i = 0; i < candidatesArray.length(); i++) {
//		    // Get license and address of candidate
//		    JSONObject cand = candidatesArray.getJSONObject(i); 
//		    license = cand.getString("license");
//		    address = cand.getString("address");
//		    // Check identity by license
//		    checkResult = runAutocheck(license);
//		    if (!checkResult) {
//		    	checkResult = runAutocheck(address);
//		    }
//	    	// UPDATE THIS CANDIDATE's Application status to "REJECTED" ==> terminate ==> reject app automatically
//		    if (!checkResult) {
//		    	rejectApplication(response, cand);
//		    } else {
//		    	candidatesIdentified.put(cand);
//		    }
//		}
//		// return list of identified candidates as String
//		String candidatesIdentifiedStr = candidatesIdentified.toString();
//		response.setContentType("application/json");
//		java.io.PrintWriter out = response.getWriter( );
//		out.print(candidatesIdentifiedStr);
//		out.flush();
//		out.close();
//		
//		String serviceURLString = getServletContext().getInitParameter("RestfulURL") + "applications/";
//		URL serviceURL = new URL(serviceURLString);
//		HttpURLConnection connection2 = (HttpURLConnection) serviceURL.openConnection();
//		connection2.setRequestMethod("PUT");
//		connection2.setRequestProperty("Content-Type","application/json");
//		connection2.setDoOutput(true);
//		OutputStreamWriter out2 = new OutputStreamWriter(connection2.getOutputStream());
//		//System.out.println("UPDATE\n"+profile.toString());
//        out2.write(candidatesJson.toString());
//        out2.flush();
//        out2.close();
//        BufferedReader in = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
//        in.close();
//		
//		candidatesJson.getString("")
	}
	
	private void rejectApplication(HttpServletResponse response, JSONObject candidate, String jobId) throws IOException {
		// Get application by userId
		String serviceURLString = getServletContext().getInitParameter("RestfulURL") + "applications?userId=" + candidate.getString("userId") + "&jobId=" + jobId;
		URL serviceURL = new URL(serviceURLString);
		URLConnection connection = serviceURL.openConnection();
		connection.setRequestProperty("Accept", "application/json");
		int responseCode = ((HttpURLConnection) connection).getResponseCode();
		if(responseCode == 200){
			InputStream serviceResponse = connection.getInputStream();
			String responseBody = "";		
			try (Scanner scanner = new Scanner(serviceResponse)) {
			    responseBody = scanner.useDelimiter("\\A").next();
			    System.out.println("2 responseBody ===>" + responseBody);
			}
			response.setContentType("application/json");
			
			// Update application status to "REJECTED"			
			JSONArray appArray = new JSONArray(responseBody); 
			JSONObject app = (JSONObject) appArray.get(0);
			app.put("status", "REJECTED");
			System.out.println("app ====> PUT ==> \n" + app.toString());
			String serviceURLString2 = getServletContext().getInitParameter("RestfulURL") + "applications/" + app.getString("appId");
			URL serviceURL2 = new URL(serviceURLString2);
			HttpURLConnection connection2 = (HttpURLConnection) serviceURL2.openConnection();
			connection2.setRequestMethod("PUT");			
			connection2.setRequestProperty("Content-Type","application/json");
			connection2.setDoOutput(true);
			OutputStreamWriter out2 = new OutputStreamWriter(connection2.getOutputStream());
			//System.out.println("UPDATE\n"+profile.toString());
            out2.write(app.toString());
            out2.flush();
            out2.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
            in.close();
		} else {
			// TODO FAILED TO UPDATE the status
		}
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String candidatesStr = request.getParameter("candidates");				// TODO might need to change
		BufferedReader br = new BufferedReader(new  InputStreamReader(request.getInputStream()));
	    String json = "";
	    if(br != null){
	        json = br.readLine();
	    }
	    System.out.println("=====> json.toString() = " + json.toString());
		//System.out.println("=====> json.toString() = " + candidatesStr);
		JSONObject returnObject = new JSONObject(json); // this parses the json
		JSONArray candidatesArray = returnObject.getJSONArray("candidates");
		String jobId = returnObject.getString("jobId");
//		JSONArray candidatesArray = candidatesObject.getJSONArray("candidates");
		boolean checkResult = false;
		String license = null, address = null;
		
		///JSONArray candidatesArray = new JSONArray(jb.toString());
		JSONArray candidatesIdentified = new JSONArray();
		//JSONArray candidatesArray = candidatesJson.getJSONArray("0");
		for (int i = 0; i < candidatesArray.length(); i++) {
		    // Get license and address of candidate
		    JSONObject cand = candidatesArray.getJSONObject(i); 
		    license = cand.getString("license");
		    address = cand.getString("address");
		    // Check identity by license
		    checkResult = runAutocheck(license);
		    if (!checkResult) {
		    	checkResult = runAutocheck(address);
		    }
	    	// UPDATE THIS CANDIDATE's Application status to "REJECTED" ==> terminate ==> reject app automatically
		    if (!checkResult) {
		    	rejectApplication(response, cand, jobId);
		    } else {
		    	candidatesIdentified.put(cand);
		    }
		}
		// return list of identified candidates as String
		String candidatesIdentifiedStr = candidatesIdentified.toString();
		response.setContentType("application/json");
		java.io.PrintWriter out = response.getWriter( );
		out.print(candidatesIdentifiedStr);
		out.flush();
		out.close();
	}
	
	
	private boolean runAutocheck(String input) {
		boolean result = false;
		try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            // Send SOAP Message to SOAP Server
            String url = "http://localhost:6060/ode/processes/AutoCheck/process?input=" + input;
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(input), url);
            // Process the SOAP Response
            result = printSOAPResponse(soapResponse);
            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
		return result;
	}
	
private static SOAPMessage createSOAPRequest(String input) throws Exception {
    	
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        String serverURI = "http://autocheck";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("aut", serverURI);

        /*
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:aut="http://autocheck">
		   <soapenv:Header/>
		   <soapenv:Body>
		      <aut:AutoCheckRequest>
		         <aut:input>?</aut:input>
		      </aut:AutoCheckRequest>
		   </soapenv:Body>
		</soapenv:Envelope>
         */
        
//        String input = "abcd";

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("AutoCheckRequest", "aut");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("input", "aut");
        soapBodyElem1.addTextNode(input);
        
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "AutoCheckRequest");
        
        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

    /**
     * Method used to print the SOAP Response
     */
    private boolean printSOAPResponse(SOAPMessage soapResponse) throws Exception {    	
    	// Read SOAP message
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        
        // Save SOAP message to catalina.home/webapps/ROOT/FoundIT/FoundITApp/autocheck/autocheck-soap.xml
        // TODO path is hard-coded
        String path = getServletContext().getInitParameter("ACPath");
        File resultFile = new File(path + "autocheck-soap.xml");
        if (!resultFile.exists()) {
        	resultFile.createNewFile();
        }
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(resultFile);
        transformer.transform(sourceContent, result);
        
        // Extarct the result from autocheck soap message and save to autocheck-result.xml
        File stylesheet = new File(path + "autocheck.xsl");
        File datafile = new File(path + "autocheck-soap.xml");
        File outFile = new File(path + "autocheck-result.xml");
        TransformerFactory factory2 = TransformerFactory.newInstance();
	    StreamSource xslStream = new StreamSource(stylesheet);
	    Transformer transformer2 = factory2.newTransformer(xslStream);
        StreamSource in = new StreamSource(datafile);
        StreamResult out = new StreamResult(outFile);
        transformer2.transform(in, out);
        
        // Read result from autocheck-result.xml, i.e., true or false as string
        String resultStr = FileUtils.readFileToString(outFile, "utf-8");
        resultStr = resultStr.trim();
        System.out.print("\nresultStr = *" + resultStr + "*");
        
        if (resultStr.toLowerCase().equals("yes")) {
        	return true;
        }
        return false;
    }

}
