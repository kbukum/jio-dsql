package org.oopdev.jio.dsql.api.test.tools.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Role extends BaseEntity {

    @Length(min = 2, max = 32)
    @NotEmpty
    @Column(length = 32, unique = true, nullable = false)
    private String code;

    @Length(min = 3, max = 50)
    @NotEmpty
    @Column(length = 50, nullable = false)
    private String name;

    public Role(){

    }

    public Role(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
