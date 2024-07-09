AppConfig에서 @Configuration 어노테이션을 빼면 어떤 문제점이 발생하냐?
일단 싱글톤이 깨짐.
그리고
MemberServiceImpl에서 private final MemberRepository memberRepository; 로 선언된 애가 있는데,
얘는 스프링 빈이 아님.
그냥 내가 직접 private final MemberRepository memberRepository = new MemoryMemberRepository; 로 넣는거랑 똑같음.
스프링 컨테이너가 관리하지 않는 애임.
그리고 orderService에 있는 애도 마찬가지임.
왜냐하면, 기존에 우리는 CGLIB을 통해서 어떻게 했냐면,
만약에 이미 스프링 컨테이너에 얘가 있으면, 그 컨테이너에서 찾아서 반환을 해줬었음.
그래서 싱글톤도 보장이 되고, 스프링 컨테이너에 있는 애도 가져오는게 보장이 됐었는데,
지금처럼 AppConfig에서 쌩자로 자바 코드를 호출해버리면, 얘는 어떤 코드냐면,
memberService()를 호출하면, new MemberServiceImpl(memberRepository())가 호출되면서,
memberRepository()를 호출함.
그러면 결국 memberRepository()의 반환값인 new MemoryMemberRepository()를 호출하므로
memberService()에서 반환값이 return new MemberServiceImpl(new MemoryMemberRepository()); 로 치환이 될거임.
이거는 스프링 컨테이너에서 관리해주는 빈이 아니고, 내가 직접 new해준 애랑 똑같은 거임
그래서 얘는 스프링 컨테이너에 관리가 안됨.
그래서 싱글톤으로 사용하려던 memberRepository()를 보면 ide에서 경고를 줌.
메소드 어노테이션 빈이 직접적으로 콜을 했다. 이런식으로