# 스프링 시큐리티 용어와 흐름

## **🔐 스프링 시큐리티란?**

```less
Spring Security는 Spring 기반의 애플리케이션의 보안(인증과 권한, 인가 등)을 담당하는 스프링 하위 프레임워크
```

스프링 시큐리티의 핵심 역할은 **Authentication Manager(인증 매니저)를 통해서 이루어진다.**

Authentication Provider는 인증 매니저가 어떻게 동작해야 하는지 결정하고

⇒ 최종적으로 실제 인증은 UserDetailsService에 의해서 이루어진다.

### 필터와 필터 체이닝

스프링 시큐리티에서 필터는 서블릿이나 JSP 에서 사용하는 필터와 같은 개념입니다만

스프링 시큐리티에서는 스프링의 빈과 연동할 수 있는 구조로 설계되어 있다.

일반적인 필터는 스프링의 빈을 사용할 수 없기 때문에 별도의 클래스를 상속받는 형태가 많다.

스프링 시큐리티의 내부에는 여러 개의 필터가 Filte Chain이라는 구조로 Request를 처리하게 된다.

앞에서 실행되었던 로그를 살펴보면 15개 정도의 필터가 동작하는 것을 볼 수 있다. 개발 시에 필터를 확장하고 설정하면 스프링 시큐리티를 이용해서 다양한 형태의 로그인 처리가 가능하게 된다.

- 실제 스프링 시큐리티 내부에 사용되는 주요 필터는 다음과 같다.
    - ChannelProcessingFilter
    - SecurityContextPersistenceFilter
    - ConcurrentSessionFilter
    - UsernamePasswordAuthenticationFilter
    - SecurityContextHolderAwareRequestFilter
    - RemeberMeAuthenticationFilter
    - AnonymousAuthenticationFilter
    - ExceptionTranslationFilter
    - FilterSecurityInterceptor

- 인증을 위한 AuthenticationManager

필터의 핵심적인 동작은 AuthenticatoinManager를 통해서 인증(Authentication)이라는 타입의 객체로 작업을 하게 된다.

흥미롭게도 AuthenticationManager가 가진 인증 처리 메서드는 파라미터도 Authentication 타입으로 받고 리턴 타입 역시 Authentacation이다.

아이디 패스워드로 실제 사용자에 대해서 검증하는 행위(주민등록증으로 그 사람을 검증)는 AuthenticationManager(인증 매니저)를 통해 이루어진다.

실제 동작에서 전달되는 파라미터는 UsernamePasswordAuthenticatoinToken과 같이 토큰이라는 이름으로 전달된다. 이 사실이 의미하는 바는 스프링 시큐리티 필터의 주요 역할이 인증 관련된 정보를 토큰이라는 객체로 만들어서 전달한다는 의미이다.


- 매니저는 인증처리 방법 (데이터베이스를 이용 혹은 메모리 상에 있는 정보를 활용할 것인지)AuthenticationProvider로 처리한다.

provider는 전달되는 토큰의 타입을 처리할 수 있는 존재인지 확인하고 이를 통해서 authenticate()를 수행한다. 그렇기 떄문에 다음 그림처럼 다양한 인증처리를 할 수 있는 객체들을 가지는 구조가 된다.

### 스프링 시큐리티 커스터마이징

별도의 설정 없이도 기본적으로 스프링 시큐리티는 동작하지만 개발 시 적절하게 인증 방식이나 접근 제한을 지정할 수 있어야 한다.

SecurityConfig 클래스를 이용해서 약간의 override로 이러한 동작을 제어할 수 있다.

### [반드시 필요한 PasswordEncoder]

가장 먼저 설정이 필요한 것은 PasswordEncoder이라는 객체이다. PasswordEncoder는 말 그대로 패스워드를 인코딩하는 것인제 주 목적은 역시 패스워드를 암호화하는 것이다. 스프링부트 2.0부터는 인증을 위해서 반드시 PasswordEncoder를 지정해야만 한다

