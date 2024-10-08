//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SupplyProblemType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SupplyProblemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}SupplyProblemFields"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplyProblemType", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", propOrder = {
    "expectedEndOn",
    "reportedBy",
    "reportedOn",
    "contactName",
    "contactMail",
    "contactCompany",
    "phone",
    "reason",
    "additionalInformation",
    "impact",
    "limitedAvailability"
})
@XmlSeeAlso({
    AddSupplyProblemType.class
})
public class SupplyProblemType {

    @XmlElement(name = "ExpectedEndOn", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar expectedEndOn;
    @XmlElement(name = "ReportedBy", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected String reportedBy;
    @XmlElement(name = "ReportedOn", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar reportedOn;
    @XmlElement(name = "ContactName", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected String contactName;
    @XmlElement(name = "ContactMail", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected String contactMail;
    @XmlElement(name = "ContactCompany", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected String contactCompany;
    @XmlElement(name = "Phone", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected String phone;
    @XmlElement(name = "Reason", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected TextType reason;
    @XmlElement(name = "AdditionalInformation", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected TextType additionalInformation;
    @XmlElement(name = "Impact", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Text255Type impact;
    @XmlElement(name = "LimitedAvailability", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Boolean limitedAvailability;

    /**
     * Gets the value of the expectedEndOn property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getExpectedEndOn() {
        return expectedEndOn;
    }

    /**
     * Sets the value of the expectedEndOn property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setExpectedEndOn(XMLGregorianCalendar value) {
        this.expectedEndOn = value;
    }

    /**
     * Gets the value of the reportedBy property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReportedBy() {
        return reportedBy;
    }

    /**
     * Sets the value of the reportedBy property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReportedBy(String value) {
        this.reportedBy = value;
    }

    /**
     * Gets the value of the reportedOn property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getReportedOn() {
        return reportedOn;
    }

    /**
     * Sets the value of the reportedOn property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setReportedOn(XMLGregorianCalendar value) {
        this.reportedOn = value;
    }

    /**
     * Gets the value of the contactName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the value of the contactName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setContactName(String value) {
        this.contactName = value;
    }

    /**
     * Gets the value of the contactMail property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getContactMail() {
        return contactMail;
    }

    /**
     * Sets the value of the contactMail property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setContactMail(String value) {
        this.contactMail = value;
    }

    /**
     * Gets the value of the contactCompany property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getContactCompany() {
        return contactCompany;
    }

    /**
     * Sets the value of the contactCompany property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setContactCompany(String value) {
        this.contactCompany = value;
    }

    /**
     * Gets the value of the phone property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the reason property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setReason(TextType value) {
        this.reason = value;
    }

    /**
     * Gets the value of the additionalInformation property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * Sets the value of the additionalInformation property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setAdditionalInformation(TextType value) {
        this.additionalInformation = value;
    }

    /**
     * Gets the value of the impact property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getImpact() {
        return impact;
    }

    /**
     * Sets the value of the impact property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setImpact(Text255Type value) {
        this.impact = value;
    }

    /**
     * Gets the value of the limitedAvailability property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isLimitedAvailability() {
        return limitedAvailability;
    }

    /**
     * Sets the value of the limitedAvailability property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setLimitedAvailability(Boolean value) {
        this.limitedAvailability = value;
    }

}
