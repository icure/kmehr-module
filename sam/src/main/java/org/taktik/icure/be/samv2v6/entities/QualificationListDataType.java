//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QualificationListDataType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="QualificationListDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:export}DataPeriodType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:chapteriv:submit}QualificationListFields"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualificationListDataType", propOrder = {
    "nameId",
    "exclusiveInd"
})
public class QualificationListDataType
    extends DataPeriodType
{

    @XmlElement(name = "NameId", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", required = true)
    protected BigDecimal nameId;
    @XmlElement(name = "ExclusiveInd", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String exclusiveInd;

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
     * Gets the value of the exclusiveInd property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getExclusiveInd() {
        return exclusiveInd;
    }

    /**
     * Sets the value of the exclusiveInd property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setExclusiveInd(String value) {
        this.exclusiveInd = value;
    }

}