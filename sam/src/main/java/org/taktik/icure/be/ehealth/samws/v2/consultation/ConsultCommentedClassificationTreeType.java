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
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ConsultCommentedClassificationTreeType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ConsultCommentedClassificationTreeType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultCommentedClassificationType">
 *       &lt;sequence>
 *         &lt;element name="CommentedClassification" type="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultCommentedClassificationTreeType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultCommentedClassificationTreeType", propOrder = {
    "commentedClassifications"
})
public class ConsultCommentedClassificationTreeType
    extends ConsultCommentedClassificationType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "CommentedClassification")
    protected List<ConsultCommentedClassificationTreeType> commentedClassifications;

    /**
     * Gets the value of the commentedClassifications property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commentedClassifications property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommentedClassifications().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConsultCommentedClassificationTreeType }
     *
     *
     */
    public List<ConsultCommentedClassificationTreeType> getCommentedClassifications() {
        if (commentedClassifications == null) {
            commentedClassifications = new ArrayList<ConsultCommentedClassificationTreeType>();
        }
        return this.commentedClassifications;
    }

}
