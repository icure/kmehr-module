//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.refdata;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * <p>Java class for StandardFormKeyCommonType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="StandardFormKeyCommonType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="standard" use="required" type="{urn:be:fgov:ehealth:samws:v2:refdata}StdFrmAllStandardsType" />
 *       &lt;attribute name="code" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}String20Type" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StandardFormKeyCommonType")
@XmlSeeAlso({
    StandardFormCommonType.class
})
public class StandardFormKeyCommonType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlAttribute(name = "standard", required = true)
    protected StdFrmAllStandardsType standard;
    @XmlAttribute(name = "code", required = true)
    protected String code;

    /**
     * Gets the value of the standard property.
     *
     * @return
     *     possible object is
     *     {@link StdFrmAllStandardsType }
     *
     */
    public StdFrmAllStandardsType getStandard() {
        return standard;
    }

    /**
     * Sets the value of the standard property.
     *
     * @param value
     *     allowed object is
     *     {@link StdFrmAllStandardsType }
     *
     */
    public void setStandard(StdFrmAllStandardsType value) {
        this.standard = value;
    }

    /**
     * Gets the value of the code property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCode(String value) {
        this.code = value;
    }

}
