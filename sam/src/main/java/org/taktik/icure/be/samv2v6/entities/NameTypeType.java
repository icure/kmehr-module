//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NameTypeType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="NameTypeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NameId" type="{urn:be:fgov:ehealth:samws:v2:core}Number10Type"/>
 *         &lt;element name="NameType" type="{urn:be:fgov:ehealth:samws:v2:core}String50Type" minOccurs="0"/>
 *         &lt;element name="NameTypeSeq" type="{urn:be:fgov:ehealth:samws:v2:core}Number2Type" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="NameTypeCV" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}String6Type" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NameTypeType", namespace = "urn:be:fgov:ehealth:samws:v2:refdata", propOrder = {
    "nameId",
    "nameType",
    "nameTypeSeq"
})
public class NameTypeType {

    @XmlElement(name = "NameId", namespace = "urn:be:fgov:ehealth:samws:v2:refdata", required = true)
    protected BigDecimal nameId;
    @XmlElement(name = "NameType", namespace = "urn:be:fgov:ehealth:samws:v2:refdata")
    protected String nameType;
    @XmlElement(name = "NameTypeSeq", namespace = "urn:be:fgov:ehealth:samws:v2:refdata")
    protected Integer nameTypeSeq;
    @XmlAttribute(name = "NameTypeCV", required = true)
    protected String nameTypeCV;

    /**
     * Gets the value of the nameId property.
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getNameId() {
        return nameId;
    }

    /**
     * Sets the value of the nameId property.
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setNameId(BigDecimal value) {
        this.nameId = value;
    }

    /**
     * Gets the value of the nameType property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNameType() {
        return nameType;
    }

    /**
     * Sets the value of the nameType property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNameType(String value) {
        this.nameType = value;
    }

    /**
     * Gets the value of the nameTypeSeq property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getNameTypeSeq() {
        return nameTypeSeq;
    }

    /**
     * Sets the value of the nameTypeSeq property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setNameTypeSeq(Integer value) {
        this.nameTypeSeq = value;
    }

    /**
     * Gets the value of the nameTypeCV property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNameTypeCV() {
        return nameTypeCV;
    }

    /**
     * Sets the value of the nameTypeCV property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNameTypeCV(String value) {
        this.nameTypeCV = value;
    }

}
