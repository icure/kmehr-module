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
 * <p>Java class for ReimbursedImportedMedicinalProductPackageDataType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ReimbursedImportedMedicinalProductPackageDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:export}DataPeriodType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:imported}ReimbursedImportedMedicinalProductPackageFields"/>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:export}ReimbursedImportedMedicinalProductPackageReferenceFields"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReimbursedImportedMedicinalProductPackageDataType", propOrder = {
    "pricingUnitQuantity",
    "pricingUnitLabel",
    "legalText",
    "addedDocument",
    "reimbursementCriterion"
})
public class ReimbursedImportedMedicinalProductPackageDataType
    extends DataPeriodType
{

    @XmlElement(name = "PricingUnitQuantity", namespace = "urn:be:fgov:ehealth:samws:v2:actual:imported", required = true)
    protected BigDecimal pricingUnitQuantity;
    @XmlElement(name = "PricingUnitLabel", namespace = "urn:be:fgov:ehealth:samws:v2:actual:imported", required = true)
    protected Text255Type pricingUnitLabel;
    @XmlElement(name = "LegalText", namespace = "urn:be:fgov:ehealth:samws:v2:actual:imported")
    protected TextType legalText;
    @XmlElement(name = "AddedDocument", namespace = "urn:be:fgov:ehealth:samws:v2:actual:imported")
    protected TextType addedDocument;
    @XmlElement(name = "ReimbursementCriterion", required = true)
    protected ReimbursementCriterionType reimbursementCriterion;

    /**
     * Gets the value of the pricingUnitQuantity property.
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getPricingUnitQuantity() {
        return pricingUnitQuantity;
    }

    /**
     * Sets the value of the pricingUnitQuantity property.
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setPricingUnitQuantity(BigDecimal value) {
        this.pricingUnitQuantity = value;
    }

    /**
     * Gets the value of the pricingUnitLabel property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getPricingUnitLabel() {
        return pricingUnitLabel;
    }

    /**
     * Sets the value of the pricingUnitLabel property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setPricingUnitLabel(Text255Type value) {
        this.pricingUnitLabel = value;
    }

    /**
     * Gets the value of the legalText property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getLegalText() {
        return legalText;
    }

    /**
     * Sets the value of the legalText property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setLegalText(TextType value) {
        this.legalText = value;
    }

    /**
     * Gets the value of the addedDocument property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getAddedDocument() {
        return addedDocument;
    }

    /**
     * Sets the value of the addedDocument property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setAddedDocument(TextType value) {
        this.addedDocument = value;
    }

    /**
     * Gets the value of the reimbursementCriterion property.
     *
     * @return
     *     possible object is
     *     {@link ReimbursementCriterionType }
     *
     */
    public ReimbursementCriterionType getReimbursementCriterion() {
        return reimbursementCriterion;
    }

    /**
     * Sets the value of the reimbursementCriterion property.
     *
     * @param value
     *     allowed object is
     *     {@link ReimbursementCriterionType }
     *
     */
    public void setReimbursementCriterion(ReimbursementCriterionType value) {
        this.reimbursementCriterion = value;
    }

}
