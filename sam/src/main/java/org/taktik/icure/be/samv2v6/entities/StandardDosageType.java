//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package org.taktik.icure.be.samv2v6.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StandardDosageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StandardDosageType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:virtual:common}StandardDosageKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:virtual:common}StandardDosageFields"/>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:virtual:common}StandardDosageReferences"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StandardDosageType", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common", propOrder = {
    "targetGroup",
    "kidneyFailureClass",
    "liverFailureClass",
    "treatmentDurationType",
    "temporalityDuration",
    "temporalityUserProvided",
    "temporalityNote",
    "quantity",
    "quantityDenominator",
    "quantityMultiplicator",
    "quantityRangeLower",
    "quantityRangeLowerDenominator",
    "quantityRangeUpper",
    "quantityRangeUpperDenominator",
    "administrationFrequencyQuantity",
    "administrationFrequencyIsMax",
    "administrationFrequencyTimeframe",
    "maximumAdministrationQuantity",
    "maximumAdministrationQuantityDenominator",
    "maximumAdministrationQuantityMultiplicator",
    "maximumDailyQuantity",
    "maximumDailyQuantityDenominator",
    "maximumDailyQuantityMultiplicator",
    "textualDosage",
    "supplementaryInfo",
    "routeSpecification",
    "indicationCode",
    "parameterBounds",
    "routeOfAdministrationCode"
})
@XmlSeeAlso({
    AddStandardDosageType.class
})
public class StandardDosageType
    extends StandardDosageKeyType
{

    @XmlElement(name = "TargetGroup", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common", required = true)
    protected String targetGroup;
    @XmlElement(name = "KidneyFailureClass", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected Integer kidneyFailureClass;
    @XmlElement(name = "LiverFailureClass", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected Integer liverFailureClass;
    @XmlElement(name = "TreatmentDurationType", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common", required = true)
    protected String treatmentDurationType;
    @XmlElement(name = "TemporalityDuration", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected QuantityType temporalityDuration;
    @XmlElement(name = "TemporalityUserProvided", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected Boolean temporalityUserProvided;
    @XmlElement(name = "TemporalityNote", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected TextType temporalityNote;
    @XmlElement(name = "Quantity", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected BigDecimal quantity;
    @XmlElement(name = "QuantityDenominator", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected BigDecimal quantityDenominator;
    @XmlElement(name = "QuantityMultiplicator", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected String quantityMultiplicator;
    @XmlElement(name = "QuantityRangeLower", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected BigDecimal quantityRangeLower;
    @XmlElement(name = "QuantityRangeLowerDenominator", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected BigDecimal quantityRangeLowerDenominator;
    @XmlElement(name = "QuantityRangeUpper", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected BigDecimal quantityRangeUpper;
    @XmlElement(name = "QuantityRangeUpperDenominator", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected BigDecimal quantityRangeUpperDenominator;
    @XmlElement(name = "AdministrationFrequencyQuantity", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected Integer administrationFrequencyQuantity;
    @XmlElement(name = "AdministrationFrequencyIsMax", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected Boolean administrationFrequencyIsMax;
    @XmlElement(name = "AdministrationFrequencyTimeframe", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected QuantityType administrationFrequencyTimeframe;
    @XmlElement(name = "MaximumAdministrationQuantity", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected BigDecimal maximumAdministrationQuantity;
    @XmlElement(name = "MaximumAdministrationQuantityDenominator", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected BigDecimal maximumAdministrationQuantityDenominator;
    @XmlElement(name = "MaximumAdministrationQuantityMultiplicator", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected String maximumAdministrationQuantityMultiplicator;
    @XmlElement(name = "MaximumDailyQuantity", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected BigDecimal maximumDailyQuantity;
    @XmlElement(name = "MaximumDailyQuantityDenominator", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected BigDecimal maximumDailyQuantityDenominator;
    @XmlElement(name = "MaximumDailyQuantityMultiplicator", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected String maximumDailyQuantityMultiplicator;
    @XmlElement(name = "TextualDosage", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected TextType textualDosage;
    @XmlElement(name = "SupplementaryInfo", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected TextType supplementaryInfo;
    @XmlElement(name = "RouteSpecification", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected Text255Type routeSpecification;
    @XmlElement(name = "IndicationCode", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common", required = true)
    protected List<String> indicationCode;
    @XmlElement(name = "ParameterBounds", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected List<BoundedParameterType> parameterBounds;
    @XmlElement(name = "RouteOfAdministrationCode", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common", required = true)
    protected String routeOfAdministrationCode;

    /**
     * Gets the value of the targetGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetGroup() {
        return targetGroup;
    }

    /**
     * Sets the value of the targetGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetGroup(String value) {
        this.targetGroup = value;
    }

    /**
     * Gets the value of the kidneyFailureClass property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKidneyFailureClass() {
        return kidneyFailureClass;
    }

    /**
     * Sets the value of the kidneyFailureClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKidneyFailureClass(Integer value) {
        this.kidneyFailureClass = value;
    }

    /**
     * Gets the value of the liverFailureClass property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLiverFailureClass() {
        return liverFailureClass;
    }

    /**
     * Sets the value of the liverFailureClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLiverFailureClass(Integer value) {
        this.liverFailureClass = value;
    }

    /**
     * Gets the value of the treatmentDurationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTreatmentDurationType() {
        return treatmentDurationType;
    }

    /**
     * Sets the value of the treatmentDurationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTreatmentDurationType(String value) {
        this.treatmentDurationType = value;
    }

    /**
     * Gets the value of the temporalityDuration property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getTemporalityDuration() {
        return temporalityDuration;
    }

    /**
     * Sets the value of the temporalityDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setTemporalityDuration(QuantityType value) {
        this.temporalityDuration = value;
    }

    /**
     * Gets the value of the temporalityUserProvided property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTemporalityUserProvided() {
        return temporalityUserProvided;
    }

    /**
     * Sets the value of the temporalityUserProvided property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTemporalityUserProvided(Boolean value) {
        this.temporalityUserProvided = value;
    }

    /**
     * Gets the value of the temporalityNote property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTemporalityNote() {
        return temporalityNote;
    }

    /**
     * Sets the value of the temporalityNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTemporalityNote(TextType value) {
        this.temporalityNote = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantity(BigDecimal value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the quantityDenominator property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantityDenominator() {
        return quantityDenominator;
    }

    /**
     * Sets the value of the quantityDenominator property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantityDenominator(BigDecimal value) {
        this.quantityDenominator = value;
    }

    /**
     * Gets the value of the quantityMultiplicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantityMultiplicator() {
        return quantityMultiplicator;
    }

    /**
     * Sets the value of the quantityMultiplicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantityMultiplicator(String value) {
        this.quantityMultiplicator = value;
    }

    /**
     * Gets the value of the quantityRangeLower property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantityRangeLower() {
        return quantityRangeLower;
    }

    /**
     * Sets the value of the quantityRangeLower property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantityRangeLower(BigDecimal value) {
        this.quantityRangeLower = value;
    }

    /**
     * Gets the value of the quantityRangeLowerDenominator property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantityRangeLowerDenominator() {
        return quantityRangeLowerDenominator;
    }

    /**
     * Sets the value of the quantityRangeLowerDenominator property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantityRangeLowerDenominator(BigDecimal value) {
        this.quantityRangeLowerDenominator = value;
    }

    /**
     * Gets the value of the quantityRangeUpper property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantityRangeUpper() {
        return quantityRangeUpper;
    }

    /**
     * Sets the value of the quantityRangeUpper property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantityRangeUpper(BigDecimal value) {
        this.quantityRangeUpper = value;
    }

    /**
     * Gets the value of the quantityRangeUpperDenominator property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantityRangeUpperDenominator() {
        return quantityRangeUpperDenominator;
    }

    /**
     * Sets the value of the quantityRangeUpperDenominator property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantityRangeUpperDenominator(BigDecimal value) {
        this.quantityRangeUpperDenominator = value;
    }

    /**
     * Gets the value of the administrationFrequencyQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAdministrationFrequencyQuantity() {
        return administrationFrequencyQuantity;
    }

    /**
     * Sets the value of the administrationFrequencyQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAdministrationFrequencyQuantity(Integer value) {
        this.administrationFrequencyQuantity = value;
    }

    /**
     * Gets the value of the administrationFrequencyIsMax property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAdministrationFrequencyIsMax() {
        return administrationFrequencyIsMax;
    }

    /**
     * Sets the value of the administrationFrequencyIsMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAdministrationFrequencyIsMax(Boolean value) {
        this.administrationFrequencyIsMax = value;
    }

    /**
     * Gets the value of the administrationFrequencyTimeframe property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getAdministrationFrequencyTimeframe() {
        return administrationFrequencyTimeframe;
    }

    /**
     * Sets the value of the administrationFrequencyTimeframe property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setAdministrationFrequencyTimeframe(QuantityType value) {
        this.administrationFrequencyTimeframe = value;
    }

    /**
     * Gets the value of the maximumAdministrationQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaximumAdministrationQuantity() {
        return maximumAdministrationQuantity;
    }

    /**
     * Sets the value of the maximumAdministrationQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaximumAdministrationQuantity(BigDecimal value) {
        this.maximumAdministrationQuantity = value;
    }

    /**
     * Gets the value of the maximumAdministrationQuantityDenominator property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaximumAdministrationQuantityDenominator() {
        return maximumAdministrationQuantityDenominator;
    }

    /**
     * Sets the value of the maximumAdministrationQuantityDenominator property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaximumAdministrationQuantityDenominator(BigDecimal value) {
        this.maximumAdministrationQuantityDenominator = value;
    }

    /**
     * Gets the value of the maximumAdministrationQuantityMultiplicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaximumAdministrationQuantityMultiplicator() {
        return maximumAdministrationQuantityMultiplicator;
    }

    /**
     * Sets the value of the maximumAdministrationQuantityMultiplicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaximumAdministrationQuantityMultiplicator(String value) {
        this.maximumAdministrationQuantityMultiplicator = value;
    }

    /**
     * Gets the value of the maximumDailyQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaximumDailyQuantity() {
        return maximumDailyQuantity;
    }

    /**
     * Sets the value of the maximumDailyQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaximumDailyQuantity(BigDecimal value) {
        this.maximumDailyQuantity = value;
    }

    /**
     * Gets the value of the maximumDailyQuantityDenominator property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaximumDailyQuantityDenominator() {
        return maximumDailyQuantityDenominator;
    }

    /**
     * Sets the value of the maximumDailyQuantityDenominator property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaximumDailyQuantityDenominator(BigDecimal value) {
        this.maximumDailyQuantityDenominator = value;
    }

    /**
     * Gets the value of the maximumDailyQuantityMultiplicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaximumDailyQuantityMultiplicator() {
        return maximumDailyQuantityMultiplicator;
    }

    /**
     * Sets the value of the maximumDailyQuantityMultiplicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaximumDailyQuantityMultiplicator(String value) {
        this.maximumDailyQuantityMultiplicator = value;
    }

    /**
     * Gets the value of the textualDosage property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTextualDosage() {
        return textualDosage;
    }

    /**
     * Sets the value of the textualDosage property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTextualDosage(TextType value) {
        this.textualDosage = value;
    }

    /**
     * Gets the value of the supplementaryInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSupplementaryInfo() {
        return supplementaryInfo;
    }

    /**
     * Sets the value of the supplementaryInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSupplementaryInfo(TextType value) {
        this.supplementaryInfo = value;
    }

    /**
     * Gets the value of the routeSpecification property.
     * 
     * @return
     *     possible object is
     *     {@link Text255Type }
     *     
     */
    public Text255Type getRouteSpecification() {
        return routeSpecification;
    }

    /**
     * Sets the value of the routeSpecification property.
     * 
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *     
     */
    public void setRouteSpecification(Text255Type value) {
        this.routeSpecification = value;
    }

    /**
     * Gets the value of the indicationCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indicationCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndicationCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIndicationCode() {
        if (indicationCode == null) {
            indicationCode = new ArrayList<String>();
        }
        return this.indicationCode;
    }

    /**
     * Gets the value of the parameterBounds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameterBounds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameterBounds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BoundedParameterType }
     * 
     * 
     */
    public List<BoundedParameterType> getParameterBounds() {
        if (parameterBounds == null) {
            parameterBounds = new ArrayList<BoundedParameterType>();
        }
        return this.parameterBounds;
    }

    /**
     * Gets the value of the routeOfAdministrationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRouteOfAdministrationCode() {
        return routeOfAdministrationCode;
    }

    /**
     * Sets the value of the routeOfAdministrationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRouteOfAdministrationCode(String value) {
        this.routeOfAdministrationCode = value;
    }

}
