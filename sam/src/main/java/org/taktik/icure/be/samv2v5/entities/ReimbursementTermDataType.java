//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v5.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReimbursementTermDataType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ReimbursementTermDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:export}DataPeriodType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit}ReimbursementTermFields"/>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:export}ReimbursementTermReferenceFields"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReimbursementTermDataType", propOrder = {
    "valueUnit",
    "parameter"
})
public class ReimbursementTermDataType
    extends DataPeriodType
{

    @XmlElement(name = "ValueUnit", namespace = "urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit", required = true)
    protected ParameterValueType valueUnit;
    @XmlElement(name = "Parameter", required = true)
    protected ParameterType parameter;

    /**
     * Gets the value of the valueUnit property.
     *
     * @return
     *     possible object is
     *     {@link ParameterValueType }
     *
     */
    public ParameterValueType getValueUnit() {
        return valueUnit;
    }

    /**
     * Sets the value of the valueUnit property.
     *
     * @param value
     *     allowed object is
     *     {@link ParameterValueType }
     *
     */
    public void setValueUnit(ParameterValueType value) {
        this.valueUnit = value;
    }

    /**
     * Gets the value of the parameter property.
     *
     * @return
     *     possible object is
     *     {@link ParameterType }
     *
     */
    public ParameterType getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     *
     * @param value
     *     allowed object is
     *     {@link ParameterType }
     *
     */
    public void setParameter(ParameterType value) {
        this.parameter = value;
    }

}
