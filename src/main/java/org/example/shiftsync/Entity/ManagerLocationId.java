package org.example.shiftsync.Entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ManagerLocationId implements Serializable {
    private Long manager;
    private Long location;
}
