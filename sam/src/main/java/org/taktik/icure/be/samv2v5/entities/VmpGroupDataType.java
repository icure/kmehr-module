//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v5.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for VmpGroupDataType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="VmpGroupDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:export}DataPeriodType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:virtual:common}VmpGroupFields"/>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:export}VmpGroupReferenceFields"/>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:virtual:common}VmpGroupStandardDosages" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VmpGroupDataType", propOrder = {
    "name",
    "noGenericPrescriptionReason",
    "noSwitchReason",
    "patientFrailtyIndicator",
    "singleAdministrationDose",
    "standardDosage"
})
public class VmpGroupDataType
    extends DataPeriodType
{

    @XmlElement(name = "Name", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common", required = true)
    protected Text255Type name;
    @XmlElement(name = "NoGenericPrescriptionReason")
    protected NoGenericPrescriptionReasonType noGenericPrescriptionReason;
    @XmlElement(name = "NoSwitchReason")
    protected NoSwitchReasonType noSwitchReason;
    @XmlElement(name = "PatientFrailtyIndicator", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected Boolean patientFrailtyIndicator;
    @XmlElement(name = "SingleAdministrationDose", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected QuantityType singleAdministrationDose;
    @XmlElement(name = "StandardDosage", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common")
    protected List<StandardDosageType> standardDosage;

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setName(Text255Type value) {
        this.name = value;
    }

    /**
     * Gets the value of the noGenericPrescriptionReason property.
     *
     * @return
     *     possible object is
     *     {@link NoGenericPrescriptionReasonType }
     *
     */
    public NoGenericPrescriptionReasonType getNoGenericPrescriptionReason() {
        return noGenericPrescriptionReason;
    }

    /**
     * Sets the value of the noGenericPrescriptionReason property.
     *
     * @param value
     *     allowed object is
     *     {@link NoGenericPrescriptionReasonType }
     *
     */
    public void setNoGenericPrescriptionReason(NoGenericPrescriptionReasonType value) {
        this.noGenericPrescriptionReason = value;
    }

    /**
     * Gets the value of the noSwitchReason property.
     *
     * @return
     *     possible object is
     *     {@link NoSwitchReasonType }
     *
     */
    public NoSwitchReasonType getNoSwitchReason() {
        return noSwitchReason;
    }

    /**
     * Sets the value of the noSwitchReason property.
     *
     * @param value
     *     allowed object is
     *     {@link NoSwitchReasonType }
     *
     */
    public void setNoSwitchReason(NoSwitchReasonType value) {
        this.noSwitchReason = value;
    }

    /**
     * Gets the value of the patientFrailtyIndicator property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isPatientFrailtyIndicator() {
        return patientFrailtyIndicator;
    }

    /**
     * Sets the value of the patientFrailtyIndicator property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setPatientFrailtyIndicator(Boolean value) {
        this.patientFrailtyIndicator = value;
    }

    /**
     * Gets the value of the singleAdministrationDose property.
     *
     * @return
     *     possible object is
     *     {@link QuantityType }
     *
     */
    public QuantityType getSingleAdministrationDose() {
        return singleAdministrationDose;
    }

    /**
     * Sets the value of the singleAdministrationDose property.
     *
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *
     */
    public void setSingleAdministrationDose(QuantityType value) {
        this.singleAdministrationDose = value;
    }

    /**
     * Gets the value of the standardDosage property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the standardDosage property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStandardDosage().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StandardDosageType }
     *
     *
     */
    public List<StandardDosageType> getStandardDosage() {
        if (standardDosage == null) {
            standardDosage = new ArrayList<StandardDosageType>();
        }
        return this.standardDosage;
    }

}
