package com.sparkbyexamples.scala

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.{Schema, SchemaFactory, Validator}
import org.xml.sax.{ErrorHandler, SAXParseException}

object XMLValidatorUsingXSD extends App {

  validate("test.xml","test.xsd")

  def validate(xmlFile:String,xsdFile:String): Unit ={
    var exceptions = List[String]()
    try {
      val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
      val url = ClassLoader.getSystemResource(xsdFile)
      val schema: Schema = schemaFactory.newSchema(new StreamSource(url.openStream()))

      val validator: Validator = schema.newValidator()
      validator.setErrorHandler(new ErrorHandler() {
        @Override
        def warning(exception:SAXParseException){

          exceptions = exception.getMessage  :: exceptions
        }
        @Override
        def fatalError(exception:SAXParseException ) {
          exceptions = exception.getMessage  :: exceptions
        }
        @Override
        def error(exception:SAXParseException ) {
          exceptions = exception.getMessage  :: exceptions
        }
      });

      val xmlUrl = ClassLoader.getSystemResource(xmlFile)
      validator.validate(new StreamSource(xmlUrl.openStream()))
      exceptions.foreach(e=>{
        println(e)
      })
    }catch {
      case ex => {
        ex.getMessage
      }
    }
  }

}
