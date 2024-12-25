package hongik.db.member;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "memberschedule")
public class MemberSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false) // 외래 키 설정
    private Member member; // Member와 관계 설정

    @Column(nullable = false) // Title 필드 매핑
    private String title;

    @Column(nullable = false) // Schedule 필드 매핑
    private LocalDate schedule;

    @Transient // 데이터베이스에 매핑하지 않음
    private Integer memberId;

    // Getter and Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMemberId() {
        // memberId 값을 반환할 때 member 객체의 ID를 가져옴
        return member != null ? member.getId() : null;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDate getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDate schedule) {
        this.schedule = schedule;
    }
}

