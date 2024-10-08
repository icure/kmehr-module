//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v5.entities;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for CommercializationType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CommercializationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}CommercializationFields"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommercializationType", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", propOrder = {
    "endOfCommercialization",
    "reason",
    "additionalInformation",
    "impact"
})
@XmlSeeAlso({
    ChangeCommercializationType.class,
    AddCommercializationType.class
})
public class CommercializationType {

    @XmlElement(name = "EndOfCommercialization")
    protected TextType endOfCommercialization;
    @XmlElement(name = "Reason")
    protected TextType reason;
    @XmlElement(name = "AdditionalInformation")
    protected TextType additionalInformation;
    @XmlElement(name = "Impact")
    protected TextType impact;

    /**
     * Gets the value of the endOfCommercialization property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getEndOfCommercialization() {
        return endOfCommercialization;
    }

    /**
     * Sets the value of the endOfCommercialization property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setEndOfCommercialization(TextType value) {
        this.endOfCommercialization = value;
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
     *     {@link TextType }
     *
     */
    public TextType getImpact() {
        return impact;
    }

    /**
     * Sets the value of the impact property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setImpact(TextType value) {
        this.impact = value;
    }

}
