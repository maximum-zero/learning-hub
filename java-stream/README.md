## 📚 함수형 프로그래밍

### 1. 함수형 인터페이스와 람다식

**함수형 인터페이스**는 단 하나의 추상 메서드(Single Abstract Method)만 가지며, **람다식**은 이 인터페이스의 구현체 역할을 합니다.

| 인터페이스 | 역할 | 추상 메서드 | 예시 |
| :--- | :--- | :--- | :--- |
| **Supplier<T>** | 값을 제공 | `T get()` | `() -> Math.random()` |
| **Consumer<T>** | 값을 받아 소비 | `void accept(T t)` | `(x) -> System.out.println(x)` |
| **Predicate<T>** | 조건 테스트  | `boolean test(T t)` | `x -> x > 0` |
| **Comparator<T>** | 두 값을 비교 | `int compare(T t1, T t2)` | `(u1, u2) -> u1.getId() - u2.getId()` |

---

### 2. 메서드 참조

**메서드 참조**는 람다식이 **기존 메서드를 단 하나만 호출**할 때, **`클래스::메서드`** 형태로 코드를 간결하게 줄이는 문법입니다.

| 유형 | 문법 | 예제 파일 |
| :--- | :--- | :--- |
| **정적 메서드** | `클래스::Static메서드` | `BasicMethodReferences` |
| **특정 객체 인스턴스** | `객체참조::인스턴스메서드` | `BasicMethodReferences` |
| **임의 객체 인스턴스** | `클래스::인스턴스메서드` | `BasicMethodReferences` |
| **생성자 참조** | `클래스::new` | `ConstructorReferenceFactory` |

---

### 3. 스트림 API (Stream API)

**스트림**은 **데이터 처리 연산을 지원하도록 소스에서 추출된 요소의 시퀀스**입니다.

컬렉션이나 배열 같은 **데이터 소스 위를 파이프라인**과 같으며, 데이터를 저장하지 않고 함수적 방식으로 처리하는 도구입니다.

| 특징 | 설명 |
| :--- | :--- |
| **Source** | 컬렉션, 배열 등 데이터의 출발점. |
| **Intermediate Operation** | 스트림을 새로운 스트림 반환. **지연 연산**으로 실행되지 않음. |
| **Terminal Operation** | 스트림을 닫고 결과를 반환. **실제 연산이 실행**됨. |

#### 중간 연산 (Intermediate Operations)

| 연산 | 역할 | 예제 파일 |
| :--- | :--- | :--- |
| **filter** | 조건(Predicate)에 맞는 요소만 선택 | `StreamFilterMap` |
| **map** | 요소를 다른 형태/값(Function)으로 변환 | `StreamFilterMap` |
| **flatMap** | 중첩된 구조(Stream in Stream)를 단일 스트림으로 평탄화 | `StreamFilterMap` |
| **sorted** | 정렬 조건(Comparator)에 따라 요소 정렬 | `StreamSortedDistinct` |
| **distinct** | 스트림에서 중복된 요소 제거 | `StreamSortedDistinct` |

#### 최종 연산 (Terminal Operations)

| 연산 | 역할 | 예제 파일 |
| :--- | :--- | :--- |
| **count** | 스트림의 요소 개수 계산 | `StreamAggregate` |
| **sum** | 숫자형 스트림의 합계 계산 | `StreamAggregate` |
| **max, min** | 최대/최소 값 검색 | `StreamAggregate` |
| **allMatch** | 모든 요소가 조건을 만족하는지 확인 | `StreamSearchMatch` |
| **anyMatch** | 하나라도 요소가 조건을 만족하는지 확인 | `StreamSearchMatch` |
| **findFirst, findAny** | 첫 번째 또는 임의의 요소 검색 | `StreamSearchMatch` |
| **reduce** | 스트림의 모든 요소를 단 하나의 결과로 축소 | `StreamReduce` |
| **collect (toMap)** | 스트림을 Map 형태로 수집 | `StreamCollectMap` |
| **collect (groupingBy)** | 스트림 요소를 특정 기준에 따라 그룹핑하여 Map으로 수집 | `StreamCollectGroup` |
| **collect (partitioningBy)** | 스트림 요소를 boolean 기준으로 두 그룹으로 분할하여 Map으로 수집 | `StreamCollectGroup` |

####  Optional

**Optional**는 스트림의 **최종 연산** 결과가 **값이 있을 수도, 없을 수도 있음**을 안전하게 나타내는 **특수 컨테이너**입니다.

`NullPointerException` 발생을 방지하기 위해 중간 연산과 최종 연산 사이에서 자주 활용됩니다.

