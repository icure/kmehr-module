//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.actual.common;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;


/**
 * <p>Java class for UpdateDmppReimbursementStatusType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UpdateDmppReimbursementStatusType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:actual:common}DmppKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}DmppReimbursementFields"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:be:fgov:ehealth:samws:v2:core}actionPeriod"/>
 *       &lt;attribute name="ctiExtended" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}CtiExtendedType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateDmppReimbursementStatusType", propOrder = {
    "reimbursable"
})
public class UpdateDmppReimbursementStatusType
    extends DmppKeyType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "Reimbursable")
    protected boolean reimbursable;
    @XmlAttribute(name = "ctiExtended", required = true)
    protected String ctiExtended;
    @XmlAttribute(name = "from", required = true)
    protected XMLGregorianCalendar from;
    @XmlAttribute(name = "to")
    protected XMLGregorianCalendar to;

    /**
     * Gets the value of the reimbursable property.
     *
     */
    public boolean isReimbursable() {
        return reimbursable;
    }

    /**
     * Sets the value of the reimbursable property.
     *
     */
    public void setReimbursable(boolean value) {
        this.reimbursable = value;
    }

    /**
     * Gets the value of the ctiExtended property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCtiExtended() {
        return ctiExtended;
    }

    /**
     * Sets the value of the ctiExtended property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCtiExtended(String value) {
        this.ctiExtended = value;
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
