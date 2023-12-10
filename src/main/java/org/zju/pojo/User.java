package org.zju.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private String id;
    private String name;
    private Integer age;
    private String phone;
    private String password;
}
