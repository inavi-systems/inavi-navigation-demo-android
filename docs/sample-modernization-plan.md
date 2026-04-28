# iNavi Navigation SDK Sample Modernization Plan

## 목적

현재 공개용 `inavi-navigation-demo-android` 프로젝트를 내부에서 사용 중인 실제 샘플 프로젝트 기준으로 현행화한다.

이 작업은 단순 파일 복사가 아니라 공개 저장소로 배포 가능한 형태로 정리하는 이관 작업이다. 내부 프로젝트에 포함된 로컬 서명 설정, 실제 앱 키, 개발자 로컬 경로, 불필요한 IDE/빌드 산출물은 공개 프로젝트에 반영하지 않는다.

## 현재 확인한 차이

### 공개 프로젝트

- Gradle Wrapper: `gradle-5.4.1-all`
- Android Gradle Plugin: `3.5.2`
- Kotlin: `1.3.50`
- `compileSdkVersion`: `28`
- `minSdkVersion`: `21`
- `targetSdkVersion`: `28`
- SDK 의존성: `com.inavisys.navisdk:inavi-navigation-sdk:0.0.27`
- 앱 키: `Input AuthKey` 플레이스홀더
- 주요 소스: `MainActivity`, `NaviViewPagerAdapter`, `Page*Adapter`, `MapFragment`

### 내부 실제 샘플 프로젝트

- Gradle Wrapper: `gradle-8.13-bin`
- Android Gradle Plugin: `8.13.2`
- Kotlin: `1.8.22`
- `compileSdkVersion`: `33`
- `minSdkVersion`: `23`
- `targetSdkVersion`: `33`
- `namespace`: `com.inavi.airlibsample`
- SDK 의존성: `com.inavisys.navisdk:inavi-navigation-sdk:0.9.0`
- 신규 코드/리소스:
  - `PageTruckAdapter.kt`
  - `row_contents_edittext.xml`
  - `ix_map_safe_pin_20.png`
  - `ix_map_safe_pin_21.png`
- 내부용으로 보이는 항목:
  - `app/build.gradle`의 `signingConfigs.releaseSignKey`
  - 로컬 키스토어 경로
  - 서명 비밀번호/키 비밀번호
  - `AndroidManifest.xml`의 실제 `com.inaviair.sdk.appkey` 값
  - `.idea`, `.DS_Store`, `local.properties`, `.gradle`

## 이관 원칙

1. 공개 저장소에는 빌드 가능한 최소 공개 샘플만 남긴다.
2. 내부 인증키, 서명키, 개인 로컬 경로는 절대 이관하지 않는다.
3. 실제 샘플의 기능 코드는 최대한 보존하되, 공개 배포에 필요한 플레이스홀더와 문서화를 적용한다.
4. 대규모 변경은 단계별로 나누고 각 단계 완료 후 검토받는다.
5. 각 단계는 빌드 가능성 또는 변경 diff를 확인한 뒤 다음 단계로 진행한다.

## 단계별 진행 계획

### 1단계: 이관 범위 확정

작업:

- 내부 프로젝트와 공개 프로젝트의 Gradle, manifest, Kotlin source, layout/resource 차이를 정리한다.
- 이관 대상과 제외 대상을 확정한다.
- 공개 샘플에서 유지할 `applicationId`, 앱 이름, README 방향을 결정한다.

검토 포인트:

- `applicationId`를 기존 공개값 `com.inavi.airlibsample`로 유지할지, 내부 프로젝트 값을 반영할지 결정해야 한다.
- 실제 앱 키는 공개하지 않고 `Input AuthKey` 또는 문서화된 placeholder를 유지한다.
- 내부 프로젝트의 release signing 설정은 제외한다.

산출물:

- 이 계획 문서
- 이관/제외 대상 목록

### 2단계: 빌드 시스템 현행화

작업:

- Gradle Wrapper를 내부 프로젝트 기준 `8.13` 계열로 갱신한다.
- Android Gradle Plugin을 `8.13.2`로 갱신한다.
- Kotlin을 `1.8.22`로 갱신한다.
- `jcenter()`를 제거하고 `google()`, `mavenCentral()`, `https://repo.inavi.com/artifactory/navigation/`만 사용한다.
- `namespace 'com.inavi.airlibsample'`를 추가한다.
- `compileSdkVersion 33`, `targetSdkVersion 33`, `minSdkVersion 23` 적용 여부를 검토 후 반영한다.
- `kotlin-android-extensions`를 제거하고 `kotlin-parcelize` 및 ViewBinding 사용 구조로 전환한다.

제외:

- 내부 프로젝트의 `signingConfigs.releaseSignKey`
- 로컬 키스토어 경로 및 비밀번호

검증:

- `./gradlew tasks`
- 가능하면 `./gradlew :app:assembleDebug`

### 3단계: SDK 의존성 및 공개 설정 정리

작업:

- SDK 의존성을 `com.inavisys.navisdk:inavi-navigation-sdk:0.9.0`로 갱신한다.
- `play-services-location`을 `21.0.1`로 갱신한다.
- 내부 프로젝트의 테스트 의존성 주석 처리 상태를 그대로 따를지, 공개 샘플에 테스트 의존성을 유지할지 결정한다.
- 앱 키 설정은 공개용 placeholder로 유지한다.
- README에 앱 키 입력 위치와 SDK repository 설정을 문서화한다.

