//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AmpComponentLinkToVirtualType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AmpComponentLinkToVirtualType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:actual:common}AmpComponentKeyType">
 *       &lt;sequence>
 *         &lt;element name="VmpComponent" type="{urn:be:fgov:ehealth:samws:v2:core}VmpComponentKeyType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmpComponentLinkToVirtualType", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", propOrder = {
    "vmpComponent"
})
public class AmpComponentLinkToVirtualType
    extends AmpComponentKeyType
{

    @XmlElement(name = "VmpComponent", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", required = true)
    protected VmpComponentKeyType vmpComponent;

    /**
     * Gets the value of the vmpComponent property.
     *
     * @return
     *     possible object is
     *     {@link VmpComponentKeyType }
     *
     */
    public VmpComponentKeyType getVmpComponent() {
        return vmpComponent;
    }

    /**
     * Sets the value of the vmpComponent property.
     *
     * @param value
     *     allowed object is
     *     {@link VmpComponentKeyType }
     *
     */
    public void setVmpComponent(VmpComponentKeyType value) {
        this.vmpComponent = value;
    }

}
