//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.reimbursement.submit;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * <p>Java class for PricingRangeType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PricingRangeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Min" type="{urn:be:fgov:ehealth:samws:v2:core}Decimal9d3Type"/>
 *         &lt;element name="Max" type="{urn:be:fgov:ehealth:samws:v2:core}Decimal9d3Type"/>
 *       &lt;/sequence>
 *       &lt;attribute name="unit" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}String20Type" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PricingRangeType", propOrder = {
    "min",
    "max"
})
public class PricingRangeType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "Min", required = true)
    protected BigDecimal min;
    @XmlElement(name = "Max", required = true)
    protected BigDecimal max;
    @XmlAttribute(name = "unit", required = true)
    protected String unit;

    /**
     * Gets the value of the min property.
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getMin() {
        return min;
    }

    /**
     * Sets the value of the min property.
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setMin(BigDecimal value) {
        this.min = value;
    }

    /**
     * Gets the value of the max property.
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getMax() {
        return max;
    }

    /**
     * Sets the value of the max property.
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setMax(BigDecimal value) {
        this.max = value;
    }

    /**
     * Gets the value of the unit property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUnit(String value) {
        this.unit = value;
    }

}
