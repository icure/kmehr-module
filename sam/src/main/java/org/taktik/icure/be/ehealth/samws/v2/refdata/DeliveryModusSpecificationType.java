//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.refdata;

import org.taktik.icure.be.ehealth.samws.v2.core.TextType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/**
 * <p>Java class for DeliveryModusSpecificationType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DeliveryModusSpecificationType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:refdata}DeliveryModusSpecificationKeyType">
 *       &lt;sequence>
 *         &lt;element name="Description" type="{urn:be:fgov:ehealth:samws:v2:core}TextType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliveryModusSpecificationType", propOrder = {
    "description"
})
public class DeliveryModusSpecificationType
    extends DeliveryModusSpecificationKeyType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "Description", required = true)
    protected TextType description;

    /**
     * Gets the value of the description property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setDescription(TextType value) {
        this.description = value;
    }

}
