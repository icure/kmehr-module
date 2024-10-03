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
 * <p>Java class for NameExplanationFullDataType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="NameExplanationFullDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:chapteriv:submit}NameExplanationKeyType">
 *       &lt;sequence>
 *         &lt;element name="Data" type="{urn:be:fgov:ehealth:samws:v2:export}NameExplanationDataType" maxOccurs="unbounded"/>
 *         &lt;element name="NameTranslation" type="{urn:be:fgov:ehealth:samws:v2:export}NameTranslationFullDataType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NameExplanationFullDataType", propOrder = {
    "data",
    "nameTranslation"
})
public class NameExplanationFullDataType
    extends NameExplanationKeyType
{

    @XmlElement(name = "Data", required = true)
    protected List<NameExplanationDataType> data;
    @XmlElement(name = "NameTranslation")
    protected List<NameTranslationFullDataType> nameTranslation;

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
     * {@link NameExplanationDataType }
     *
     *
     */
    public List<NameExplanationDataType> getData() {
        if (data == null) {
            data = new ArrayList<NameExplanationDataType>();
        }
        return this.data;
    }

    /**
     * Gets the value of the nameTranslation property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nameTranslation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNameTranslation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameTranslationFullDataType }
     *
     *
     */
    public List<NameTranslationFullDataType> getNameTranslation() {
        if (nameTranslation == null) {
            nameTranslation = new ArrayList<NameTranslationFullDataType>();
        }
        return this.nameTranslation;
    }

}