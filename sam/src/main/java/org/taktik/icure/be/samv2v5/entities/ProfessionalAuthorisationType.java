//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v5.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProfessionalAuthorisationType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ProfessionalAuthorisationType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:chapteriv:submit}ProfessionalAuthorisationKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:chapteriv:submit}ProfessionalAuthorisationFields"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfessionalAuthorisationType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", propOrder = {
    "createdTimestamp",
    "createdUserId",
    "professionalCV",
    "purchasingAdvisorName",
    "modificationStatus"
})
public class ProfessionalAuthorisationType
    extends ProfessionalAuthorisationKeyType
{

    @XmlElement(name = "CreatedTimestamp", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdTimestamp;
    @XmlElement(name = "CreatedUserId", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", required = true)
    protected String createdUserId;
    @XmlElement(name = "ProfessionalCV", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String professionalCV;
    @XmlElement(name = "PurchasingAdvisorName", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String purchasingAdvisorName;
    @XmlElement(name = "ModificationStatus", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", required = true)
    protected String modificationStatus;

    /**
     * Gets the value of the createdTimestamp property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getCreatedTimestamp() {
        return createdTimestamp;
    }

    /**
     * Sets the value of the createdTimestamp property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setCreatedTimestamp(XMLGregorianCalendar value) {
        this.createdTimestamp = value;
    }

    /**
     * Gets the value of the createdUserId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCreatedUserId() {
        return createdUserId;
    }

    /**
     * Sets the value of the createdUserId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCreatedUserId(String value) {
        this.createdUserId = value;
    }

    /**
     * Gets the value of the professionalCV property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getProfessionalCV() {
        return professionalCV;
    }

    /**
     * Sets the value of the professionalCV property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setProfessionalCV(String value) {
        this.professionalCV = value;
    }

    /**
     * Gets the value of the purchasingAdvisorName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPurchasingAdvisorName() {
        return purchasingAdvisorName;
    }

    /**
     * Sets the value of the purchasingAdvisorName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPurchasingAdvisorName(String value) {
        this.purchasingAdvisorName = value;
    }

    /**
     * Gets the value of the modificationStatus property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getModificationStatus() {
        return modificationStatus;
    }

    /**
     * Sets the value of the modificationStatus property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setModificationStatus(String value) {
        this.modificationStatus = value;
    }

}
