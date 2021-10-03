package com.sangam.sangam.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.sangam.sangam.utils.StringPrefixedSequenceIdGenerator;

import lombok.Data;

@Entity
@Table(name = "table_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Data
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_seq")
    @GenericGenerator(
        name = "table_seq", 
        strategy = "com.sangam.sangam.utils.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "USR"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_COLUMN_PARAM, value = "next_user_id")
        })
    @Column(name="t_u_id")
    private String id;

    @Column(name="name")
    private String name;

    @Email
    @Column(name="email")
    private String email;

    @Column(name="image_url")
    private String imageUrl;

}
