//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.actual.common;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for AmpComponentFamhpType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AmpComponentFamhpType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:actual:common}AmpComponentKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}AmpComponentFamhpReferences"/>
 *         &lt;element name="RealActualIngredient" type="{urn:be:fgov:ehealth:samws:v2:actual:common}AddRealActualIngredientType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmpComponentFamhpType", propOrder = {
    "pharmaceuticalFormCodes",
    "routeOfAdministrationCodes",
    "realActualIngredients"
})
@XmlSeeAlso({
    AddAmpComponentFamhpType.class
})
public class AmpComponentFamhpType
    extends AmpComponentKeyType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "PharmaceuticalFormCode", required = true)
    protected List<String> pharmaceuticalFormCodes;
    @XmlElement(name = "RouteOfAdministrationCode", required = true)
    protected List<String> routeOfAdministrationCodes;
    @XmlElement(name = "RealActualIngredient", required = true)
    protected List<AddRealActualIngredientType> realActualIngredients;

    /**
     * Gets the value of the pharmaceuticalFormCodes property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pharmaceuticalFormCodes property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPharmaceuticalFormCodes().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     *
     *
     */
    public List<String> getPharmaceuticalFormCodes() {
        if (pharmaceuticalFormCodes == null) {
            pharmaceuticalFormCodes = new ArrayList<String>();
        }
        return this.pharmaceuticalFormCodes;
    }

    /**
     * Gets the value of the routeOfAdministrationCodes property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the routeOfAdministrationCodes property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRouteOfAdministrationCodes().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     *
     *
     */
    public List<String> getRouteOfAdministrationCodes() {
        if (routeOfAdministrationCodes == null) {
            routeOfAdministrationCodes = new ArrayList<String>();
        }
        return this.routeOfAdministrationCodes;
    }

    /**
     * Gets the value of the realActualIngredients property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the realActualIngredients property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRealActualIngredients().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddRealActualIngredientType }
     *
     *
     */
    public List<AddRealActualIngredientType> getRealActualIngredients() {
        if (realActualIngredients == null) {
            realActualIngredients = new ArrayList<AddRealActualIngredientType>();
        }
        return this.realActualIngredients;
    }

}
