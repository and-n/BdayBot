package and.bday.service.model;

import org.joda.time.DateTime;

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

    public DateTime getBdayDate() {
        return DateTime.parse(bdayDate);
    }

}
