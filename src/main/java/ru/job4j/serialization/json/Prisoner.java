package ru.job4j.serialization.json;

import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Prisoner {

    @XmlAttribute
    private boolean sex;

    @XmlAttribute
    private long prisonerNumber;

    @XmlAttribute
    private String credentials;

    @XmlElement
    private Crime crime;

    @XmlElementWrapper(name = "articles")
    @XmlElement(name = "article")
    private float[] articleNumber;

    public Prisoner() {
    }

    public Prisoner(boolean sex, long prisonerNumber, String credentials, Crime crime, float[] articleNumber) {
        this.sex = sex;
        this.prisonerNumber = prisonerNumber;
        this.credentials = credentials;
        this.crime = crime;
        this.articleNumber = articleNumber;
    }

    public boolean getSex() {
        return sex;
    }

    public long getPrisonerNumber() {
        return prisonerNumber;
    }

    public String getCredentials() {
        return credentials;
    }

    public Crime getCrime() {
        return crime;
    }

    public float[] getArticleNumber() {
        return articleNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Prisoner prisoner = (Prisoner) o;
        return sex == prisoner.sex && prisonerNumber == prisoner.prisonerNumber && Objects.equals(credentials, prisoner.credentials) && Objects.equals(crime, prisoner.crime) && Arrays.equals(articleNumber, prisoner.articleNumber);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sex, prisonerNumber, credentials, crime);
        result = 31 * result + Arrays.hashCode(articleNumber);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Prisoner.class.getSimpleName() + "[", "]")
                .add("sex=" + sex)
                .add("prisonerNumber=" + prisonerNumber)
                .add("credentials='" + credentials + "'")
                .add("crime=" + crime)
                .add("articleNumber=" + Arrays.toString(articleNumber))
                .toString();
    }
}
