package com.brink.shared;

import com.brink.model.ableton.AbletonProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class AbletonProjectService {

    private static final Logger logger = LoggerFactory.getLogger(AbletonProjectService.class);

    public static AbletonProject convert2AbletonProject(String abletonXmlProjectFile) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AbletonProject.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (AbletonProject) jaxbUnmarshaller.unmarshal(new StringReader(abletonXmlProjectFile));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


}
