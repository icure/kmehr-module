//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.consultation;

import org.taktik.icure.be.ehealth.samws.v2.core.VmpKeyType;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ConsultVmpType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ConsultVmpType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:core}VmpKeyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultTextType"/>
 *         &lt;element name="Abbreviation" type="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultTextType"/>
 *         &lt;element name="Wada" type="{urn:be:fgov:ehealth:samws:v2:consultation}WadaType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="CommentedClassification" type="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultCommentedClassificationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="VmpGroup" type="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultVmpGroupType"/>
 *         &lt;element name="Vtm" type="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultVtmType" minOccurs="0"/>
 *         &lt;element name="VmpComponent" type="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultVmpComponentType" maxOccurs="unbounded"/>
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
@XmlType(name = "ConsultVmpType", propOrder = {
    "name",
    "abbreviation",
    "wadas",
    "commentedClassifications",
    "vmpGroup",
    "vtm",
    "vmpComponents"
})
public class ConsultVmpType
    extends VmpKeyType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "Name", required = true)
    protected ConsultTextType name;
    @XmlElement(name = "Abbreviation", required = true)
    protected ConsultTextType abbreviation;
    @XmlElement(name = "Wada")
    protected List<WadaType> wadas;
    @XmlElement(name = "CommentedClassification")
    protected List<ConsultCommentedClassificationType> commentedClassifications;
    @XmlElement(name = "VmpGroup", required = true)
    protected ConsultVmpGroupType vmpGroup;
    @XmlElement(name = "Vtm")
    protected ConsultVtmType vtm;
    @XmlElement(name = "VmpComponent", required = true)
    protected List<ConsultVmpComponentType> vmpComponents;
    @XmlAttribute(name = "StartDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlAttribute(name = "EndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link ConsultTextType }
     *
     */
    public ConsultTextType getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link ConsultTextType }
     *
     */
    public void setName(ConsultTextType value) {
        this.name = value;
    }

    /**
     * Gets the value of the abbreviation property.
     *
     * @return
     *     possible object is
     *     {@link ConsultTextType }
     *
     */
    public ConsultTextType getAbbreviation() {
        return abbreviation;
    }

    /**
     * Sets the value of the abbreviation property.
     *
     * @param value
     *     allowed object is
     *     {@link ConsultTextType }
     *
     */
    public void setAbbreviation(ConsultTextType value) {
        this.abbreviation = value;
    }

    /**
     * Gets the value of the wadas property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wadas property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWadas().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WadaType }
     *
     *
     */
    public List<WadaType> getWadas() {
        if (wadas == null) {
            wadas = new ArrayList<WadaType>();
        }
        return this.wadas;
    }

    /**
     * Gets the value of the commentedClassifications property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commentedClassifications property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommentedClassifications().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConsultCommentedClassificationType }
     *
     *
     */
    public List<ConsultCommentedClassificationType> getCommentedClassifications() {
        if (commentedClassifications == null) {
            commentedClassifications = new ArrayList<ConsultCommentedClassificationType>();
        }
        return this.commentedClassifications;
    }

    /**
     * Gets the value of the vmpGroup property.
     *
     * @return
     *     possible object is
     *     {@link ConsultVmpGroupType }
     *
     */
    public ConsultVmpGroupType getVmpGroup() {
        return vmpGroup;
    }

    /**
     * Sets the value of the vmpGroup property.
     *
     * @param value
     *     allowed object is
     *     {@link ConsultVmpGroupType }
     *
     */
    public void setVmpGroup(ConsultVmpGroupType value) {
        this.vmpGroup = value;
    }

    /**
     * Gets the value of the vtm property.
     *
     * @return
     *     possible object is
     *     {@link ConsultVtmType }
     *
     */
    public ConsultVtmType getVtm() {
        return vtm;
    }

    /**
     * Sets the value of the vtm property.
     *
     * @param value
     *     allowed object is
     *     {@link ConsultVtmType }
     *
     */
    public void setVtm(ConsultVtmType value) {
        this.vtm = value;
    }

    /**
     * Gets the value of the vmpComponents property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vmpComponents property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVmpComponents().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConsultVmpComponentType }
     *
     *
     */
    public List<ConsultVmpComponentType> getVmpComponents() {
        if (vmpComponents == null) {
            vmpComponents = new ArrayList<ConsultVmpComponentType>();
        }
        return this.vmpComponents;
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
