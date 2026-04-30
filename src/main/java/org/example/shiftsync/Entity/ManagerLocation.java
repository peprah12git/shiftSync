package org.example.shiftsync.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "manager_locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ManagerLocationId.class)
public class ManagerLocation {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
}