여러 종류의 PasswordEncoder를 제공하고 있는데 그중에서도 가장 많이 사용하는 것은 BCryptPasswordEncoder 클래스이다. 이는 bcrypt라는 해시 함수를 이용해서 패스워드를 암호화하는 목적으로 설계된 클래스이다. BCryptPasswordEncoder로 암호화된  패스워드는 다시 원래대로 복호화가 불가능하고 매번 암호화가 된 값도 다르게 된다.(길이는 동일)

대신에 특정한 문자열이 암호화된 결과인지만을 확인할 수 있기 때문에 원본 내용을 볼 수 없으므로 최근에 가장 많이 사용되고 있다. SecurityConfig에는 @Bean을 이용해서 BCryptPasswordEncoder를 지정한다.

```java
@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
		@Bean
		PasswordEncoder passwordEncoder()
		{
				return new BCryptPasswordEncoder();
		}
}
```

### [CSRF 설정]

스프링 시큐리티는 기본적으로 CSRF(Cross Site Request Forgery- 크로스 사이트 요청 위조)라는 공격을 방어하기 위해서 임의의 값을 만들어서 이를 GET 방식을 제외한 모든 요청 방식 (POST, PUT, DELETE) 등에 포함시켜야만 정상적인 동작이 가능하게 된다.

CSRF 공격은 ‘사이트간 요청 위조’라고 변역할 수 있다. 서버에서 받아들이는 정보가 특별히 사전 조건을 검증하지 않는다는 담을 이용하는 공격 방식

CSRF 를 이용해서 단순히 게시물의 조회수를 늘리는 등의 조작부터 피해자의 계정을 이용하는 다양한 공격이 가능하다.

ex) 인터넷에 A라는 사이트가 있다. 이 사이트에는 특정 사용자의 등급을 변경하는 URI가 존재한다는 것을 알았고 해당 URI에는 약간의 파라미터가 필요하다는 것을 알았다고 가정하자

공격자는 A 사이트의 관리자가 자주 방문하는 B 사이트에 img 태그나 form 태그를 이용해 URI를 추가한 게시물을 작성한다. 그리고 관리자가 공격자가 작성한 게시물을 보게 되면 URI가 호출되고 공격자는 admin 등급의 사용자로 변경된다. A 사이트 입장에서는 어떤 출처에서 호출이 되었는지 따지지 않기 때문에 로그인한 사용자의 정상적인 요청으로 해석한다.

그래서 실제 서버에서 받아온 뷰 페이지가 아닌 위조된 페이지에서 서버에 요청을 보내는 행위를 걸러내기 위한 방법이 다양하게 존재하는데 그 중 한 방법이 CSRF Token이라는 것을 사용해, 서버에 요청을 올린 페이지가 실제 서버에서 발행한 뷰 페이지가 맞는지 확인하는 것이다. 이 토큰은 세션당 하나씩 생성된다.

한편,  일반적인 세션을 이용하거 form 태그를 이용하는 방식에서는 CSRF 토큰이 보안상으로 권장되지만 REST 방식 등에서 매번 CSRF 토큰의 값을 알아내야 하는 불편함이 있기 때문에 경우에 따라서는 CSRF 토큰의 발행을 하지 않는 경우도 있다.

[CSRF 토큰 비활성화]

HttpSecurity의 csrf() 메서드를 이용해서 CSRF 토큰을 발행하지 않도록 설정하기 위해 다음의 설정을 추가한다.

```java
@Overide
protected void configure(HttpSecurity http) throws Exception
{
		..
		http.formLogin();
		http.csrf().disable();
}
```

위와 같이 disable()을 지정한 후에 만들어지는 로그인 페이지의 코드는 CSRF 토큰이 필요하지 않은 상태로 만들어지는 것을 볼 수 있다. 이렇게 비활성화하는 이유는 외부에서 REST 방식으로 이용할 수 있는 보안 설정을 다루기 위해서이다.

