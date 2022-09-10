package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDisountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    /**
     * 문제
     * */
    // 할인 정책을 변경하기 위해 이 클래스의 코드를 고쳐야 함
    // discountPolicy(인터페이스)뿐이 아니고, 이외 FixDiscountPolicy, RateDiscountPolicy 등 구체 클레스에도 의존하고 있다
    // 추상과 구체에 둘 다 의존 -> DIP 위반

    /**
     * 해결
     * */
    // 인터페이스에만 의존하도록 설계 변경


    private final MemberRepository memberRepository;
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 코드 변경 -> OCP 위반
    private final DiscountPolicy discountPolicy; // DIP는 지킴

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice); // nullPointExecption -> 누군가 대신 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야 함
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
