package org.example.irctc.entities;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="trains")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long trainId;
    private String trainNo;

    @ElementCollection
    private List<String>stations;

    @ElementCollection
    @MapKeyColumn(name = "station")
    @Column(name = "time")
    private Map<String,String>stationTimes;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "train")
    private List<Ticket> tickets = new ArrayList<>();

}
