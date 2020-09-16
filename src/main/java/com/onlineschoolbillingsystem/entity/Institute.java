package com.onlineschoolbillingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Institute")
public class Institute {
    /**
     * This class represents an Institute who makes use of this system to manage its students,
     * billing, and other record keeping matters.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long instituteId;

    @Column(unique = true)
    private String email;

    private char[] password;
    private String address;
    private String contact;

    public Institute(String email, char[] password, String address, String contact) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.contact = contact;
    }

}
