
package au.edu.unsw.soacourse.loandefinitions;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the au.edu.unsw.soacourse.loandefinitions package. 
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

    private final static QName _CreditInformationMessage_QNAME = new QName("http://soacourse.unsw.edu.au/loandefinitions", "creditInformationMessage");
    private final static QName _ApprovalMessage_QNAME = new QName("http://soacourse.unsw.edu.au/loandefinitions", "approvalMessage");
    private final static QName _RiskAssessmentMessage_QNAME = new QName("http://soacourse.unsw.edu.au/loandefinitions", "riskAssessmentMessage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: au.edu.unsw.soacourse.loandefinitions
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LoanInputType }
     * 
     */
    public LoanInputType createLoanInputType() {
        return new LoanInputType();
    }

    /**
     * Create an instance of {@link ApprovalType }
     * 
     */
    public ApprovalType createApprovalType() {
        return new ApprovalType();
    }

    /**
     * Create an instance of {@link RiskType }
     * 
     */
    public RiskType createRiskType() {
        return new RiskType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoanInputType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soacourse.unsw.edu.au/loandefinitions", name = "creditInformationMessage")
    public JAXBElement<LoanInputType> createCreditInformationMessage(LoanInputType value) {
        return new JAXBElement<LoanInputType>(_CreditInformationMessage_QNAME, LoanInputType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApprovalType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soacourse.unsw.edu.au/loandefinitions", name = "approvalMessage")
    public JAXBElement<ApprovalType> createApprovalMessage(ApprovalType value) {
        return new JAXBElement<ApprovalType>(_ApprovalMessage_QNAME, ApprovalType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RiskType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soacourse.unsw.edu.au/loandefinitions", name = "riskAssessmentMessage")
    public JAXBElement<RiskType> createRiskAssessmentMessage(RiskType value) {
        return new JAXBElement<RiskType>(_RiskAssessmentMessage_QNAME, RiskType.class, null, value);
    }

}
