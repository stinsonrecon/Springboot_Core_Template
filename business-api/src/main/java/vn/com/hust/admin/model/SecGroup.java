package vn.com.hust.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "SEC_GROUP")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecGroup implements Serializable {
    @Id
    @Column(name = "GROUP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(name = "GROUP_CODE")
    private String groupCode;

    @Column(name = "GROUP_NAME")
    private String groupName;

    @Column(name = "PARENT_GROUP_ID")
    private Long parentGroupId;

    @Column(name = "STATUS")
    private Long status;

    @Column(name = "DESCRIPTION")
    private String description;

    @JsonIgnore
    @Transient
    private String parentName;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @Builder.Default
    @JoinTable(name = "SEC_GROUP_SCHEDULE",
            joinColumns = @JoinColumn(name = "GROUP_ID"),
            inverseJoinColumns = @JoinColumn(name = "SCHEDULE_ID")
    )
    private List<SecSchedule> SecSchedule = new ArrayList<>();
}
