package com.kosa.chanzipup.domain.notice;

import com.kosa.chanzipup.domain.account.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String email;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Notice(String title, String content, String email, Member member) {
        this.title = title;
        this.content = content;
        this.email = email;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        this.member = member;
    }

    // 작성자의 닉네임을 가져오는 메소드
    public String getAuthorNickName() {
        return member != null ? member.getNickName() : null;
    }

    // 제목 수정 메서드
    public void updateTitle(String title) {
        this.title = title;
    }

    // 내용 수정 메서드
    public void updateContent(String content) {
        this.content = content;
    }
}
