//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AmpFamhpType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AmpFamhpType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:actual:common}AmpKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}AmpFamhpFields"/>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}AmpFamhpReferences"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmpFamhpType", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", propOrder = {
    "officialName",
    "status",
    "name",
    "blackTriangle",
    "medicineType",
    "prescriptionNameFamhp",
    "companyActorNr"
})
@XmlSeeAlso({
    AddAmpFamhpType.class
})
public class AmpFamhpType
    extends AmpKeyType
{

    @XmlElement(name = "OfficialName", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", required = true)
    protected String officialName;
    @XmlElement(name = "Status", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    @XmlSchemaType(name = "string")
    protected AmpStatusType status;
    @XmlElement(name = "Name", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", required = true)
    protected Text255Type name;
    @XmlElement(name = "BlackTriangle", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected boolean blackTriangle;
    @XmlElement(name = "MedicineType", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", required = true)
    @XmlSchemaType(name = "string")
    protected MedicineTypeType medicineType;
    @XmlElement(name = "PrescriptionNameFamhp", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Text255Type prescriptionNameFamhp;
    @XmlElement(name = "CompanyActorNr", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", required = true)
    protected String companyActorNr;

    /**
     * Gets the value of the officialName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOfficialName() {
        return officialName;
    }

    /**
     * Sets the value of the officialName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOfficialName(String value) {
        this.officialName = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return
     *     possible object is
     *     {@link AmpStatusType }
     *
     */
    public AmpStatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value
     *     allowed object is
     *     {@link AmpStatusType }
     *
     */
    public void setStatus(AmpStatusType value) {
        this.status = value;
    }

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
     * Gets the value of the blackTriangle property.
     *
     */
    public boolean isBlackTriangle() {
        return blackTriangle;
    }

    /**
     * Sets the value of the blackTriangle property.
     *
     */
    public void setBlackTriangle(boolean value) {
        this.blackTriangle = value;
    }

    /**
     * Gets the value of the medicineType property.
     *
     * @return
     *     possible object is
     *     {@link MedicineTypeType }
     *
     */
    public MedicineTypeType getMedicineType() {
        return medicineType;
    }

    /**
     * Sets the value of the medicineType property.
     *
     * @param value
     *     allowed object is
     *     {@link MedicineTypeType }
     *
     */
    public void setMedicineType(MedicineTypeType value) {
        this.medicineType = value;
    }

    /**
     * Gets the value of the prescriptionNameFamhp property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getPrescriptionNameFamhp() {
        return prescriptionNameFamhp;
    }

    /**
     * Sets the value of the prescriptionNameFamhp property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setPrescriptionNameFamhp(Text255Type value) {
        this.prescriptionNameFamhp = value;
    }

    /**
     * Gets the value of the companyActorNr property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCompanyActorNr() {
        return companyActorNr;
    }

    /**
     * Sets the value of the companyActorNr property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCompanyActorNr(String value) {
        this.companyActorNr = value;
    }

}