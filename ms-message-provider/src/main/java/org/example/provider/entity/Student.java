package org.example.provider.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    private int id;

    private String name;

    private int age;

    private String hobby;
}