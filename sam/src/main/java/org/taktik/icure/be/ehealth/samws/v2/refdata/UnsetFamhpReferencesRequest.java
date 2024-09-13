//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.refdata;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for UnsetFamhpReferencesRequestType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UnsetFamhpReferencesRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:refdata}UnsetFamhpMainEntities"/>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:refdata}UnsetFamhpAdditionalEntities"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnsetFamhpReferencesRequestType", propOrder = {
    "atcClassifications",
    "deliveryModuses",
    "deliveryModusSpecifications",
    "deviceTypes",
    "packagingClosures",
    "packagingMaterials",
    "packagingTypes",
    "pharmaceuticalForms",
    "routeOfAdministrations",
    "substances",
    "standardForms",
    "standardRoutes",
    "standardSubstances",
    "standardUnits"
})
@XmlRootElement(name = "UnsetFamhpReferencesRequest")
public class UnsetFamhpReferencesRequest
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "AtcClassification")
    protected List<AtcClassificationKeyType> atcClassifications;
    @XmlElement(name = "DeliveryModus")
    protected List<DeliveryModusKeyType> deliveryModuses;
    @XmlElement(name = "DeliveryModusSpecification")
    protected List<DeliveryModusSpecificationKeyType> deliveryModusSpecifications;
    @XmlElement(name = "DeviceType")
    protected List<DeviceTypeKeyType> deviceTypes;
    @XmlElement(name = "PackagingClosure")
    protected List<PackagingClosureKeyType> packagingClosures;
    @XmlElement(name = "PackagingMaterial")
    protected List<PackagingMaterialKeyType> packagingMaterials;
    @XmlElement(name = "PackagingType")
    protected List<PackagingTypeKeyType> packagingTypes;
    @XmlElement(name = "PharmaceuticalForm")
    protected List<PharmaceuticalFormKeyType> pharmaceuticalForms;
    @XmlElement(name = "RouteOfAdministration")
    protected List<RouteOfAdministrationKeyType> routeOfAdministrations;
    @XmlElement(name = "Substance")
    protected List<SubstanceKeyType> substances;
    @XmlElement(name = "StandardForm")
    protected List<StandardFormKeyFamhpType> standardForms;
    @XmlElement(name = "StandardRoute")
    protected List<StandardRouteKeyFamhpType> standardRoutes;
    @XmlElement(name = "StandardSubstance")
    protected List<StandardSubstanceKeyFamhpType> standardSubstances;
    @XmlElement(name = "StandardUnit")
    protected List<StandardUnitKeyFamhpType> standardUnits;

    /**
     * Gets the value of the atcClassifications property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the atcClassifications property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAtcClassifications().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AtcClassificationKeyType }
     *
     *
     */
    public List<AtcClassificationKeyType> getAtcClassifications() {
        if (atcClassifications == null) {
            atcClassifications = new ArrayList<AtcClassificationKeyType>();
        }
        return this.atcClassifications;
    }

    /**
     * Gets the value of the deliveryModuses property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deliveryModuses property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeliveryModuses().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeliveryModusKeyType }
     *
     *
     */
    public List<DeliveryModusKeyType> getDeliveryModuses() {
        if (deliveryModuses == null) {
            deliveryModuses = new ArrayList<DeliveryModusKeyType>();
        }
        return this.deliveryModuses;
    }

    /**
     * Gets the value of the deliveryModusSpecifications property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deliveryModusSpecifications property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeliveryModusSpecifications().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeliveryModusSpecificationKeyType }
     *
     *
     */
    public List<DeliveryModusSpecificationKeyType> getDeliveryModusSpecifications() {
        if (deliveryModusSpecifications == null) {
            deliveryModusSpecifications = new ArrayList<DeliveryModusSpecificationKeyType>();
        }
        return this.deliveryModusSpecifications;
    }

    /**
     * Gets the value of the deviceTypes property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deviceTypes property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeviceTypes().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeviceTypeKeyType }
     *
     *
     */
    public List<DeviceTypeKeyType> getDeviceTypes() {
        if (deviceTypes == null) {
            deviceTypes = new ArrayList<DeviceTypeKeyType>();
        }
        return this.deviceTypes;
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
     * {@link PackagingClosureKeyType }
     *
     *
     */
    public List<PackagingClosureKeyType> getPackagingClosures() {
        if (packagingClosures == null) {
            packagingClosures = new ArrayList<PackagingClosureKeyType>();
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
     * {@link PackagingMaterialKeyType }
     *
     *
     */
    public List<PackagingMaterialKeyType> getPackagingMaterials() {
        if (packagingMaterials == null) {
            packagingMaterials = new ArrayList<PackagingMaterialKeyType>();
        }
        return this.packagingMaterials;
    }

    /**
     * Gets the value of the packagingTypes property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the packagingTypes property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPackagingTypes().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PackagingTypeKeyType }
     *
     *
     */
    public List<PackagingTypeKeyType> getPackagingTypes() {
        if (packagingTypes == null) {
            packagingTypes = new ArrayList<PackagingTypeKeyType>();
        }
        return this.packagingTypes;
    }

    /**
     * Gets the value of the pharmaceuticalForms property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pharmaceuticalForms property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPharmaceuticalForms().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PharmaceuticalFormKeyType }
     *
     *
     */
    public List<PharmaceuticalFormKeyType> getPharmaceuticalForms() {
        if (pharmaceuticalForms == null) {
            pharmaceuticalForms = new ArrayList<PharmaceuticalFormKeyType>();
        }
        return this.pharmaceuticalForms;
    }

    /**
     * Gets the value of the routeOfAdministrations property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the routeOfAdministrations property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRouteOfAdministrations().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RouteOfAdministrationKeyType }
     *
     *
     */
    public List<RouteOfAdministrationKeyType> getRouteOfAdministrations() {
        if (routeOfAdministrations == null) {
            routeOfAdministrations = new ArrayList<RouteOfAdministrationKeyType>();
        }
        return this.routeOfAdministrations;
    }

    /**
     * Gets the value of the substances property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the substances property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubstances().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubstanceKeyType }
     *
     *
     */
    public List<SubstanceKeyType> getSubstances() {
        if (substances == null) {
            substances = new ArrayList<SubstanceKeyType>();
        }
        return this.substances;
    }

    /**
     * Gets the value of the standardForms property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the standardForms property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStandardForms().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StandardFormKeyFamhpType }
     *
     *
     */
    public List<StandardFormKeyFamhpType> getStandardForms() {
        if (standardForms == null) {
            standardForms = new ArrayList<StandardFormKeyFamhpType>();
        }
        return this.standardForms;
    }

    /**
     * Gets the value of the standardRoutes property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the standardRoutes property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStandardRoutes().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StandardRouteKeyFamhpType }
     *
     *
     */
    public List<StandardRouteKeyFamhpType> getStandardRoutes() {
        if (standardRoutes == null) {
            standardRoutes = new ArrayList<StandardRouteKeyFamhpType>();
        }
        return this.standardRoutes;
    }

    /**
     * Gets the value of the standardSubstances property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the standardSubstances property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStandardSubstances().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StandardSubstanceKeyFamhpType }
     *
     *
     */
    public List<StandardSubstanceKeyFamhpType> getStandardSubstances() {
        if (standardSubstances == null) {
            standardSubstances = new ArrayList<StandardSubstanceKeyFamhpType>();
        }
        return this.standardSubstances;
    }

    /**
     * Gets the value of the standardUnits property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the standardUnits property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStandardUnits().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StandardUnitKeyFamhpType }
     *
     *
     */
    public List<StandardUnitKeyFamhpType> getStandardUnits() {
        if (standardUnits == null) {
            standardUnits = new ArrayList<StandardUnitKeyFamhpType>();
        }
        return this.standardUnits;
    }

}
