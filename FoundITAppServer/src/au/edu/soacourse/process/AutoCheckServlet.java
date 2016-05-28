package au.edu.soacourse.process;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Servlet implementation class AutoCheckServlet
 */
@WebServlet("/AutoCheckServlet")
public class AutoCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutoCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO input is hard-coded for testing, shall change it to request.param...
		String input = "1b2c3d4e";
		
		try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            // Send SOAP Message to SOAP Server
            String url = "http://localhost:6060/ode/processes/AutoCheck/process?input=" + input;
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);
            // Process the SOAP Response
            printSOAPResponse(soapResponse);

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
		
		
	}
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
    private static SOAPMessage createSOAPRequest() throws Exception {
    	
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
        
        String input = "abcd";

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
    private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {    	
    	// Read SOAP message
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        
        // Save SOAP message to catalina.home/webapps/ROOT/FoundIT/FoundITApp/autocheck/autocheck-soap.xml
        // TODO path is hard-coded
        String path = "/Users/zenglinwang/Documents/Workspace/COMP9322/cs9322-Prac/apache-tomcat-8.0.32/webapps/ROOT/FoundIT/FoundITApp/autocheck";
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
    }
    
    /**
     * Starting point for the SAAJ - SOAP Client Testing
     */
    public static void main(String args[]) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            String url = "http://localhost:6060/ode/processes/AutoCheck/process?input=" + "abcd";
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

            // Process the SOAP Response
            printSOAPResponse(soapResponse);

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
    }
}