- 로그아웃 같은 경우 CSRF 토큰을 사용할 때는 반드시 POST 방식으로만 로그아웃을 처리해야 한다. 하지만 CSRF 토큰을 비활성화시키면 GET 방식으로 로그아웃 처리가 가능하다.

### 프로젝트에 스프링 시큐리티 적용

[주의사항]

- 스프링 시큐리티에서는 회원이나 계정에 대해서 User라는 용어를 사용한다. User 라는 단어를 사용할 때 주의해야하기 때문에 Member 와 같이 다른 이름을 사용하자
- 회원 아이디라는 용어 대신에 username이라는 단어를 사용한다. 스프링 시큐리티에서는 username이라는 단어 자체가 회원을 구별할 수 있는 식별 데이터를 의미한다. 문자열로 처리하는 점은 같지만 일반적으로 사용하는 회원의 이름이 아니라 id에 해당하는 것이라고 볼 수 있다.
- username 과 password 를 동시에 사용하지 않는다. 스프링 시큐리티는 UserDetailService를 이용해서 회원의 존재만을 우선적으로 가져오고, 이후에 password가 틀리면 ‘Bad Cridential(잘못된 자격증명)’이라는 결과를 만들어 낸다.(인증)
- 사용자의 username과 password로 인증 과정이 끝나면 원하는 자원(URL)에 접근할 수 있는 적절한 권한이 있는지를 확인하고 인가 과정을 실행한다. 이 과정에서 ‘Access Denied’와 같은 결과가 만들어 진다.

위와 같은 점들을 처리하는 가장 핵심적인 부분은 UserDetailService이다.  여기에는 loadUserByUsername() 이라는 하나의 메서드를 가지고 있다. 이는 username 이라는 회원 아이디 같은 값으로 회원 정보를 가져온다. 이 메서드의 리턴타입은 UserDetails라는 타입인데 이를 통해 다음과 같은 정보를 알아낼 수 있다.

- getAuthorities()  : 사용자가 가지는 권한에 대한 정보
- getPassword() : 인증을 마무리하기 위한 패스워드 정보
- getUsername() : 인증에 필요한 아이디와 같은 정보
- 계정 만료 여부 - 더이상 사용이 불가능한 계정인지 알 수 있는 정보
- 계정 잠김 여부 - 현재 계정의 잠김 여부

이를 처리하기 위해서 ClubMemeber를 처리할 수 있는 2가지 방법이 있다.

(1) 기존 DTO 클래스에 UseDetails 인터페이스를 구현하는 방법

(2) DTO와 같은 개념으로 별도의 클래스를 구성하고 이를 활용하는 방법

이 책의 저자의 개인적인 선호 방식은 별도의 클래스를 하나 구성하고 이를 DTO처럼 사용하는 방식이라고 했다.

UserDetails 인터페이스를 별도의 클래스로 구성하는 것은 어려운 일은 아니지만, 인터페이스를 구현한 별도의 클래스가 있기 때문에 이를 사용하는 것이 더 수월하다고 말한다.

```java
@Log4j2
@Service
public class ClubUserDetailsService implements UserDetailsService
{
		@Overide
		public UserDetails loadUserByUsername(String username) throws
UsernameNotFoundException {
				log.info("ClubUserDetailsService loadUserByUsername" + username);
				return null;
}

```

여기서 @Service를 사용해서 자동으로 스프링에서 빈으로 처리될 수 있도록 되어 있다. 이렇게 빈으로 등록되면 스프링 시큐리티에서 UserDetailsService로 인식한다. 그래서 SecurityConfig 부분에서 임시로 configure(AuthenticationManagerBuilder auth) 부분으로 직접 설정한 부분이 있다면 삭제해야한다.  아니면ClubUserDetailsService를 주입받아서 구성하는 코드를 작성하는 방법도 있다.