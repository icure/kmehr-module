//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.core;

import org.taktik.icure.be.ehealth.samws.v2.consultation.ConsultVmpComponentType;
import org.taktik.icure.be.ehealth.samws.v2.virtual.common.ChangeVmpComponentType;
import org.taktik.icure.be.ehealth.samws.v2.virtual.common.VmpComponentType;
import org.taktik.icure.be.samv2v5.entities.VmpComponentFullDataType;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * <p>Java class for VmpComponentKeyType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="VmpComponentKeyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="code" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}PositiveIntType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VmpComponentKeyType")
@XmlSeeAlso({
    VmpComponentFullDataType.class,
    ChangeVmpComponentType.class,
    VmpComponentType.class,
    ConsultVmpComponentType.class
})
public class VmpComponentKeyType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlAttribute(name = "code", required = true)
    protected int code;

    /**
     * Gets the value of the code property.
     *
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     */
    public void setCode(int value) {
        this.code = value;
    }

}
