package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)  // 조회 트랜잭션은 읽기전용이 성능이 좋음
@RequiredArgsConstructor // final 필드만 가지고 생성자를 자동으로 만들어줌
public class MemberService {

    // final 사용 : 생성자에서 값 설정 안하면 컴파일 단계에서 오류 체크할 수 있음
    private final MemberRepository memberRepository;

    //@RequiredArgsConstructor 사용으로 주석처리
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    @Transactional  // 메소드에 별도로 설정하면 클래스에서 설정한 어노테이션으로 동작 안함
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증

        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //Exception
        // 아래 로직은 동시 접근 시 중복 발생할 수 있음
        List<Member> findMembers = memberRepository.findByname(member.getName());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
