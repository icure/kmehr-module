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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExportChapterIVType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ExportChapterIVType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:export}VersionedExportType">
 *       &lt;sequence>
 *         &lt;element name="Paragraph" type="{urn:be:fgov:ehealth:samws:v2:export}ParagraphFullDataType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="QualificationList" type="{urn:be:fgov:ehealth:samws:v2:export}QualificationListFullDataType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="NameExplanation" type="{urn:be:fgov:ehealth:samws:v2:export}NameExplanationFullDataType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExportChapterIVType", propOrder = {
    "paragraph",
    "qualificationList",
    "nameExplanation"
})
@XmlRootElement(name = "ExportChapterIV")
public class ExportChapterIVType
    extends VersionedExportType
{

    @XmlElement(name = "Paragraph")
    protected List<ParagraphFullDataType> paragraph;
    @XmlElement(name = "QualificationList")
    protected List<QualificationListFullDataType> qualificationList;
    @XmlElement(name = "NameExplanation")
    protected List<NameExplanationFullDataType> nameExplanation;

    /**
     * Gets the value of the paragraph property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paragraph property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParagraph().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParagraphFullDataType }
     *
     *
     */
    public List<ParagraphFullDataType> getParagraph() {
        if (paragraph == null) {
            paragraph = new ArrayList<ParagraphFullDataType>();
        }
        return this.paragraph;
    }

    /**
     * Gets the value of the qualificationList property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualificationList property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualificationList().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualificationListFullDataType }
     *
     *
     */
    public List<QualificationListFullDataType> getQualificationList() {
        if (qualificationList == null) {
            qualificationList = new ArrayList<QualificationListFullDataType>();
        }
        return this.qualificationList;
    }

    /**
     * Gets the value of the nameExplanation property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nameExplanation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNameExplanation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameExplanationFullDataType }
     *
     *
     */
    public List<NameExplanationFullDataType> getNameExplanation() {
        if (nameExplanation == null) {
            nameExplanation = new ArrayList<NameExplanationFullDataType>();
        }
        return this.nameExplanation;
    }

}