| 메서드 | 역할 |
| :--- | :--- |
| **isPresent()** | 값이 존재하는지 확인 (boolean) |
| **orElse(), orElseGet()** | 값이 없을 때 기본값 제공 |
| **ifPresent()** | 값이 있을 때만 특정 Consumer 실행 |

#### Parallel Stream (병렬 처리)

**병렬 처리**는 멀티 스레드를 활용하여 데이터 처리량을 높이는 기법이며, **Stream API**에서는 **`parallel()`** 메서드를 통해 이를 가능하게 합니다.

* 내부적으로 **Fork/Join Pool**을 사용하여 전체 작업을 더 작은 작업으로 **분할**(Fork)하고, 각 작업을 병렬로 실행한 후 결과를 **병합**(Join)하여 최종 결과를 도출합니다.
* 병렬화 및 병합에 드는 비용이 단순한 연산이나 적은 데이터 양에서는 **순차 스트림보다 오히려 느리게** 만들 수 있습니다.
* 스트림 내부에서 **상태 변경**(State Mutation)을 시도할 경우, 여러 스레드가 동시에 접근하여 **동시성 문제**(Race Condition)를 일으킬 위험이 높습니다.

---

### 4. 함수형 프로그래밍 심화 개념

#### Scope, Currying, Closure

| 개념 | 정의 | 핵심 원리 |
| :--- | :--- | :--- |
| **Scope** | 변수에 접근할 수 있는 영역입니다. | Lexical Scope |
| **Currying** | 여러 매개변수를 중첩된 함수로 쪼개어 단계별로 나눠 받는 기술입니다. | Partial Application |
| **Closure** | 함수가 선언 시점의 환경(Scope)을 기억하여, 외부 함수 종료 후에도 상태를 보존하는 메커니즘입니다. | State Preservation |



#### Short-Circuiting (단축 평가)

논리 연산자 (&&, ||)를 평가할 때, 왼쪽 피연산자만으로 최종 결과가 결정되는 경우 **오른쪽 피연산자를 평가하지 않고 실행을 중단하는 기법**입니다.

이를 통해 불필요한 계산을 줄여 성능을 최적화합니다.

#### Lazy Evaluation (지연 평가)

어떤 값의 계산이나 연산이 그 결과가 **실제로 필요해지는 시점까지 지연**되는 원리입니다.

함수형 프로그래밍에서 중간 연산(Intermediate Operations)은 최종 연산(Terminal Operation)이 호출될 때까지 실행되지 않고 미뤄집니다.

#### Function Composition (함수 합성)

여러 개의 독립적인 함수를 합쳐 **하나의 새로운 함수**로 만드는 기술입니다.

재사용 가능한 작은 함수들을 연결하여 복잡한 데이터 처리 파이프라인을 구축할 수 있습니다.


| 메서드 | 실행 순서 | 특징 |
| :--- | :--- | :--- |
| **`andThen(g)`** | `f` 실행 후 → `g` 실행 | **순차적** (파이프라인) |
| **`compose(g)`** | `g` 실행 후 → `f` 실행 | **역순** |

---

### 5. 함수형 프로그래밍 디자인 패턴

#### 디자인 패턴 분류

| 유형 | 목적 |
| :--- | :--- |
| **생성 패턴** | 오브젝트의 생성에 관련된 패턴 (ex. Builder) |
| **구조 패턴** | 상속을 이용해 클래스/오브젝트를 조합하여 더 발전된 구조로 만드는 패턴 (ex. Decorator) |
| **행동 패턴** | 필요한 작업을 여러 객체에 분배하여 객체 간 결합도를 줄이는 패턴 (ex. Strategy, Template Method, Chain of Responsibility) |

#### 함수형 디자인 패턴 구현 비교

| 패턴 | 설명 | 예제 파일 |
| :--- | :--- | :--- |
| **Builder Pattern** | 객체의 **생성 로직**과 **표현 로직**을 분리하여 필드가 많은 객체의 생성 과정을 유연하게 만듭니다. | `BuilderPattern` |
| **Strategy Pattern** | 런타임에 어떤 **전략**(행동)을 사용할지 선택할 수 있게 하고, 전략들을 캡슐화하여 간단하게 교체합니다. | `StrategyPattern` |
| **Decorator Pattern** | 용도에 따라 객체에 **기능을 동적으로 계속 추가**할 수 있게 해줍니다. | `DecoratorPattern` |
| **Template Method Pattern** | 상위 클래스가 **알고리즘의 뼈대**만을 정의하고, 가변적인 각 단계는 하위 클래스에게 정의를 위임하는 패턴입니다. | `TemplateMethodPattern` |
| **Chain of Responsibility** | 명령을 처리할 객체들을 **체인으로 엮어** 명령을 순차적으로 처리하며, 새로운 처리 객체의 추가가 매우 간단합니다. | `ChainOfResponsibilityPattern` |
