
package at.fhooe.mc.jaxws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für dummyData complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="dummyData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mTime" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dummyData", propOrder = {
    "mText",
    "mTime"
})
public class DummyData {

    protected String mText;
    protected long mTime;

    /**
     * Ruft den Wert der mText-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMText() {
        return mText;
    }

    /**
     * Legt den Wert der mText-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMText(String value) {
        this.mText = value;
    }

    /**
     * Ruft den Wert der mTime-Eigenschaft ab.
     * 
     */
    public long getMTime() {
        return mTime;
    }

    /**
     * Legt den Wert der mTime-Eigenschaft fest.
     * 
     */
    public void setMTime(long value) {
        this.mTime = value;
    }

}
