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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VmpComponentFullDataType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="VmpComponentFullDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:core}VmpComponentKeyType">
 *       &lt;sequence>
 *         &lt;element name="Data" type="{urn:be:fgov:ehealth:samws:v2:export}VmpComponentDataType" maxOccurs="unbounded"/>
 *         &lt;element name="VirtualIngredient" type="{urn:be:fgov:ehealth:samws:v2:export}VirtualIngredientFullDataType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VmpComponentFullDataType", propOrder = {
    "data",
    "virtualIngredient"
})
public class VmpComponentFullDataType
    extends VmpComponentKeyType
{

    @XmlElement(name = "Data", required = true)
    protected List<VmpComponentDataType> data;
    @XmlElement(name = "VirtualIngredient")
    protected List<VirtualIngredientFullDataType> virtualIngredient;

    /**
     * Gets the value of the data property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the data property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getData().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VmpComponentDataType }
     *
     *
     */
    public List<VmpComponentDataType> getData() {
        if (data == null) {
            data = new ArrayList<VmpComponentDataType>();
        }
        return this.data;
    }

    /**
     * Gets the value of the virtualIngredient property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the virtualIngredient property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVirtualIngredient().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualIngredientFullDataType }
     *
     *
     */
    public List<VirtualIngredientFullDataType> getVirtualIngredient() {
        if (virtualIngredient == null) {
            virtualIngredient = new ArrayList<VirtualIngredientFullDataType>();
        }
        return this.virtualIngredient;
    }

}
