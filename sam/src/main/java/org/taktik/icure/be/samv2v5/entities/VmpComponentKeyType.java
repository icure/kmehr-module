//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v5.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VmpComponentKeyType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="VmpComponentKeyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="code" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}PositiveIntType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VmpComponentKeyType", namespace = "urn:be:fgov:ehealth:samws:v2:core")
@XmlSeeAlso({
    ChangeVmpComponentType.class,
    VmpComponentType.class
})
public class VmpComponentKeyType {

    @XmlAttribute(name = "code", required = true)
    protected int code;

    /**
     * Gets the value of the code property.
     *
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     */
    public void setCode(int value) {
        this.code = value;
    }

}
