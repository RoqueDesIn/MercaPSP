package Common;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlReader {
	void XMLReader () {}
	
	/**
	 * 
	 * @return
	 */
	public String leeXml() {
		String email = null;
		String clave = null;
		
		try {
            // Generador de constructor de objetos XML
			// Creo una instancia de DocumentBuilderFactory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// Creo un documentBuilder
			DocumentBuilder builder = factory.newDocumentBuilder();
			// Obtengo el documento, a partir del XML
			Document documento = builder.parse(new File(Constantes.XMLFILE));
			// Cojo todas las etiquetas Appconfig del documento
			NodeList listaConfig = documento.getElementsByTagName("AppConfig");  
			
			// Recorro las etiquetas
			for (int i = 0; i < listaConfig.getLength(); i++) {
                // Cojo el nodo actual
                Node nodo = listaConfig.item(i);
                // Compruebo si el nodo es un elemento
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    // Lo transformo a Element
                    Element e = (Element) nodo;
                    // Obtengo sus hijos
                    NodeList hijos = e.getChildNodes();
                    // Recorro sus hijos
                    for (int j = 0; j < hijos.getLength(); j++) {
                        // Obtengo al hijo actual
                        Node hijo = hijos.item(j);
                        // Compruebo si es un nodo
                        if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                            // Muestro el contenido
                            System.out.println("Propiedad: " + hijo.getNodeName()
                                    + ", Valor: " + hijo.getTextContent());
                            if (hijo.getNodeName().contentEquals("EmailEmergencia")) email = hijo.getTextContent();
                            if (hijo.getNodeName().contentEquals("clave")) clave = hijo.getTextContent();
                        }
 
                    }
                    System.out.println("");
                }
 
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
			ex.printStackTrace();
        }
		
		return email + ";" + clave;
	}
}
