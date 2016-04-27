package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by leonard on 17.08.15.
 */
@Entity
public class Person {

  @Id
  @GeneratedValue
  private Long personId;

  @Column private String name;

  @Column private int age;

  public Person(final String name, final int age) {
    this.name = name;
    this.age = age;
  }

  public Person() {
  }

  public Long getPersonId() {
    return personId;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }
}
