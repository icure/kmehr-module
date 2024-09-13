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
 * <p>Java class for LegalReferenceDataType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LegalReferenceDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:export}DataPeriodType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit}LegalReferenceFields"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LegalReferenceDataType", propOrder = {
    "title",
    "type",
    "firstPublishedOn",
    "lastModifiedOn"
})
public class LegalReferenceDataType
    extends DataPeriodType
{

    @XmlElement(name = "Title", namespace = "urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit", required = true)
    protected TextType title;
    @XmlElement(name = "Type", namespace = "urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit", required = true)
    @XmlSchemaType(name = "string")
    protected LegalReferenceTypeType type;
    @XmlElement(name = "FirstPublishedOn", namespace = "urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar firstPublishedOn;
    @XmlElement(name = "LastModifiedOn", namespace = "urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastModifiedOn;

    /**
     * Gets the value of the title property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setTitle(TextType value) {
        this.title = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link LegalReferenceTypeType }
     *
     */
    public LegalReferenceTypeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link LegalReferenceTypeType }
     *
     */
    public void setType(LegalReferenceTypeType value) {
        this.type = value;
    }

    /**
     * Gets the value of the firstPublishedOn property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFirstPublishedOn() {
        return firstPublishedOn;
    }

    /**
     * Sets the value of the firstPublishedOn property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setFirstPublishedOn(XMLGregorianCalendar value) {
        this.firstPublishedOn = value;
    }

    /**
     * Gets the value of the lastModifiedOn property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getLastModifiedOn() {
        return lastModifiedOn;
    }

    /**
     * Sets the value of the lastModifiedOn property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setLastModifiedOn(XMLGregorianCalendar value) {
        this.lastModifiedOn = value;
    }

}
