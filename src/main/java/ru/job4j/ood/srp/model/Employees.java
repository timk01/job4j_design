package ru.job4j.ood.srp.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * оттаткиваться от представления !!! (если тест тесты = замечаельно)
 * <employees> </employees> - стало быть, рут. РАБОТНИКИ. но - ставится только над классом, классом содержащим поле
 * ПРИ ЭТОМ: MemoryStore - вообще к работникам отношения не имеет (не ДТО), на него забиваем
 * JAXB = "1 XML root = 1 Java class", или "нужен XML → делай отдельный класс под XML"
 * ИЛИ: Java-классы = схема XML (или нужно создать новый класс)
 * ДАЛЕЕ: потому что существующий эмплоии (один) - описывает ОДНОГО работника - это элемент
 * <элемент - ВЛОЖЕННЫЕ типы, а-ля <name>John Doe</name>. ЭМПЛОИИ, оыбчный, буквально вложенный в рут.
 * да, это лист. но элемент-то один (однотипный элемент, считй)
 * аттрибут - с тегами <employee name="John Doe" salary="5000.0"/> или <employee name="John Doe"/>
 */

@XmlRootElement(name = "employees")
public class Employees {

    @XmlElement(name = "employee")
    private List<Employee> employees;

    public Employees(List<Employee> employees) {
        this.employees = employees;
    }

    public Employees() {
    }
}
