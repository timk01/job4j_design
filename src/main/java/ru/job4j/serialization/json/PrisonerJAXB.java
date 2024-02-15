package ru.job4j.serialization.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.io.UsageLog4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class PrisonerJAXB {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) throws JAXBException {
        final Prisoner prisoner = new Prisoner(
                false,
                12348898789L,
                "Ivanov Ivan Ivanovich",
                new Crime(
                        "arson",
                        15,
                        true
                ),
                new float[]{167.2F, 109F}
        );

        String xml = "";
        try {
            JAXBContext context = JAXBContext.newInstance(Prisoner.class, Crime.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            try (StringWriter writer = new StringWriter()) {
                marshaller.marshal(prisoner, writer);
                xml = writer.getBuffer().toString();
                System.out.println(xml + System.lineSeparator());
            } catch (IOException e) {
                LOG.debug("IOException happened in StringWriter in PrisonerJAXB", e);
            }
            Unmarshaller unmarshaller = context.createUnmarshaller();
            try (StringReader reader = new StringReader(xml)) {
                Prisoner prisonerXml = (Prisoner) unmarshaller.unmarshal(reader);
                System.out.println(prisonerXml);
            }
        } catch (JAXBException e) {
            LOG.error("Error was encountered while creating the JAXBContext in PrisonerJAXB", e);
        }
    }
}
