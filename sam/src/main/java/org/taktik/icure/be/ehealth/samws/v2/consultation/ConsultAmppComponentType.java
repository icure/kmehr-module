//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.consultation;

import org.taktik.icure.be.ehealth.samws.v2.actual.common.AmppComponentKeyType;
import org.taktik.icure.be.ehealth.samws.v2.actual.common.ContentTypeType;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ConsultAmppComponentType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ConsultAmppComponentType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:actual:common}AmppComponentKeyType">
 *       &lt;sequence>
 *         &lt;element name="ContentType" type="{urn:be:fgov:ehealth:samws:v2:actual:common}ContentTypeType"/>
 *         &lt;element name="ContentMultiplier" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="PackSpecification" type="{urn:be:fgov:ehealth:samws:v2:core}String255Type" minOccurs="0"/>
 *         &lt;element name="DeviceType" type="{urn:be:fgov:ehealth:samws:v2:consultation}DeviceTypeType" minOccurs="0"/>
 *         &lt;element name="PackagingClosure" type="{urn:be:fgov:ehealth:samws:v2:consultation}PackagingClosureType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PackagingMaterial" type="{urn:be:fgov:ehealth:samws:v2:consultation}PackagingMaterialType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PackagingType" type="{urn:be:fgov:ehealth:samws:v2:consultation}PackagingTypeType" minOccurs="0"/>
 *         &lt;element name="AmppComponentEquivalent" type="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultAmppComponentEquivalentType" maxOccurs="unbounded"/>
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
@XmlType(name = "ConsultAmppComponentType", propOrder = {
    "contentType",
    "contentMultiplier",
    "packSpecification",
    "deviceType",
    "packagingClosures",
    "packagingMaterials",
    "packagingType",
    "amppComponentEquivalents"
})
public class ConsultAmppComponentType
    extends AmppComponentKeyType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "ContentType", required = true)
    @XmlSchemaType(name = "string")
    protected ContentTypeType contentType;
    @XmlElement(name = "ContentMultiplier")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger contentMultiplier;
    @XmlElement(name = "PackSpecification")
    protected String packSpecification;
    @XmlElement(name = "DeviceType")
    protected DeviceTypeType deviceType;
    @XmlElement(name = "PackagingClosure")
    protected List<PackagingClosureType> packagingClosures;
    @XmlElement(name = "PackagingMaterial")
    protected List<PackagingMaterialType> packagingMaterials;
    @XmlElement(name = "PackagingType")
    protected PackagingTypeType packagingType;
    @XmlElement(name = "AmppComponentEquivalent", required = true)
    protected List<ConsultAmppComponentEquivalentType> amppComponentEquivalents;
    @XmlAttribute(name = "StartDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlAttribute(name = "EndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;

    /**
     * Gets the value of the contentType property.
     *
     * @return
     *     possible object is
     *     {@link ContentTypeType }
     *
     */
    public ContentTypeType getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     *
     * @param value
     *     allowed object is
     *     {@link ContentTypeType }
     *
     */
    public void setContentType(ContentTypeType value) {
        this.contentType = value;
    }

    /**
     * Gets the value of the contentMultiplier property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getContentMultiplier() {
        return contentMultiplier;
    }

    /**
     * Sets the value of the contentMultiplier property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setContentMultiplier(BigInteger value) {
        this.contentMultiplier = value;
    }

    /**
     * Gets the value of the packSpecification property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPackSpecification() {
        return packSpecification;
    }

    /**
     * Sets the value of the packSpecification property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPackSpecification(String value) {
        this.packSpecification = value;
    }

    /**
     * Gets the value of the deviceType property.
     *
     * @return
     *     possible object is
     *     {@link DeviceTypeType }
     *
     */
    public DeviceTypeType getDeviceType() {
        return deviceType;
    }

    /**
     * Sets the value of the deviceType property.
     *
     * @param value
     *     allowed object is
     *     {@link DeviceTypeType }
     *
     */
    public void setDeviceType(DeviceTypeType value) {
        this.deviceType = value;
    }

    /**
     * Gets the value of the packagingClosures property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the packagingClosures property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPackagingClosures().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PackagingClosureType }
     *
     *
     */
    public List<PackagingClosureType> getPackagingClosures() {
        if (packagingClosures == null) {
            packagingClosures = new ArrayList<PackagingClosureType>();
        }
        return this.packagingClosures;
    }

    /**
     * Gets the value of the packagingMaterials property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the packagingMaterials property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPackagingMaterials().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PackagingMaterialType }
     *
     *
     */
    public List<PackagingMaterialType> getPackagingMaterials() {
        if (packagingMaterials == null) {
            packagingMaterials = new ArrayList<PackagingMaterialType>();
        }
        return this.packagingMaterials;
    }

    /**
     * Gets the value of the packagingType property.
     *
     * @return
     *     possible object is
     *     {@link PackagingTypeType }
     *
     */
    public PackagingTypeType getPackagingType() {
        return packagingType;
    }

    /**
     * Sets the value of the packagingType property.
     *
     * @param value
     *     allowed object is
     *     {@link PackagingTypeType }
     *
     */
    public void setPackagingType(PackagingTypeType value) {
        this.packagingType = value;
    }

    /**
     * Gets the value of the amppComponentEquivalents property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the amppComponentEquivalents property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAmppComponentEquivalents().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConsultAmppComponentEquivalentType }
     *
     *
     */
    public List<ConsultAmppComponentEquivalentType> getAmppComponentEquivalents() {
        if (amppComponentEquivalents == null) {
            amppComponentEquivalents = new ArrayList<ConsultAmppComponentEquivalentType>();
        }
        return this.amppComponentEquivalents;
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
