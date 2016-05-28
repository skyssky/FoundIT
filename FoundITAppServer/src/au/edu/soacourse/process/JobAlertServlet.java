package au.edu.soacourse.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.commons.digester.rss.Channel;
import org.apache.commons.digester.rss.Item;
import org.apache.commons.digester.rss.RSSDigester;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		System.out.println("JobAlertServlet......doGet");
		
		String keyword = request.getParameter("keyword");
		String sort_by = request.getParameter("sort_by");
		
		// TODO For Testing
		keyword = "Java";
		sort_by = "jobtitle";
		
		System.out.println("keyword = " + keyword);
		System.out.println("sort_by = " + sort_by);
		
		String uri = getServletContext().getInitParameter("RestfulURL")+"jobalerts?keyword=" + keyword + "&sort_by=" + sort_by;
		HttpResponse hresponse = reqOp.makeGetRequest(uri);
		
		// TODO Hardcode the path here, need to FIX
		String jobalertFilename = getServletContext().getInitParameter("DBPath")+"alert/jobalert.xml";
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            instream.close();
	        }
	    }

		
		// TODO Send RSS/Atom feed to user's email, i.e., jobalertFile
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
            mailer.sendRssEmail(host, port, mailFrom, password, mailTo,
                    subject, message);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }    
			      
		getServletContext().getRequestDispatcher("/FoundIT/FoundITApp/temp/jobalert.xml").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("JobAlertServlet......doPost");

		
		getServletContext().getRequestDispatcher("/jobalert.jsp").forward(
				request, response);
	}

}
