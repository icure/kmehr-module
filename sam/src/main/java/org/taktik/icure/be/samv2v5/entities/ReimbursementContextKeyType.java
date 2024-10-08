//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v5.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReimbursementContextKeyType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ReimbursementContextKeyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="deliveryEnvironment" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}DeliveryEnvironmentType" />
 *       &lt;attribute name="code" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}DmppCodeType" />
 *       &lt;attribute name="codeType" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}DmppCodeTypeType" />
 *       &lt;attribute name="legalReferencePath" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}LegalReferencePathType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReimbursementContextKeyType", namespace = "urn:be:fgov:ehealth:samws:v2:reimbursement:submit")
@XmlSeeAlso({
    ReimbursementContextType.class
})
public class ReimbursementContextKeyType {

    @XmlAttribute(name = "deliveryEnvironment", required = true)
    protected DeliveryEnvironmentType deliveryEnvironment;
    @XmlAttribute(name = "code", required = true)
    protected String code;
    @XmlAttribute(name = "codeType", required = true)
    protected DmppCodeTypeType codeType;
    @XmlAttribute(name = "legalReferencePath", required = true)
    protected String legalReferencePath;

    /**
     * Gets the value of the deliveryEnvironment property.
     *
     * @return
     *     possible object is
     *     {@link DeliveryEnvironmentType }
     *
     */
    public DeliveryEnvironmentType getDeliveryEnvironment() {
        return deliveryEnvironment;
    }

    /**
     * Sets the value of the deliveryEnvironment property.
     *
     * @param value
     *     allowed object is
     *     {@link DeliveryEnvironmentType }
     *
     */
    public void setDeliveryEnvironment(DeliveryEnvironmentType value) {
        this.deliveryEnvironment = value;
    }

    /**
     * Gets the value of the code property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the codeType property.
     *
     * @return
     *     possible object is
     *     {@link DmppCodeTypeType }
     *
     */
    public DmppCodeTypeType getCodeType() {
        return codeType;
    }

    /**
     * Sets the value of the codeType property.
     *
     * @param value
     *     allowed object is
     *     {@link DmppCodeTypeType }
     *
     */
    public void setCodeType(DmppCodeTypeType value) {
        this.codeType = value;
    }

    /**
     * Gets the value of the legalReferencePath property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLegalReferencePath() {
        return legalReferencePath;
    }

    /**
     * Sets the value of the legalReferencePath property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLegalReferencePath(String value) {
        this.legalReferencePath = value;
    }

}
