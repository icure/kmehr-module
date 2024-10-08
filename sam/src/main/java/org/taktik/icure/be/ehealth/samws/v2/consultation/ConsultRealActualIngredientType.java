//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.consultation;

import org.taktik.icure.be.ehealth.samws.v2.actual.common.RealActualIngredientKeyType;
import org.taktik.icure.be.ehealth.samws.v2.core.IngredientTypeType;
import org.taktik.icure.be.ehealth.samws.v2.core.QuantityType;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ConsultRealActualIngredientType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ConsultRealActualIngredientType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:actual:common}RealActualIngredientKeyType">
 *       &lt;sequence>
 *         &lt;element name="Type" type="{urn:be:fgov:ehealth:samws:v2:core}IngredientTypeType"/>
 *         &lt;element name="KnownEffect" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="Strength" type="{urn:be:fgov:ehealth:samws:v2:core}QuantityType"/>
 *           &lt;element name="StrengthDescription" type="{urn:be:fgov:ehealth:samws:v2:core}String50Type"/>
 *         &lt;/choice>
 *         &lt;element name="AdditionalInformation" type="{urn:be:fgov:ehealth:samws:v2:core}String255Type" minOccurs="0"/>
 *         &lt;element name="Substance" type="{urn:be:fgov:ehealth:samws:v2:consultation}SubstanceWithStandardsType"/>
 *         &lt;element name="RealActualIngredientEquivalent" type="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultRealActualIngredientEquivalentType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:be:fgov:ehealth:samws:v2:consultation}validityPeriod"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultRealActualIngredientType", propOrder = {
    "type",
    "knownEffect",
    "strengthDescription",
    "strength",
    "additionalInformation",
    "substance",
    "realActualIngredientEquivalents"
})
public class ConsultRealActualIngredientType
    extends RealActualIngredientKeyType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected IngredientTypeType type;
    @XmlElement(name = "KnownEffect")
    protected Boolean knownEffect;
    @XmlElement(name = "StrengthDescription")
    protected String strengthDescription;
    @XmlElement(name = "Strength")
    protected QuantityType strength;
    @XmlElement(name = "AdditionalInformation")
    protected String additionalInformation;
    @XmlElement(name = "Substance", required = true)
    protected SubstanceWithStandardsType substance;
    @XmlElement(name = "RealActualIngredientEquivalent")
    protected List<ConsultRealActualIngredientEquivalentType> realActualIngredientEquivalents;
    @XmlAttribute(name = "StartDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlAttribute(name = "EndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link IngredientTypeType }
     *
     */
    public IngredientTypeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link IngredientTypeType }
     *
     */
    public void setType(IngredientTypeType value) {
        this.type = value;
    }

    /**
     * Gets the value of the knownEffect property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isKnownEffect() {
        return knownEffect;
    }

    /**
     * Sets the value of the knownEffect property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setKnownEffect(Boolean value) {
        this.knownEffect = value;
    }

    /**
     * Gets the value of the strengthDescription property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStrengthDescription() {
        return strengthDescription;
    }

    /**
     * Sets the value of the strengthDescription property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStrengthDescription(String value) {
        this.strengthDescription = value;
    }

    /**
     * Gets the value of the strength property.
     *
     * @return
     *     possible object is
     *     {@link QuantityType }
     *
     */
    public QuantityType getStrength() {
        return strength;
    }

    /**
     * Sets the value of the strength property.
     *
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *
     */
    public void setStrength(QuantityType value) {
        this.strength = value;
    }

    /**
     * Gets the value of the additionalInformation property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * Sets the value of the additionalInformation property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAdditionalInformation(String value) {
        this.additionalInformation = value;
    }

    /**
     * Gets the value of the substance property.
     *
     * @return
     *     possible object is
     *     {@link SubstanceWithStandardsType }
     *
     */
    public SubstanceWithStandardsType getSubstance() {
        return substance;
    }

    /**
     * Sets the value of the substance property.
     *
     * @param value
     *     allowed object is
     *     {@link SubstanceWithStandardsType }
     *
     */
    public void setSubstance(SubstanceWithStandardsType value) {
        this.substance = value;
    }

    /**
     * Gets the value of the realActualIngredientEquivalents property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the realActualIngredientEquivalents property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRealActualIngredientEquivalents().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConsultRealActualIngredientEquivalentType }
     *
     *
     */
    public List<ConsultRealActualIngredientEquivalentType> getRealActualIngredientEquivalents() {
        if (realActualIngredientEquivalents == null) {
            realActualIngredientEquivalents = new ArrayList<ConsultRealActualIngredientEquivalentType>();
        }
        return this.realActualIngredientEquivalents;
    }

    /**
     * Gets the value of the startDate property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

}
