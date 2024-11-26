package com.cloudl.cloudle.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Marks this class as a JPA entity
@Table(name = "meeting") // Maps to the "meetings" table
public class Meeting {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate primary key
    private Long id;
    private String name;
    private String email;

    @Column(unique = true)
    private String meetingCode;
    private boolean ongoing;
    private LocalDateTime timestamp;
}
