package com.netcracker.marshaller;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import entitites.Parsable;

/**
 * 
 * @author acersanya
 *	JAXB parser. Parse entitites into xml
 */
public class JAXBParser {

	public JAXBParser() {
	}

	/**
	 * 
	 * @param path output file
	 * @param object which implement Parsable interface
	 */
	public <T extends Parsable> void parse(String path, T object) {

		File file;
		JAXBContext jaxbContext;
		Marshaller jaxbMarshaller;
		
		try {
			file = new File(path);
			jaxbContext = JAXBContext.newInstance(object.getClass());
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(object, file);
			jaxbMarshaller.marshal(object, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
