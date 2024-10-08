//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.consultation;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;


/**
 * <p>Java class for FindVtmRequestType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FindVtmRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="FindByTherapeuticMoiety" type="{urn:be:fgov:ehealth:samws:v2:consultation}FindByTherapeuticMoietyType"/>
 *         &lt;element name="FindByProduct" type="{urn:be:fgov:ehealth:samws:v2:consultation}FindByVirtualProductType"/>
 *       &lt;/choice>
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang use="required""/>
 *       &lt;attribute name="searchDate" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindVtmRequestType", propOrder = {
    "findByProduct",
    "findByTherapeuticMoiety"
})
@XmlRootElement(name = "FindVtmRequest")
public class FindVtmRequest
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "FindByProduct")
    protected FindByVirtualProductType findByProduct;
    @XmlElement(name = "FindByTherapeuticMoiety")
    protected FindByTherapeuticMoietyType findByTherapeuticMoiety;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace", required = true)
    protected String lang;
    @XmlAttribute(name = "searchDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar searchDate;

    /**
     * Gets the value of the findByProduct property.
     *
     * @return
     *     possible object is
     *     {@link FindByVirtualProductType }
     *
     */
    public FindByVirtualProductType getFindByProduct() {
        return findByProduct;
    }

    /**
     * Sets the value of the findByProduct property.
     *
     * @param value
     *     allowed object is
     *     {@link FindByVirtualProductType }
     *
     */
    public void setFindByProduct(FindByVirtualProductType value) {
        this.findByProduct = value;
    }

    /**
     * Gets the value of the findByTherapeuticMoiety property.
     *
     * @return
     *     possible object is
     *     {@link FindByTherapeuticMoietyType }
     *
     */
    public FindByTherapeuticMoietyType getFindByTherapeuticMoiety() {
        return findByTherapeuticMoiety;
    }

    /**
     * Sets the value of the findByTherapeuticMoiety property.
     *
     * @param value
     *     allowed object is
     *     {@link FindByTherapeuticMoietyType }
     *
     */
    public void setFindByTherapeuticMoiety(FindByTherapeuticMoietyType value) {
        this.findByTherapeuticMoiety = value;
    }

    /**
     * Gets the value of the lang property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the searchDate property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getSearchDate() {
        return searchDate;
    }

    /**
     * Sets the value of the searchDate property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setSearchDate(XMLGregorianCalendar value) {
        this.searchDate = value;
    }

}
