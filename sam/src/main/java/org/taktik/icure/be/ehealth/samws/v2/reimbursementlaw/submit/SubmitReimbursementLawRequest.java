//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.reimbursementlaw.submit;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * <p>Java class for SubmitReimbursementLawRequestType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SubmitReimbursementLawRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LegalBasis" type="{urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit}SubmitLegalBasisType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubmitReimbursementLawRequestType", propOrder = {
    "legalBasis"
})
@XmlRootElement(name = "SubmitReimbursementLawRequest")
public class SubmitReimbursementLawRequest
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "LegalBasis", required = true)
    protected SubmitLegalBasisType legalBasis;

    /**
     * Gets the value of the legalBasis property.
     *
     * @return
     *     possible object is
     *     {@link SubmitLegalBasisType }
     *
     */
    public SubmitLegalBasisType getLegalBasis() {
        return legalBasis;
    }

    /**
     * Sets the value of the legalBasis property.
     *
     * @param value
     *     allowed object is
     *     {@link SubmitLegalBasisType }
     *
     */
    public void setLegalBasis(SubmitLegalBasisType value) {
        this.legalBasis = value;
    }

}
