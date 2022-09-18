# 4. 스프링 컨테이너와 스프링 빈

```java:
ApplicationContext applicationContext = new ApplicationConfigApplicationContext(AppConfig.context);
```
ApplicationContext: 스프링 컨테이너, 인터페이스
스프링 컨테이너는 XML 기반으로 만듦 + 애노테이션 기반 자바 설정 클래스로 만들 수 있음

### 스프링 컨테이너의 생성 과정
1. 스프링 컨테이너 생성(AppConfig.class)

스프링 빈 저장소에 빈 이름과 빈 객체가 등록되머
스프링 빈 저장소의 구성 전보를 활용하여 AppConfig.java를 구성정보로 지정

2. 스프링 빈 등록

@Bean을 사용하여 스프링 빈 저장소에 빈 등록
빈 이름은 일반적으로 메서드 이름 사용(부여하는 것도 가능), 유니크하게 사용(중복 불가)

3. 스프링 빈 의존관계 설정

설정 정보를 참고하여 의존관계를 주입(DI): 빈에서 의존하는 클래스를 확인하여 주입
동적인 인스턴스 객체 연결관계를 스프링이 연결함

### 컨테이너에 등록된 빈 조회
```java:
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + " object = " + bean);
            }
```
BeanDeafinition.ROLE_APPLICATION: 개발을 위해 등록한 빈
BeanDefinition.ROLE_INFRASTRUCTURE: 스프링에서 기본적으로 등록된 빈

### 스프링 빈 조회 - 상속관계
모든 자바 객체의 최고 부모인 'Object'타입으로 조회 -> 모든 스프링 빈을 조회
부모 타입으로 조회하면 자식 타입도 다 끌려나온다(= 함께 조회한다)

### BeanFactory와 ApplicationContext
**BeanFactory**
- 스프링 컨테이너의 최상위 인터페이스
- 스프링 빈을 관리하고 조회하는 역할: 지금까지 우리가 사용했던 대부분의 기능은 BeanFactory가 하는 일(getBean() 같은 것)

**ApplicationContext**
- BeanFactory의 기능을 모두 상속받아서 제공
- 애플리케이션을 개발할 때 빈을 관리하고 조회하는 기능 + 수많은 부가기능: BeanFactory 이외에도 메세징, 환경설정, 이벤트 퍼블리셔, 리소스 로더 등도 상속 
    - MessageSource: 메세지 소스를 활용한 국제화 기능
    - EnvironmentCapable: 로컬, 개발, 운영 등을 구분해서 처리
    - ApplicationEventPublisher: 이벤트를 발행하고 구독하는 모델을 편리하게 지원
    - ResourceLoader: 파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회

=> 부가기능이 포함된 ApplicationContext를 사용(BeanFactory, ApplicationContext 등을 스프링 컨테이너라 칭함)

### 다양한 설정 형식 지원 - 자바 코드, XML
스프링 컨테이너는 다양한 형식의 설정 정보를 받아들일 수 있게 유연하게 설계(자바 코드, XML, Groovy 등등)
- 자바 코드: AnnotationConfig, ApplicationContext(AppConfig.class)
- xml: (요즘은 거의 안 씀) xml이라는 파일을 따로 작성하여 설정해줌. 컴파일 없이 빈 설정 정보를 변경할 수 있다는 장점


### 스프링 빈 설정 메타 정보 - BeanDefinition
- 다양한 설정 형식 지원: BeanDefinition이라는 추상화
  - 역할과 구현을 개념적으로 나눈 것(xml, 자바 코드...)
- ApplicationContext를 구현한 AnnotationConfigApplicationContext
  - AnnotatedBeanDefinitionReader: AppConfig 설정 정보를 읽는다(설정 정보 메타데이터처럼) -> BeanDefinition을 생성
- Bean 설정 정보를 읽어서 BeanDefinition으로 추상화하여 사용(실제로 정의해서 사용하는 경우는 거의 없음)