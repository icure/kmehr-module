//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AmppFamhpType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AmppFamhpType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:actual:common}AmppKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}AmppFamhpFields"/>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}AmppFamhpFMDFields" minOccurs="0"/>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}AmppFamhpReferences"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmppFamhpType", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", propOrder = {
    "authorisationNr",
    "orphan",
    "leafletLink",
    "spcLink",
    "rmaPatientLink",
    "rmaProfessionalLink",
    "parallelCircuit",
    "parallelDistributor",
    "packMultiplier",
    "packAmount",
    "packDisplayValue",
    "status",
    "gtin",
    "dhpcLink",
    "prescriptionNameFamhp",
    "endOfCommercialization",
    "endOfCommercializationReason",
    "endOfCommercializationAdditionalInformation",
    "endOfCommercializationImpact",
    "endOfCommercializationPresumedEndDate",
    "rmaKeyMessages",
    "unitDose",
    "legalBasis",
    "unitOfPresentation",
    "conditionalApproval",
    "genericPrescriptionRequired",
    "informationPerUnit",
    "fmdProductCode",
    "fmdInScope",
    "antiTemperingDevicePresent",
    "atcCode",
    "deliveryModusCode",
    "deliveryModusSpecificationCode",
    "distributorCompanyActorNr"
})
@XmlSeeAlso({
    AddAmppFamhpType.class
})
public class AmppFamhpType
    extends AmppKeyType
{

    @XmlElement(name = "AuthorisationNr", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", required = true)
    protected String authorisationNr;
    @XmlElement(name = "Orphan", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected boolean orphan;
    @XmlElement(name = "LeafletLink", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Text255Type leafletLink;
    @XmlElement(name = "SpcLink", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Text255Type spcLink;
    @XmlElement(name = "RmaPatientLink", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Text255Type rmaPatientLink;
    @XmlElement(name = "RmaProfessionalLink", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Text255Type rmaProfessionalLink;
    @XmlElement(name = "ParallelCircuit", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    @XmlSchemaType(name = "integer")
    protected Integer parallelCircuit;
    @XmlElement(name = "ParallelDistributor", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected String parallelDistributor;
    @XmlElement(name = "PackMultiplier", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Short packMultiplier;
    @XmlElement(name = "PackAmount", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected PackAmountType packAmount;
    @XmlElement(name = "PackDisplayValue", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Text255Type packDisplayValue;
    @XmlElement(name = "Status", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", required = true)
    @XmlSchemaType(name = "string")
    protected AmpStatusType status;
    @XmlElement(name = "GTIN", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected String gtin;
    @XmlElement(name = "DHPCLink", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Text255Type dhpcLink;
    @XmlElement(name = "PrescriptionNameFamhp", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Text255Type prescriptionNameFamhp;
    @XmlElement(name = "EndOfCommercialization", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected TextType endOfCommercialization;
    @XmlElement(name = "EndOfCommercializationReason", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected TextType endOfCommercializationReason;
    @XmlElement(name = "EndOfCommercializationAdditionalInformation", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected TextType endOfCommercializationAdditionalInformation;
    @XmlElement(name = "EndOfCommercializationImpact", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected TextType endOfCommercializationImpact;
    @XmlElement(name = "EndOfCommercializationPresumedEndDate", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endOfCommercializationPresumedEndDate;
    @XmlElement(name = "RMAKeyMessages", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected TextType rmaKeyMessages;
    @XmlElement(name = "UnitDose", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Boolean unitDose;
    @XmlElement(name = "LegalBasis", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected TextType legalBasis;
    @XmlElement(name = "UnitOfPresentation", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected QuantityType unitOfPresentation;
    @XmlElement(name = "ConditionalApproval", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Boolean conditionalApproval;
    @XmlElement(name = "GenericPrescriptionRequired", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Boolean genericPrescriptionRequired;
    @XmlElement(name = "InformationPerUnit", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Boolean informationPerUnit;
    @XmlElement(name = "FMDProductCode", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected List<String> fmdProductCode;
    @XmlElement(name = "FMDInScope", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Boolean fmdInScope;
    @XmlElement(name = "AntiTemperingDevicePresent", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected Boolean antiTemperingDevicePresent;
    @XmlElement(name = "AtcCode", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected List<String> atcCode;
    @XmlElement(name = "DeliveryModusCode", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", required = true)
    protected String deliveryModusCode;
    @XmlElement(name = "DeliveryModusSpecificationCode", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected String deliveryModusSpecificationCode;
    @XmlElement(name = "DistributorCompanyActorNr", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected String distributorCompanyActorNr;

    /**
     * Gets the value of the authorisationNr property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAuthorisationNr() {
        return authorisationNr;
    }

    /**
     * Sets the value of the authorisationNr property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAuthorisationNr(String value) {
        this.authorisationNr = value;
    }

    /**
     * Gets the value of the orphan property.
     *
     */
    public boolean isOrphan() {
        return orphan;
    }

    /**
     * Sets the value of the orphan property.
     *
     */
    public void setOrphan(boolean value) {
        this.orphan = value;
    }

    /**
     * Gets the value of the leafletLink property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getLeafletLink() {
        return leafletLink;
    }

    /**
     * Sets the value of the leafletLink property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setLeafletLink(Text255Type value) {
        this.leafletLink = value;
    }

    /**
     * Gets the value of the spcLink property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getSpcLink() {
        return spcLink;
    }

    /**
     * Sets the value of the spcLink property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setSpcLink(Text255Type value) {
        this.spcLink = value;
    }

    /**
     * Gets the value of the rmaPatientLink property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getRmaPatientLink() {
        return rmaPatientLink;
    }

    /**
     * Sets the value of the rmaPatientLink property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setRmaPatientLink(Text255Type value) {
        this.rmaPatientLink = value;
    }

    /**
     * Gets the value of the rmaProfessionalLink property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getRmaProfessionalLink() {
        return rmaProfessionalLink;
    }

    /**
     * Sets the value of the rmaProfessionalLink property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setRmaProfessionalLink(Text255Type value) {
        this.rmaProfessionalLink = value;
    }

    /**
     * Gets the value of the parallelCircuit property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getParallelCircuit() {
        return parallelCircuit;
    }

    /**
     * Sets the value of the parallelCircuit property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setParallelCircuit(Integer value) {
        this.parallelCircuit = value;
    }

    /**
     * Gets the value of the parallelDistributor property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParallelDistributor() {
        return parallelDistributor;
    }

    /**
     * Sets the value of the parallelDistributor property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParallelDistributor(String value) {
        this.parallelDistributor = value;
    }

    /**
     * Gets the value of the packMultiplier property.
     *
     * @return
     *     possible object is
     *     {@link Short }
     *
     */
    public Short getPackMultiplier() {
        return packMultiplier;
    }

    /**
     * Sets the value of the packMultiplier property.
     *
     * @param value
     *     allowed object is
     *     {@link Short }
     *
     */
    public void setPackMultiplier(Short value) {
        this.packMultiplier = value;
    }

    /**
     * Gets the value of the packAmount property.
     *
     * @return
     *     possible object is
     *     {@link PackAmountType }
     *
     */
    public PackAmountType getPackAmount() {
        return packAmount;
    }

    /**
     * Sets the value of the packAmount property.
     *
     * @param value
     *     allowed object is
     *     {@link PackAmountType }
     *
     */
    public void setPackAmount(PackAmountType value) {
        this.packAmount = value;
    }

    /**
     * Gets the value of the packDisplayValue property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getPackDisplayValue() {
        return packDisplayValue;
    }

    /**
     * Sets the value of the packDisplayValue property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setPackDisplayValue(Text255Type value) {
        this.packDisplayValue = value;
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
     * Gets the value of the gtin property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getGTIN() {
        return gtin;
    }

    /**
     * Sets the value of the gtin property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setGTIN(String value) {
        this.gtin = value;
    }

    /**
     * Gets the value of the dhpcLink property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getDHPCLink() {
        return dhpcLink;
    }

    /**
     * Sets the value of the dhpcLink property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setDHPCLink(Text255Type value) {
        this.dhpcLink = value;
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
     * Gets the value of the endOfCommercialization property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getEndOfCommercialization() {
        return endOfCommercialization;
    }

    /**
     * Sets the value of the endOfCommercialization property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setEndOfCommercialization(TextType value) {
        this.endOfCommercialization = value;
    }

    /**
     * Gets the value of the endOfCommercializationReason property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getEndOfCommercializationReason() {
        return endOfCommercializationReason;
    }

    /**
     * Sets the value of the endOfCommercializationReason property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setEndOfCommercializationReason(TextType value) {
        this.endOfCommercializationReason = value;
    }

    /**
     * Gets the value of the endOfCommercializationAdditionalInformation property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getEndOfCommercializationAdditionalInformation() {
        return endOfCommercializationAdditionalInformation;
    }

    /**
     * Sets the value of the endOfCommercializationAdditionalInformation property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setEndOfCommercializationAdditionalInformation(TextType value) {
        this.endOfCommercializationAdditionalInformation = value;
    }

    /**
     * Gets the value of the endOfCommercializationImpact property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getEndOfCommercializationImpact() {
        return endOfCommercializationImpact;
    }

    /**
     * Sets the value of the endOfCommercializationImpact property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setEndOfCommercializationImpact(TextType value) {
        this.endOfCommercializationImpact = value;
    }

    /**
     * Gets the value of the endOfCommercializationPresumedEndDate property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getEndOfCommercializationPresumedEndDate() {
        return endOfCommercializationPresumedEndDate;
    }

    /**
     * Sets the value of the endOfCommercializationPresumedEndDate property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setEndOfCommercializationPresumedEndDate(XMLGregorianCalendar value) {
        this.endOfCommercializationPresumedEndDate = value;
    }

    /**
     * Gets the value of the rmaKeyMessages property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getRMAKeyMessages() {
        return rmaKeyMessages;
    }

    /**
     * Sets the value of the rmaKeyMessages property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setRMAKeyMessages(TextType value) {
        this.rmaKeyMessages = value;
    }

    /**
     * Gets the value of the unitDose property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isUnitDose() {
        return unitDose;
    }

    /**
     * Sets the value of the unitDose property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setUnitDose(Boolean value) {
        this.unitDose = value;
    }

    /**
     * Gets the value of the legalBasis property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getLegalBasis() {
        return legalBasis;
    }

    /**
     * Sets the value of the legalBasis property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setLegalBasis(TextType value) {
        this.legalBasis = value;
    }

    /**
     * Gets the value of the unitOfPresentation property.
     *
     * @return
     *     possible object is
     *     {@link QuantityType }
     *
     */
    public QuantityType getUnitOfPresentation() {
        return unitOfPresentation;
    }

    /**
     * Sets the value of the unitOfPresentation property.
     *
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *
     */
    public void setUnitOfPresentation(QuantityType value) {
        this.unitOfPresentation = value;
    }

    /**
     * Gets the value of the conditionalApproval property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isConditionalApproval() {
        return conditionalApproval;
    }

    /**
     * Sets the value of the conditionalApproval property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setConditionalApproval(Boolean value) {
        this.conditionalApproval = value;
    }

    /**
     * Gets the value of the genericPrescriptionRequired property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isGenericPrescriptionRequired() {
        return genericPrescriptionRequired;
    }

    /**
     * Sets the value of the genericPrescriptionRequired property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setGenericPrescriptionRequired(Boolean value) {
        this.genericPrescriptionRequired = value;
    }

    /**
     * Gets the value of the informationPerUnit property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isInformationPerUnit() {
        return informationPerUnit;
    }

    /**
     * Sets the value of the informationPerUnit property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setInformationPerUnit(Boolean value) {
        this.informationPerUnit = value;
    }

    /**
     * Gets the value of the fmdProductCode property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fmdProductCode property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFMDProductCode().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     *
     *
     */
    public List<String> getFMDProductCode() {
        if (fmdProductCode == null) {
            fmdProductCode = new ArrayList<String>();
        }
        return this.fmdProductCode;
    }

    /**
     * Gets the value of the fmdInScope property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isFMDInScope() {
        return fmdInScope;
    }

    /**
     * Sets the value of the fmdInScope property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setFMDInScope(Boolean value) {
        this.fmdInScope = value;
    }

    /**
     * Gets the value of the antiTemperingDevicePresent property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isAntiTemperingDevicePresent() {
        return antiTemperingDevicePresent;
    }

    /**
     * Sets the value of the antiTemperingDevicePresent property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setAntiTemperingDevicePresent(Boolean value) {
        this.antiTemperingDevicePresent = value;
    }

    /**
     * Gets the value of the atcCode property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the atcCode property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAtcCode().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     *
     *
     */
    public List<String> getAtcCode() {
        if (atcCode == null) {
            atcCode = new ArrayList<String>();
        }
        return this.atcCode;
    }

    /**
     * Gets the value of the deliveryModusCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDeliveryModusCode() {
        return deliveryModusCode;
    }

    /**
     * Sets the value of the deliveryModusCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDeliveryModusCode(String value) {
        this.deliveryModusCode = value;
    }

    /**
     * Gets the value of the deliveryModusSpecificationCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDeliveryModusSpecificationCode() {
        return deliveryModusSpecificationCode;
    }

    /**
     * Sets the value of the deliveryModusSpecificationCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDeliveryModusSpecificationCode(String value) {
        this.deliveryModusSpecificationCode = value;
    }

    /**
     * Gets the value of the distributorCompanyActorNr property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDistributorCompanyActorNr() {
        return distributorCompanyActorNr;
    }

    /**
     * Sets the value of the distributorCompanyActorNr property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDistributorCompanyActorNr(String value) {
        this.distributorCompanyActorNr = value;
    }

}
