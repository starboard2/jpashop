package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city",
                    column = @Column(name = "city")),
            @AttributeOverride(name = "street",
                    column = @Column(name = "street")),
            @AttributeOverride(name = "zipcode",
                    column = @Column(name = "zipcode"))
    })
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(getId(), member.getId()) &&
                Objects.equals(getName(), member.getName()) &&
                Objects.equals(getAddress(), member.getAddress()) &&
                Objects.equals(getOrders(), member.getOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAddress(), getOrders());
    }
}
