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


/**
 * <p>Java class for FormTypeKeyType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FormTypeKeyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="FormTypeId" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}Number3Type" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormTypeKeyType", namespace = "urn:be:fgov:ehealth:samws:v2:refdata")
public class FormTypeKeyType {

    @XmlAttribute(name = "FormTypeId", required = true)
    protected int formTypeId;

    /**
     * Gets the value of the formTypeId property.
     *
     */
    public int getFormTypeId() {
        return formTypeId;
    }

    /**
     * Sets the value of the formTypeId property.
     *
     */
    public void setFormTypeId(int value) {
        this.formTypeId = value;
    }

}