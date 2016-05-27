
package au.edu.unsw.soacourse.founditappdefinitions;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the au.edu.unsw.soacourse.founditappdefinitions package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InputMessage_QNAME = new QName("http://soacourse.unsw.edu.au/founditappdefinitions", "inputMessage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: au.edu.unsw.soacourse.founditappdefinitions
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InputType }
     * 
     */
    public InputType createInputType() {
        return new InputType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soacourse.unsw.edu.au/founditappdefinitions", name = "inputMessage")
    public JAXBElement<InputType> createInputMessage(InputType value) {
        return new JAXBElement<InputType>(_InputMessage_QNAME, InputType.class, null, value);
    }

}
