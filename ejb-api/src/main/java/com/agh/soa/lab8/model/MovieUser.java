package com.agh.soa.lab8.model;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieUser {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private String name;
  private Short age;
  private String avatar;

  @OneToMany(fetch = EAGER)
  private List<Movie> movies;
}
