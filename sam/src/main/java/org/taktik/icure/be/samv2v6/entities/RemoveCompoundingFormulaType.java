//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RemoveCompoundingFormulaType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RemoveCompoundingFormulaType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:compounding:common}CompoundingFormulaKeyType">
 *       &lt;attribute name="action" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}removeActionType" />
 *       &lt;attribute name="from" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}validityDateType" />
 *       &lt;attribute name="to" type="{urn:be:fgov:ehealth:samws:v2:core}validityDateType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemoveCompoundingFormulaType", namespace = "urn:be:fgov:ehealth:samws:v2:compounding:common")
public class RemoveCompoundingFormulaType
    extends CompoundingFormulaKeyType
{

    @XmlAttribute(name = "action", required = true)
    protected RemoveActionType action;
    @XmlAttribute(name = "from", required = true)
    protected XMLGregorianCalendar from;
    @XmlAttribute(name = "to")
    protected XMLGregorianCalendar to;

    /**
     * Gets the value of the action property.
     *
     * @return
     *     possible object is
     *     {@link RemoveActionType }
     *
     */
    public RemoveActionType getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     *
     * @param value
     *     allowed object is
     *     {@link RemoveActionType }
     *
     */
    public void setAction(RemoveActionType value) {
        this.action = value;
    }

    /**
     * Gets the value of the from property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setFrom(XMLGregorianCalendar value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setTo(XMLGregorianCalendar value) {
        this.to = value;
    }

}
