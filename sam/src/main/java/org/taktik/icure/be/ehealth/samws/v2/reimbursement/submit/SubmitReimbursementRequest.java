//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.reimbursement.submit;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * <p>Java class for SubmitReimbursementRequestType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SubmitReimbursementRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReimbursementContext" type="{urn:be:fgov:ehealth:samws:v2:reimbursement:submit}SubmitReimbursementContextType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubmitReimbursementRequestType", propOrder = {
    "reimbursementContext"
})
@XmlRootElement(name = "SubmitReimbursementRequest")
public class SubmitReimbursementRequest
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "ReimbursementContext", required = true)
    protected SubmitReimbursementContextType reimbursementContext;

    /**
     * Gets the value of the reimbursementContext property.
     *
     * @return
     *     possible object is
     *     {@link SubmitReimbursementContextType }
     *
     */
    public SubmitReimbursementContextType getReimbursementContext() {
        return reimbursementContext;
    }

    /**
     * Sets the value of the reimbursementContext property.
     *
     * @param value
     *     allowed object is
     *     {@link SubmitReimbursementContextType }
     *
     */
    public void setReimbursementContext(SubmitReimbursementContextType value) {
        this.reimbursementContext = value;
    }

}
