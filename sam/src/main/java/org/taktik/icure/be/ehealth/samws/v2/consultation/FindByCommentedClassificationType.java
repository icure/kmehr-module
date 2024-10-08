//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.consultation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/**
 * <p>Java class for FindByCommentedClassificationType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FindByCommentedClassificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="CommentedClassificationCode" type="{urn:be:fgov:ehealth:samws:v2:core}String10Type"/>
 *         &lt;element name="AnyNamePart" type="{urn:be:fgov:ehealth:samws:v2:core}SearchStringType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindByCommentedClassificationType", propOrder = {
    "anyNamePart",
    "commentedClassificationCode"
})
public class FindByCommentedClassificationType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "AnyNamePart")
    protected String anyNamePart;
    @XmlElement(name = "CommentedClassificationCode")
    protected String commentedClassificationCode;

    /**
     * Gets the value of the anyNamePart property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAnyNamePart() {
        return anyNamePart;
    }

    /**
     * Sets the value of the anyNamePart property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAnyNamePart(String value) {
        this.anyNamePart = value;
    }

    /**
     * Gets the value of the commentedClassificationCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCommentedClassificationCode() {
        return commentedClassificationCode;
    }

    /**
     * Sets the value of the commentedClassificationCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCommentedClassificationCode(String value) {
        this.commentedClassificationCode = value;
    }

}
