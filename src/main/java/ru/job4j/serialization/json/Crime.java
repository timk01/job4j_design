package ru.job4j.serialization.json;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.StringJoiner;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Crime {
    private String definition;
    private int duration;
    private boolean conditionalRelease;

    public Crime() {
    }

    public Crime(String definition, int duration, boolean conditionalRelease) {
        this.definition = definition;
        this.duration = duration;
        this.conditionalRelease = conditionalRelease;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crime crime = (Crime) o;
        return duration == crime.duration && conditionalRelease == crime.conditionalRelease && Objects.equals(definition, crime.definition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(definition, duration, conditionalRelease);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Crime.class.getSimpleName() + "[", "]")
                .add("definition='" + definition + "'")
                .add("duration=" + duration)
                .add("conditionalRelease=" + conditionalRelease)
                .toString();
    }
}
