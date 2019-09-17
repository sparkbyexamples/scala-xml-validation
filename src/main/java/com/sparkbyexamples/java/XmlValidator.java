package com.sparkbyexamples.java;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.net.URL;

public class XmlValidator {

    public static void main(String args[]){

        validate("test.xml","test.xsd");
    }

    public static void validate(String xmlFile, String schemaFile) {

        try {
            SchemaFactory schemaFactory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL url = ClassLoader.getSystemResource(schemaFile);
            Schema schema = schemaFactory.newSchema(new StreamSource(url.openStream()));
            Validator validator = schema.newValidator();
            validator.setErrorHandler(new ErrorHandler() {

                public void error(SAXParseException exception){
                    System.out.println("Error => "+ exception.getMessage());
                }

                public void warning(SAXParseException exception){
                    System.out.println("Warning => "+ exception.getMessage());
                }

                public void fatalError(SAXParseException exception){
                    System.out.println("Fatal => "+ exception.getMessage());
                }
            });
            URL xmlUrl = ClassLoader.getSystemResource(xmlFile);
            validator.validate(new StreamSource(xmlUrl.openStream()));

        } catch (SAXException e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
