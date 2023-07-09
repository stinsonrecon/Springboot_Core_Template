package vn.com.hust.identityservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class GroupPosition implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "group_id")
    private Long groupId;

    @Id
    @Column(name = "position_id")
    private Long positionId;
}
