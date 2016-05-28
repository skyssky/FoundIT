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

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.NodeList;

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
		String input = "1b2c3d4e";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://localhost:6060/ode/processes/AutoCheck/process?input=" + input);
		httpget.setHeader("Content-Type", "application/xml; charset=utf-8");
		CloseableHttpResponse hresponse = httpclient.execute(httpget);
		HttpEntity entity = hresponse.getEntity();
		String entityString = EntityUtils.toString(entity);
		System.out.println(entityString);
		hresponse.close();
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

    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://autocheck";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("aut", serverURI);

        /*
        Constructed SOAP Request Message:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:example="http://ws.cdyne.com/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <example:VerifyEmail>
                    <example:email>mutantninja@gmail.com</example:email>
                    <example:LicenseKey>123</example:LicenseKey>
                </example:VerifyEmail>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        
        
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
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        
        String path = "/Users/zenglinwang/Documents/Workspace/COMP9322/cs9322-Prac/apache-tomcat-8.0.32/webapps/ROOT/FoundIT/FoundITApp/";
        File resultFile = new File(path + "autocheck-result.xml");
        if (!resultFile.exists()) {
        	resultFile.createNewFile();
        }
        
        System.out.print("\nResponse SOAP Message = ");
//        StreamResult result = new StreamResult(System.out);
        StreamResult result = new StreamResult(resultFile);
        transformer.transform(sourceContent, result);
        
        
        File stylesheet = new File(path + "autocheck.xsl");
        File datafile = new File(path + "autocheck-result.xml");
        File outFile = new File(path + "autocheck-xsl-result.xml");
        TransformerFactory factory2 = TransformerFactory.newInstance();
	    StreamSource xslStream = new StreamSource(stylesheet);
	    Transformer transformer2 = factory2.newTransformer(xslStream);
        StreamSource in = new StreamSource(datafile);
        StreamResult out = new StreamResult(outFile);
        transformer2.transform(sourceContent, result);
        transformer2.transform(in, out);
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
