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
 * <p>Java class for LegalBasisDataType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LegalBasisDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:export}DataPeriodType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit}LegalBasisFields"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LegalBasisDataType", propOrder = {
    "title",
    "type",
    "effectiveOn"
})
public class LegalBasisDataType
    extends DataPeriodType
{

    @XmlElement(name = "Title", namespace = "urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit", required = true)
    protected Text255Type title;
    @XmlElement(name = "Type", namespace = "urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit", required = true)
    @XmlSchemaType(name = "string")
    protected LegalBasisTypeType type;
    @XmlElement(name = "EffectiveOn", namespace = "urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar effectiveOn;

    /**
     * Gets the value of the title property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setTitle(Text255Type value) {
        this.title = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link LegalBasisTypeType }
     *
     */
    public LegalBasisTypeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link LegalBasisTypeType }
     *
     */
    public void setType(LegalBasisTypeType value) {
        this.type = value;
    }

    /**
     * Gets the value of the effectiveOn property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getEffectiveOn() {
        return effectiveOn;
    }

    /**
     * Sets the value of the effectiveOn property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setEffectiveOn(XMLGregorianCalendar value) {
        this.effectiveOn = value;
    }

}
