package services;

import models.Person;
import com.mysema.query.jpa.impl.JPAQuery;
import models.Person;
import models.QPerson;
import play.db.jpa.JPA;

/**
 * Created by thaodang on 27/4/16.
 */


public class PersonService {
    public Person getPerson(String userName) {
        // Normal hibernate way
        //Person person = JPA.em().find(Person.class, new Long(1));

        //Query DSL way
        QPerson qPerson = QPerson.person;
        JPAQuery query = new JPAQuery(JPA.em());
        return query.from(qPerson)
                .where(qPerson.userName.eq(userName))
                .uniqueResult(qPerson);
    }
}
