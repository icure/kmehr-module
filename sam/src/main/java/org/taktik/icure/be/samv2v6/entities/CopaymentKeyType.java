//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CopaymentKeyType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CopaymentKeyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="regimeType" use="required" type="{urn:be:fgov:ehealth:samws:v2:reimbursement:submit}RegimeTypeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CopaymentKeyType", namespace = "urn:be:fgov:ehealth:samws:v2:reimbursement:submit")
@XmlSeeAlso({
    CopaymentFullDataType.class,
    CopaymentType.class
})
public class CopaymentKeyType {

    @XmlAttribute(name = "regimeType", required = true)
    protected int regimeType;

    /**
     * Gets the value of the regimeType property.
     *
     */
    public int getRegimeType() {
        return regimeType;
    }

    /**
     * Sets the value of the regimeType property.
     *
     */
    public void setRegimeType(int value) {
        this.regimeType = value;
    }

}
