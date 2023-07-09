package vn.com.hust.identityservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Group {
    @Id
    private Long groupId;

    private int childLevel;
}
