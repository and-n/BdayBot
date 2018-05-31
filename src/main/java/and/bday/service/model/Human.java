package and.bday.service.model;

import org.joda.time.DateTime;

import java.util.Objects;

public class Human {

    private final String name;
    private final String surname;
    private final String bdayDate;


    public Human(String name, String surname, DateTime bdayDate) {
        this.name = name;
        this.surname = surname;
        this.bdayDate = bdayDate.toString();
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBdayDate() {
        return bdayDate;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", bdayDate='" + bdayDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(name, human.name) &&
                Objects.equals(surname, human.surname);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, surname, bdayDate);
    }
}
