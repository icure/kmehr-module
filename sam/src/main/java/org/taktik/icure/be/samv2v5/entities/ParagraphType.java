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
 * <p>Java class for ParagraphType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ParagraphType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:chapteriv:submit}ParagraphKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:chapteriv:submit}ParagraphFields"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParagraphType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", propOrder = {
    "createdTimestamp",
    "createdUserId",
    "keyStringNl",
    "keyStringFr",
    "agreementType",
    "processType",
    "legalReference",
    "publicationDate",
    "modificationDate",
    "processTypeOverrule",
    "paragraphVersion",
    "agreementTypePro",
    "modificationStatus"
})
public class ParagraphType
    extends ParagraphKeyType
{

    @XmlElement(name = "CreatedTimestamp", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdTimestamp;
    @XmlElement(name = "CreatedUserId", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", required = true)
    protected String createdUserId;
    @XmlElement(name = "KeyStringNl", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String keyStringNl;
    @XmlElement(name = "KeyStringFr", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String keyStringFr;
    @XmlElement(name = "AgreementType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String agreementType;
    @XmlElement(name = "ProcessType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected Integer processType;
    @XmlElement(name = "LegalReference", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String legalReference;
    @XmlElement(name = "PublicationDate", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar publicationDate;
    @XmlElement(name = "ModificationDate", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar modificationDate;
    @XmlElement(name = "ProcessTypeOverrule", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String processTypeOverrule;
    @XmlElement(name = "ParagraphVersion", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected int paragraphVersion;
    @XmlElement(name = "AgreementTypePro", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String agreementTypePro;
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
     * Gets the value of the keyStringNl property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getKeyStringNl() {
        return keyStringNl;
    }

    /**
     * Sets the value of the keyStringNl property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setKeyStringNl(String value) {
        this.keyStringNl = value;
    }

    /**
     * Gets the value of the keyStringFr property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getKeyStringFr() {
        return keyStringFr;
    }

    /**
     * Sets the value of the keyStringFr property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setKeyStringFr(String value) {
        this.keyStringFr = value;
    }

    /**
     * Gets the value of the agreementType property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAgreementType() {
        return agreementType;
    }

    /**
     * Sets the value of the agreementType property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAgreementType(String value) {
        this.agreementType = value;
    }

    /**
     * Gets the value of the processType property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getProcessType() {
        return processType;
    }

    /**
     * Sets the value of the processType property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setProcessType(Integer value) {
        this.processType = value;
    }

    /**
     * Gets the value of the legalReference property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLegalReference() {
        return legalReference;
    }

    /**
     * Sets the value of the legalReference property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLegalReference(String value) {
        this.legalReference = value;
    }

    /**
     * Gets the value of the publicationDate property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets the value of the publicationDate property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setPublicationDate(XMLGregorianCalendar value) {
        this.publicationDate = value;
    }

    /**
     * Gets the value of the modificationDate property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets the value of the modificationDate property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setModificationDate(XMLGregorianCalendar value) {
        this.modificationDate = value;
    }

    /**
     * Gets the value of the processTypeOverrule property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getProcessTypeOverrule() {
        return processTypeOverrule;
    }

    /**
     * Sets the value of the processTypeOverrule property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setProcessTypeOverrule(String value) {
        this.processTypeOverrule = value;
    }

    /**
     * Gets the value of the paragraphVersion property.
     *
     */
    public int getParagraphVersion() {
        return paragraphVersion;
    }

    /**
     * Sets the value of the paragraphVersion property.
     *
     */
    public void setParagraphVersion(int value) {
        this.paragraphVersion = value;
    }

    /**
     * Gets the value of the agreementTypePro property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAgreementTypePro() {
        return agreementTypePro;
    }

    /**
     * Sets the value of the agreementTypePro property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAgreementTypePro(String value) {
        this.agreementTypePro = value;
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
