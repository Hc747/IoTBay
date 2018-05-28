/**
 * Provide a package level XML Namespace declaration so that
 * every class within the 'au.edu.uts.wsd.model' is consistent.
 */
@XmlSchema(
        xmlns = {
            @XmlNs(prefix = "wsd", namespaceURI = "https://au.edu.uts.wsd/pet-manager")
        },
        namespace = "https://au.edu.uts.wsd/pet-manager",
        location = "https://au.edu.uts.wsd/pet-manager PetOwners.xsd",
        elementFormDefault = XmlNsForm.QUALIFIED
)
package au.edu.uts.wsd.model;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;

