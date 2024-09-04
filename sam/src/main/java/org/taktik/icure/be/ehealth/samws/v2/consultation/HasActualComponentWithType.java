//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.consultation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/**
 * <p>Java class for HasActualComponentWithType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="HasActualComponentWithType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="PharmaceuticalFormCode" type="{urn:be:fgov:ehealth:samws:v2:consultation}StandardFormCodeCriterionType"/>
 *           &lt;element name="PharmaceuticalFormName" type="{urn:be:fgov:ehealth:samws:v2:consultation}StandardFormNameCriterionType"/>
 *         &lt;/choice>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="RouteOfAdministrationCode" type="{urn:be:fgov:ehealth:samws:v2:consultation}StandardRouteCodeCriterionType"/>
 *           &lt;element name="RouteOfAdministrationName" type="{urn:be:fgov:ehealth:samws:v2:consultation}StandardRouteNameCriterionType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HasActualComponentWithType", propOrder = {
    "pharmaceuticalFormName",
    "pharmaceuticalFormCode",
    "routeOfAdministrationName",
    "routeOfAdministrationCode"
})
public class HasActualComponentWithType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "PharmaceuticalFormName")
    protected StandardFormNameCriterionType pharmaceuticalFormName;
    @XmlElement(name = "PharmaceuticalFormCode")
    protected StandardFormCodeCriterionType pharmaceuticalFormCode;
    @XmlElement(name = "RouteOfAdministrationName")
    protected StandardRouteNameCriterionType routeOfAdministrationName;
    @XmlElement(name = "RouteOfAdministrationCode")
    protected StandardRouteCodeCriterionType routeOfAdministrationCode;

    /**
     * Gets the value of the pharmaceuticalFormName property.
     *
     * @return
     *     possible object is
     *     {@link StandardFormNameCriterionType }
     *
     */
    public StandardFormNameCriterionType getPharmaceuticalFormName() {
        return pharmaceuticalFormName;
    }

    /**
     * Sets the value of the pharmaceuticalFormName property.
     *
     * @param value
     *     allowed object is
     *     {@link StandardFormNameCriterionType }
     *
     */
    public void setPharmaceuticalFormName(StandardFormNameCriterionType value) {
        this.pharmaceuticalFormName = value;
    }

    /**
     * Gets the value of the pharmaceuticalFormCode property.
     *
     * @return
     *     possible object is
     *     {@link StandardFormCodeCriterionType }
     *
     */
    public StandardFormCodeCriterionType getPharmaceuticalFormCode() {
        return pharmaceuticalFormCode;
    }

    /**
     * Sets the value of the pharmaceuticalFormCode property.
     *
     * @param value
     *     allowed object is
     *     {@link StandardFormCodeCriterionType }
     *
     */
    public void setPharmaceuticalFormCode(StandardFormCodeCriterionType value) {
        this.pharmaceuticalFormCode = value;
    }

    /**
     * Gets the value of the routeOfAdministrationName property.
     *
     * @return
     *     possible object is
     *     {@link StandardRouteNameCriterionType }
     *
     */
    public StandardRouteNameCriterionType getRouteOfAdministrationName() {
        return routeOfAdministrationName;
    }

    /**
     * Sets the value of the routeOfAdministrationName property.
     *
     * @param value
     *     allowed object is
     *     {@link StandardRouteNameCriterionType }
     *
     */
    public void setRouteOfAdministrationName(StandardRouteNameCriterionType value) {
        this.routeOfAdministrationName = value;
    }

    /**
     * Gets the value of the routeOfAdministrationCode property.
     *
     * @return
     *     possible object is
     *     {@link StandardRouteCodeCriterionType }
     *
     */
    public StandardRouteCodeCriterionType getRouteOfAdministrationCode() {
        return routeOfAdministrationCode;
    }

    /**
     * Sets the value of the routeOfAdministrationCode property.
     *
     * @param value
     *     allowed object is
     *     {@link StandardRouteCodeCriterionType }
     *
     */
    public void setRouteOfAdministrationCode(StandardRouteCodeCriterionType value) {
        this.routeOfAdministrationCode = value;
    }

}
