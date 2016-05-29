package au.edu.soacourse.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import au.edu.soacourse.httprequest.HttpRequestOp;
import au.edu.soacourse.mail.MailSender;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/JobAlertServlet")
public class JobAlertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	HttpRequestOp reqOp = new HttpRequestOp();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobAlertServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		System.out.println("JobAlertServlet......doGet");
		
		
		String keyword = request.getParameter("keyword");
		String sort_by = request.getParameter("sort_by");
		
		// For Testing
//		keyword = "Java";
//		sort_by = "jobtitle";
		
		System.out.println("keyword = " + keyword);
		System.out.println("sort_by = " + sort_by);
		
		String serviceURLString = getServletContext().getInitParameter("RestfulURL")+"jobalerts?keyword=" + keyword + "&sort_by=" + sort_by;
		URL serviceURL = new URL(serviceURLString);
		URLConnection connection = serviceURL.openConnection();
		connection.setRequestProperty("Accept", "application/json");
		int responseCode = ((HttpURLConnection) connection).getResponseCode();
		String responseBody = "";
		if(responseCode == 200){
			InputStream serviceResponse = connection.getInputStream();	
			try (Scanner scanner = new Scanner(serviceResponse)) {
			    responseBody = scanner.useDelimiter("\\A").next();
			    System.out.println("2 responseBody ===>" + responseBody);
			}
			response.setContentType("application/json");
			java.io.PrintWriter out = response.getWriter( );
			out.print(responseBody);
			out.flush();
			out.close();
		}
		
		// Send RSS/Atom feed to user's email, i.e., jobalertFile
		 // SMTP server information
        String host = "smtp.live.com";
        String port = "25";	// 25 or 465
        String mailFrom = "skysskyTest@hotmail.com";
        String password = "!Q@W#E$R";
 
        // outgoing message information
        String mailTo = "skysskyTest@hotmail.com";
        String subject = "HTML Test: Hello my friend";
 
        // message contains HTML markups
        String message = responseBody;
        
        MailSender mailer = new MailSender();
        try {
            mailer.sendRssEmail(host, port, mailFrom, password, mailTo, subject, message);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }    
		// TODO give a feedback to client: email sent
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("JobAlertServlet......doPost");
		
		String keyword = request.getParameter("keyword");
		String sort_by = request.getParameter("sort_by");
		
		System.out.println("keyword = " + keyword);
		System.out.println("sort_by = " + sort_by);
		
		String uri = getServletContext().getInitParameter("RestfulURL")+"jobalerts?keyword=" + keyword + "&sort_by=" + sort_by;
		HttpResponse hresponse = reqOp.makeGetRequest(uri);
		
		String jobalertFilename = getServletContext().getInitParameter("ALERTPath")+"jobalert.xml";
		String entityString = null;
		
		HttpEntity entity = hresponse.getEntity();
	    if (entity != null) {
	        InputStream instream = entity.getContent();
	        entityString = EntityUtils.toString(entity);
	        try {
//		            System.out.println(EntityUtils.toString(entity));
	            File jobalertFile = new File(jobalertFilename);
	            if (!jobalertFile.exists()) {
	            	jobalertFile.createNewFile();
	            }
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document document = builder.parse(new InputSource(new StringReader(entityString)));
	            
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            DOMSource source = new DOMSource(document);
	            StreamResult streamResult =  new StreamResult(jobalertFile);
	            transformer.transform(source, streamResult);
	            
	        } catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			} finally {
	            instream.close();
	        }
	    }
		
		// Send RSS/Atom feed to user's email, i.e., jobalertFile
		 // SMTP server information
        String host = "smtp.live.com";
        String port = "25";	// 25 or 465
        String mailFrom = "skysskyTest@hotmail.com";
        String password = "!Q@W#E$R";
 
        // outgoing message information
        String mailTo = "skysskyTest@hotmail.com";
        String subject = "HTML Test: Hello my friend";
 
        // message contains HTML markups
        String message = entityString;
        
        MailSender mailer = new MailSender();
        try {
            mailer.sendRssEmail(host, port, mailFrom, password, mailTo, subject, message);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }    

        response.setContentType("application/xml");
		java.io.PrintWriter out = response.getWriter( );
		out.print(entityString);
		out.flush();
		out.close();	
		
		// TODO give a feedback to client: email sent
	}

}