검토 포인트:

- 공개 샘플에서 `SYSTEM_ALERT_WINDOW` 권한이 필요한지 확인한다. 내부 프로젝트에는 포함되어 있으나 공개 프로젝트에는 현재 없다.
- 실제 앱 키가 Git diff에 포함되지 않았는지 확인한다.

검증:

- 민감정보 검색:
  - `rg -n "(appkey|storePassword|keyPassword|local keystore path|real auth key)"`
- Gradle dependency resolution 확인

### 4단계: 앱 코드 이관

작업:

- 내부 프로젝트의 Kotlin 소스 변경을 공개 프로젝트에 반영한다.
- 신규 `PageTruckAdapter.kt`를 추가한다.
- `MainActivity`, `NaviViewPagerAdapter`, `MapFragment`, `Page*Adapter`, `PageData*` 변경을 반영한다.
- ViewBinding 전환에 필요한 import/API 차이를 정리한다.

검토 포인트:

- 기존 공개 샘플 사용자가 이해하기 쉬운 예제 구조인지 확인한다.
- 내부 개발용 로그, 임시 좌표, 개발 서버 URL, 하드코딩된 인증값이 포함되지 않았는지 확인한다.
- 최신 SDK API 사용 예제가 기능별로 잘 분리되어 있는지 확인한다.

검증:

- Kotlin compile
- Android Studio sync 가능성 확인
- 주요 화면 진입 경로 수동 점검

### 5단계: 리소스 및 UI 이관

작업:

- 내부 프로젝트의 layout 변경을 반영한다.
- 신규 리소스 `row_contents_edittext.xml`, `ix_map_safe_pin_20.png`, `ix_map_safe_pin_21.png`를 추가한다.
- `activity_main.xml`, `strings.xml` 변경을 반영한다.
- 불필요한 `.DS_Store` 등 메타 파일은 제외한다.

검토 포인트:

- 공개 샘플로 필요한 리소스만 포함한다.
- 이미지 리소스의 라이선스/공개 가능 여부를 확인한다.

검증:

- `./gradlew :app:assembleDebug`
- 리소스 컴파일 오류 확인

### 6단계: 공개용 문서 정리

작업:

- README를 최신 SDK 기준으로 갱신한다.
- 포함할 내용:
  - 프로젝트 목적
  - Android Studio/Gradle 요구 버전
  - SDK repository 설정
  - 앱 키 입력 방법
  - 실행 방법
  - 주요 예제 기능 목록
  - 공개 저장소에 포함하지 않는 항목 안내
- 라이선스 문구는 기존 정책 유지 여부를 확인한다.

검토 포인트:

- 내부 명칭, 내부 경로, 실제 인증값, 개인 정보가 문서에 포함되지 않았는지 확인한다.
- SDK artifact 좌표와 버전이 실제 배포 버전과 일치하는지 확인한다.

### 7단계: 최종 검증 및 리뷰

작업:

- 전체 diff 리뷰
- 민감정보 검색
- Debug build
- README 절차대로 클린 환경에서 sync/build 가능한지 확인

검증 명령 후보:

```bash
./gradlew clean
./gradlew :app:assembleDebug
rg -n "(storePassword|keyPassword|local keystore path|real auth key)"
git diff --stat
git diff -- app/build.gradle app/src/main/AndroidManifest.xml README.md
```

완료 기준:

- 공개 프로젝트가 최신 SDK 샘플 코드로 갱신되어 있다.
- 내부 프로젝트의 실제 앱 키와 서명 정보가 포함되어 있지 않다.
- Android Gradle Plugin 8 계열 기준으로 sync/build 가능하다.
- README만 보고 샘플 실행 준비가 가능하다.

## 예상 리스크

- 내부 프로젝트의 Gradle 버전(`8.13.2`)이 로컬/CI 환경에서 바로 사용 가능하지 않을 수 있다.
- SDK artifact `0.9.0` 접근에 인증 또는 네트워크 제약이 있을 수 있다.
- 내부 샘플의 실제 앱 키를 placeholder로 바꾸면 런타임 지도/탐색 기능은 별도 발급 키 없이는 동작하지 않을 수 있다.
- `minSdkVersion`을 `21`에서 `23`으로 올리면 기존 공개 샘플의 지원 범위가 변경된다.
- Android 12 이상 `android:exported` 요구사항 때문에 manifest 차이는 반드시 반영해야 한다.

## 다음 승인 요청

다음 단계로는 `1단계: 이관 범위 확정`을 완료하기 위해 아래 결정을 확인받은 뒤 진행한다.

1. `applicationId`는 공개 프로젝트 기존값 `com.inavi.airlibsample`로 유지한다.
2. 실제 앱 키는 이관하지 않고 `Input AuthKey` placeholder를 유지한다.
3. 내부 release signing 설정은 이관하지 않는다.
4. Gradle/SDK 버전은 내부 프로젝트 기준으로 현행화한다.
5. `minSdkVersion`은 내부 프로젝트 기준 `23`으로 올린다.
