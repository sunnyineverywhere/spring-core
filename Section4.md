# 4. 스프링 컨테이너와 스프링 빈

```java
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
```java
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