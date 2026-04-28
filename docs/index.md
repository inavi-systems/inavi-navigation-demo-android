---
title: iNavi Navigation SDK for Android
---

# iNavi SDK Android 개발 가이드 — 아이나비 에어

## 개정이력

### v0.0.1 — 2020-01-17
- 인증 업데이트 추가

### v0.0.2 — 2020-01-29
- SDK 적용방법 추가

### v0.0.3 — 2020-02-20
- `setDrivingStatusListener` I/F — `nearGoalDist` Param 추가

### v0.0.4 — 2020-03-23
- 주행중 특정지점에서 비정상 종료되는 이슈 수정

### v0.0.5 — 2020-03-30
- Proguard 옵션 추가

### v0.0.6 — 2020-04-01
- SDK 난독화 예외처리 추가 (통신관련)
- App 난독화 가이드 추가

### v0.0.7 — 2020-04-07
- 길 찾기 I/F 파라미터 변경 (`runRoute`, `runSingleRoute`)
  - `RouteExtraInfo` 추가
  - `RoutePtItem`에 `angle` 필드 추가

### v0.0.8 — 2020-04-23
- 과속 백그라운드 알림 추가

### v0.0.9 — 2020-05-20
- 차량 속도 Callback 추가 (`setCarSpeedListener`)

### v0.0.10 — 2020-05-28
- 디버그 로그 제거
- 주행화면 TBT UI 개선 (차기/차차기 높이 및 간격 조정)

### v0.0.11 — 2020-06-08
- 내부 안정성 이슈 개선
- `NaviLocation`에 속도 필드 추가

### v0.0.12 — 2020-07-30
- 주행화면 GUI 개선 (회전정보, 카메라정보, 현위치 버튼 등)
- 길 찾기 실패 코드 추가 (코드표: 5002, 5003, 5004)

### v0.0.13 — 2020-11-02
- 기본 자차 아이콘 및 폰트 변경
- 주행화면 TBT UI 개선 (형태 및 컬러 변경)
- 주행화면 TBT 출발/도착 아이콘 변경 I/F 추가 (`setTurnViewTaxiIcon`)
- 지도 컴포넌트 Show/Hide I/F 수정 (`forceHideMapComponent`)
- 도로명 주소 I/F 추가 (`getFullAddr`)
- 현재 도로명 I/F 추가 (`getCurrentRoadName`)
- 경유지 정보 I/F 추가 (`getRoutePtViaInfo`)
- 주행정보 요금 필드 추가 (`getRemainRouteInfo`) — `RouteRemainInfo`에 `remainFee` 필드 추가
- NaviEvent Callback 추가 (`setNaviEventListener`) — 현위치 이동, 전체경로 보기

### v0.0.14 — 2020-11-05
- 주행 속도 레이아웃 오류 개선

### v0.0.15 — 2020-11-25
- Audio Stream Type 변경 I/F 추가 (`setAudioStreamType`)

### v0.0.16 — 2020-12-04
- 지도 Style 변경 I/F 추가 (`setMapStyle`)
- 도착예정시간 초기화 및 갱신 타이밍 개선

### v0.0.17 — 2020-12-16
- 총 주행정보 I/F 추가 (`getTotalRemainRouteInfo`)
- `getRemainRouteInfo`의 Distance 갱신 로직 개선
- `forceHideMapComponent` 지도 컴포넌트 타입 추가: `MAPCOMPONENT.ROUTE_TRAFFIC_STATUS_BAR`

### v0.0.18 — 2021-02-05
- Layout 초기화 I/F 추가 (`initLayout`)
- 기존 `initLayout` I/F에 지도/주행의 부모 뷰 추가

### v0.0.19 — 2021-02-09
- SurfaceView Activity Casting 오류 개선

### v0.0.20 — 2021-03-03
- **[필수] SDK 저장소 변경** — Bintray에서 Artifactory로 변경
- **[필수] `NaviLocation` 자료형 변경** — `angle` 필드 자료형 `Double`로 변경
- 화면회전 세팅 I/F 수정 (`setOrientation`) — `ORIENTATION_UNDEFINED` 추가

### v0.0.21 — 2021-03-17
- 내부적으로 사용하던 TimeZone 제거

### v0.0.22 — 2021-04-21
- **[필수] `minSdkVersion` 19 → 21 변경**
- 경유지 기능 추가
  - 기존 길찾기 I/F에 경유지 Param 추가: `runRoute`, `runSingleRoute`
  - 남은 경유지 정보 I/F 추가: `getRoutePtRemainViaInfo`, `getRemainViaRouteInfo`
  - 경유지 변경 재탐색 I/F 추가: `runReRouteChangeVia`
- 에뮬레이터 지원 — 에뮬레이터 모드 I/F 추가 (`setEmulatorMode`)

### v0.0.23 — 2021-08-06
- 안내종료거리 튜닝
- 길찾기 I/F에 탐색옵션 Param 추가 (`runRoute`)
- 안전운행정보 변경 기능 추가
  - 안내설정 I/F 추가 (`setGuidanceSettings`, `getGuidanceSettings`)
  - `GUIDANCESETTING` 추가

### v0.0.24 — 2021-11-29
- 안내종료거리 2차 튜닝
- 내부 모듈 안정성 개선
- 주행 경로선 색상 변경 기능 추가
  - 경로선 색상 변경 I/F 추가 (`setMapRouteColor`)
  - `MAPROUTECOLOR` 추가

### v0.0.25 — 2021-12-22
- 내부 모듈 안정성 개선
- 주행중 전체 경로보기 기능 추가 — `drivingRouteZoomMap`

### v0.0.26 — 2022-03-31
- 목적지 도착시 요금 반환 I/F 추가 — `onArrivalFee` (`OnDrivingStatusListener`)
- `setDrivingStatusListener` 설명 추가 — `onArrivalFee` 사양 추가

### v0.0.27 — 2022-11-11
- 경유지 사양 확대 (최대 100개까지 설정 가능)
- `forceHideMapComponent` 지도 컴포넌트 타입 추가
  - `MAPCOMPONENT.BOTTOM_NORMAL_SIDE_BUTTON`
  - `MAPCOMPONENT.BOTTOM_DRIVING_SIDE_BUTTON`
  - `MAPCOMPONENT.BOTTOM_ARRIVAL_LEFT_BUTTON`
- 화물차 설정 및 전용 탐색 옵션 추가
  - 차량 높이/중량/U-turn 도로폭/좁은길 도로폭/통행제한구간 회피 설정 옵션 추가
    - `setCarHeight`, `getCarHeight`
    - `setCarWeight`, `getCarWeight`
    - `setAvoidUturn`, `getAvoidUturn`
    - `setAvoidNarrowRoad`, `getAvoidNarrowRoad`
    - `setAvoidRestriction`, `getAvoidRestriction`
  - **[필수] `ROUTEOPTIONTYPE` 수정** — 화물차 전용 탐색 옵션 추가

### v0.0.28 — 2023-01-10
- 지도 여백(Padding) 설정 I/F 추가
  - `routeZoomMapWithPadding`
  - `setMapPadding`
  - `clearMapPadding`
- 차종 설정 I/F 추가 — `setCarType`, `getCarType`
- enum class `CARTYPE` 추가

### v0.0.29 — 2023-01-20
- **[필수] `OnDrivingStatusListener` 수정** — `onArrivalVia` 추가 (경유지 도착 알림)
- TBT(TurnView) Custom Background Color 설정 I/F 추가
  - `setTurnViewBackgroundColor`, `getTurnViewBackgroundColor` (차기)
  - `setNextTurnViewBackgroundColor`, `getNextTurnViewBackgroundColor` (차차기)
  - `setArrivalViewBackgroundColor`, `getArrivalViewBackgroundColor` (목적지 도착 view)
  - `resetTurnViewsBackgroundColor` (기본값으로 초기화)
- 확대도 표출 기능 추가 — `setVisibleExtendView`, `getVisibleExtendView`

### v0.5.0 — 2023-03-22
- 내부 모듈 안정성 개선

### v0.5.1 — 2023-05-04
- 경유지 도착 알림 I/F 사양 변경
- 탐색열에서 진행방향으로 현 위치에서 가장 가까운 좌표 인덱스 반환 I/F 추가 — `getNearestVertexIndex`

### v0.5.2 — 2023-05-15
- 내부 모듈 안정성 개선

### v0.6.0 — 2023-08-31
- 내부 엔진 수정
- AndroidX 적용
- `play-services-location` 버전 16.0.0 → 21.0.1 업데이트 (fusedLocationProvider 적용)

### v0.6.1 — 2023-09-05
- 경로탐색 실패 처리 사양 변경 — 경유지가 포함된 경우 출발지-목적지 간 거리 체크 제외

### v0.6.2 — 2023-10-17
- **[필수] SDK 저장소 URL 변경**
- 화물차 전용옵션 로직 개선

### v0.6.3 — 2024-04-03
- 안정성 개선

### v0.6.4 — 2024-04-17
- 구글 정책 위반 관련 이슈 개선

### v0.7.0 — 2024-04-25
- Kotlin version 업그레이드 (1.5.21)
- 가이드 문서 업데이트 (프로젝트 설정 관련 내용 추가)
  - 라이브러리 종속성 항목 추가 (`recyclerview`)
  - `jvmTarget` 관련 내용 추가

### v0.7.1 — 2024-09-26
- 내부 엔진 수정
- 상수원보호구역 회피탐색 기능 추가
- 최적탐색 기능 추가

### v0.7.2 — 2024-10-23
- 안내 음성 수정
- 후면카메라 단속 안내 추가
- 안내 UI 폰트 변경

### v0.7.3 — 2025-03-12
- 내부 엔진 수정
- iMPS 내제화에 따른 인증 서버 전환

### v0.7.4 — 2025-07-09
- 다음턴/다다음턴 (TBT View) 백그라운드 안내 기능 추가 (다른 앱 위에 그리기)
- 사용자 디스플레이 크기 옵션 변경 시 안내 화면 축소 문제 수정

### v0.8.0 — 2025-10-29
- Android 15 대응 (support 16kb page size)
- 위수지역 회피탐색 기능 추가

### v0.8.1 — 2025-11-13
- 아파트 동/호/명칭 충돌 기능 On/Off 추가 — `setAptPoiCollisionIgnored`
- 지도 엔진 최신화

### v0.9.0 — 2026-03-13
- 일부 단말에서 안내 음성 마지막이 잘리는 현상 수정
- 지도 Style 변경 I/F 제거 — `setMapStyle`, `MAPSTYLE`
- AGP 7.4.2 적용 (AGP 7.x 계열로 맞추는 것을 권장)
- 지도 엔진 최신화

---

## 개요

본 문서는 Android 기반 플랫폼에서 구동되는 **아이나비 내비게이션 SDK**(이하 SDK) 연동에 대한 기술 문서입니다.

## Appkey 발급

SDK를 사용하기 위해서는 우선 Appkey 발급이 필요합니다. (규격서 제공 제휴 사업 담당자를 통해 별도 요청 필요)

## 개발 환경

- 개발 도구: AndroidStudio 2025.2.2 Patch 1 이상
- 개발 언어: Android, Kotlin
- SDK 지원 범위
  - `targetSdkVersion`: 28 (Android 9, P OS)
  - `minSdkVersion`: 21 (Android 5.0, LOLLIPOP OS)

## SDK 세팅 방법

### 1. SDK 저장소 추가

`Root Project build.gradle` 파일에 추가합니다.

```groovy
// Root Project build.gradle
allprojects {
    repositories {
        maven { url 'https://repo.inavi.com/artifactory/navigation/' }
    }
}
```

### 2. SDK 의존성 추가

`App build.gradle` 파일에 추가합니다.

```groovy
// App build.gradle
dependencies {
    // SDK 의존성
    implementation 'com.inavisys.navisdk:inavi-navigation-sdk:0.9.0'

    // SDK가 사용하는 Library 의존성
    implementation 'com.google.code.gson:gson:2.8.5'

    // SDK v0.6.0 이하: 16.0.0 사용
    // implementation 'com.google.android.gms:play-services-location:16.0.0'
    // SDK v0.6.0 부터
    implementation 'com.google.android.gms:play-services-location:21.0.1'

    // ConstraintLayout 2.0 이상 필수 (SDK v0.0.28 이후)
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'

    // 1.0.0 이상 버전 사용 가능
    implementation("androidx.recyclerview:recyclerview:1.3.2")
}
```

`gradle.properties` 파일에 추가합니다. (SDK v0.6.0부터 필수)

```properties
android.useAndroidX=true
```

### 3. SDK 환경 세팅

`App build.gradle` 파일에 아래 내용을 추가합니다.

**ndk abiFilters 추가:**

```groovy
android {
    defaultConfig {
        ndk {
            abiFilters "arm64-v8a", "armeabi-v7a"
        }
    }
}
```

**컴파일 옵션 추가:**

```groovy
android {
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
```

`AndroidManifest.xml` 설정:

**http 허용:**

```xml
<manifest>
    <application android:usesCleartextTraffic="true">
    </application>
</manifest>
```

**Apache http legacy 허용:**

```xml
<manifest>
    <application>
        <uses-library
            android:name="org.apache.http.legacy"
            android:required="false"/>
    </application>
</manifest>
```

**AppKey 등록:**

```xml
<manifest>
    <application>
        <meta-data
            android:name="com.inaviair.sdk.appkey"
            android:value="AppKey"/>
    </application>
</manifest>
```

### 4. SDK 난독화 옵션 세팅

`proguard-rules.pro` 파일에 추가합니다.

```proguard
-keep class com.inaviair.sdk.** { *; }
```

### 5. SDK Layout 세팅

지도를 표출할 Layout(xml) 파일에 추가합니다.

- 지도 View: `com.inaviair.sdkEngine.MapAdapter`
- 주행화면 View: `com.inaviair.sdkEngine.MapLayer`

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <!-- 지도 View -->
        <com.inaviair.sdkEngine.MapAdapter
            android:id="@+id/mapAdapter"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:visibility="visible" />

        <!-- 주행화면 View -->
        <com.inaviair.sdkEngine.MapLayer
            android:id="@+id/mapLayer"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:visibility="visible" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</androidx.constraintlayout.widget.ConstraintLayout>
```

---

## SDK 권한 목록

App에서 필수로 권한을 획득해야 합니다.

### 필수 권한 목록

```kotlin
Manifest.permission.ACCESS_FINE_LOCATION
Manifest.permission.ACCESS_COARSE_LOCATION
Manifest.permission.READ_EXTERNAL_STORAGE
Manifest.permission.WRITE_EXTERNAL_STORAGE
```

### SDK 내부 선언 Permission 목록

```xml
<!-- 통신 -->
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>

<!-- 위치 -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS"/>

<!-- 저장소 -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

---

## SDK Interface

1. **SDK 생명주기**
   - SDK 생명주기는 Application 생명주기와 동일하게 사용하는 것을 권장
   - SDK의 내비게이션 지도 및 주행화면은 앱 실행 시 최초 실행되는 Activity에서 구현
   - `loadNaviLibrary`, `initLayout`, `initalizeNavi`, `destroyNavi`는 앱 실행당 1회만 호출

2. **`INaviController`** 클래스를 통해 SDK의 모든 기능 사용
   - `INaviController`는 Singleton 객체
   - 비동기 처리는 SDK에서 Callback 제공

3. **좌표계**
   - SDK는 위경도 좌표를 사용
   - DD(십진수 도) 방식 사용
   - 예) 서울역 — 위도: 37.553173, 경도: 126.972471

---

## 시나리오별 예시

### 1. 초기화

**Activity 예시:**

```kotlin
// 필수 호출 순서: loadNaviLibrary → initLayout → initalizeNavi
class MainActivity : AppCompatActivity() {

    companion object {
        init {
            // Application 초기화 시 호출 (필수)
            INaviController.loadNaviLibrary()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.xxx)

        // OnCreate에서 호출 (필수)
        INaviController.initLayout(this, R.id.mapAdapter, R.id.mapLayer)

        // 음성 Stream 변경
        INaviController.setAudioStreamType(AudioManager.STREAM_MUSIC)

        val mdn = "UniqueID" // ex) UUID 등 고유식별자

        // SDK 엔진 초기화 (필수)
        INaviController.initalizeNavi(this, getRootPath(), mdn, object : OnNaviInitListener {
            override fun onSuccess() {
                // SDK 초기화 성공
            }
            override fun onFail(errCode: Int, errMsg: String) {
                // SDK 초기화 실패
            }
        })
    }

    private fun getRootPath(): String {
        var strStorageDir = ""
        val fRootPath = ContextCompat.getExternalFilesDirs(this, null)
        if (fRootPath.isNotEmpty() && fRootPath[0] != null)
            strStorageDir = fRootPath[0].absolutePath
        return strStorageDir
    }
}
```

**Fragment 예시:**

```kotlin
// 필수 호출 순서: loadNaviLibrary → initLayout → initalizeNavi
class MainActivity : AppCompatActivity() {

    companion object {
        init {
            INaviController.loadNaviLibrary()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.xxx)
        val mapFragment = MapFragment(this)
    }
}

class MapFragment(private val acty: Activity) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_xxx, container, false)

        // OnCreateView에서 호출 (필수)
        INaviController.initLayout(acty, rootView, R.id.mapAdapter, R.id.mapLayer)

        INaviController.setAudioStreamType(AudioManager.STREAM_MUSIC)

        val phoneNumber = "UniqueID"

        INaviController.initalizeNavi(this, getRootPath(), phoneNumber, object : OnNaviInitListener {
            override fun onSuccess() { }
            override fun onFail(errCode: Int, errMsg: String) { }
        })
    }

    private fun getRootPath(): String {
        var strStorageDir = ""
        val fRootPath = ContextCompat.getExternalFilesDirs(this, null)
        if (fRootPath.isNotEmpty() && fRootPath[0] != null)
            strStorageDir = fRootPath[0].absolutePath
        return strStorageDir
    }
}
```

### 2. 지도 아이콘 표출 및 제거

```kotlin
var overlayMap: MapOverlay? = null
var iconEx: MapIcon? = null

// 지도 overlay 생성
overlayMap = INaviController.createMapOverlay()

// overlay에 mapIcon 클릭 이벤트 등록 (선택사항)
overlayMap?.setMapIconListener { icon -> }

// 지도 아이콘 생성
val mapIcon = INaviController.createMapIcon(...)

// 지도 아이콘을 overlay에 추가 (정상 추가 시 mapIcon 반환)
iconEx = INaviController.addMapIcon(overlayMap, mapIcon)

// ...

// 지도 아이콘 제거
INaviController.removeMapIcon(overlayMap, iconEx)
iconEx = null

// 지도 overlay 제거
INaviController.removeMapOverlay(overlayMap)
overlayMap = null
```

### 3. 검색

```kotlin
val query = "검색어"
val lat = 37.00000
val lon = 127.0000

INaviController.runSearch(query, lat, lon, object : OnSearchListener {
    override fun onSuccess(result: SearchResult) {
        // 검색 성공 처리
    }
    override fun onFail(errCode: Int, errMsg: String) {
        // 검색 실패 처리
    }
})
```

### 4. 길 찾기 및 안내 시작

```kotlin
var startPoint: RoutePtItem
var goalPoint: RoutePtItem
var viaList = mutableListOf<RoutePtItem>()
viaList.add(RoutePtItem("이름", 37.00000, 127.00000, 0.0, 0.0, 0))

var extraInfo: RouteExtraInfo
var routeID: String = ""

val optionType = mutableListOf<ROUTEOPTIONTYPE>()
optionType.add(ROUTEOPTIONTYPE.HIGHWAYPRIOTY)
optionType.add(ROUTEOPTIONTYPE.MOTOCYCLE)

INaviController.runRoute(
    startPoint, goalPoint, viaList, optionType, extraInfo,
    object : OnRouteMultiListener {
        override fun onSuccess(result: ArrayList<String>, same: Boolean) {
            routeID = result[0]
            // 모든 경로에 맞게 지도 스케일 조절 (선택한 경로는 select 상태)
            INaviController.routeZoomMap(result, routeID)
        }
        override fun onFail(errCode: Int, errMsg: String) {
            // 길 찾기 실패 처리
        }
    }
)

// ...

// 주행 안내 시작
INaviController.runGuidance(routeID)

// 모의 주행 시작
INaviController.startSimulation(routeID)
```

### 5. 주행중 경유지 변경 재탐색

```kotlin
// 지나가지 않은 경유지 정보 확인 (남은시간, 남은거리)
val remainViaInfo: List<RouteViaRemainInfo>? = INaviController.getRemainViaRouteInfo()

// 지나가지 않은 경유지 좌표 확인
val rVia: List<RoutePtItem>? = INaviController.getRoutePtRemainViaInfo()

val viaList = mutableListOf<RoutePtItem>()
viaList.add(RoutePtItem(rVia[0].name, rVia[0].rpLat, rVia[0].rpLon, 0.0, 0.0, 0))
viaList.add(RoutePtItem("이름", 37.00000, 127.00000, 0.0, 0.0, 0))

INaviController.runReRouteChangeVia(viaList, object : OnReRouteResultListener {
    override fun onSuccess() {
        // 재탐색 성공 — 새로운 경로로 자동 안내 시작
    }
    override fun onFail(errCode: Int, msg: String) {
        // 재탐색 실패
    }
})
```

### 6. 안전운행정보 안내 변경

```kotlin
val settings = INaviController.getGuidanceSettings().toMutableList()

// 과속방지턱 안내가 없으면 추가
if (settings.find { it == GUIDANCESETTING.SPEED_BUMP } == null) {
    settings.add(GUIDANCESETTING.SPEED_BUMP)
}

// 교통정보 수집카메라 안내가 있으면 삭제
settings.find { it == GUIDANCESETTING.FIX_TRAFFIC }?.let { settings.remove(it) }

INaviController.setGuidanceSettings(settings)
```

### 7. 화물차 특화 옵션 적용

> 설정 값은 재실행 시에도 유지되며, 앱 삭제 시 초기화됩니다.
> 회피옵션이 설정되어 있어도 해당 경로가 유일한 경우에는 제한구역을 포함하는 경로로 안내될 수 있습니다.

```kotlin
// 현재 설정값 조회
var nHeight: Int = INaviController.getCarHeight()           // default: 0
var nWeight: Int = INaviController.getCarWeight()           // default: 0
var nAvoidNarrow: Int = INaviController.getAvoidNarrowRoad() // default: 0
var nAvoidUturn: Int = INaviController.getAvoidUturn()      // default: 0
var bIsAvoidRestriction: Boolean = INaviController.getAvoidRestriction() // default: false

// 도로통과 높이 제한 설정 (cm, 0 ~ 1000)
val nHeight = value.toIntOrNull()
if (nHeight != null) INaviController.setCarHeight(nHeight)

// 도로통과 중량 제한 설정 (kg, 0 ~ 60000)
val nWeight = value.toIntOrNull()
if (nWeight != null) INaviController.setCarWeight(nWeight)

// 왕복 n차선 이하 유턴 회피
val nUturn = value.toIntOrNull()
if (nUturn != null) INaviController.setAvoidUturn(nUturn)

// 왕복 n차선 이하 도로 회피
val nNarrow = value.toIntOrNull()
if (nNarrow != null) INaviController.setAvoidNarrowRoad(nNarrow)

// 화물차 통행제한구간 회피
INaviController.setAvoidRestriction(value.toBoolean())

// 위수지역 회피
INaviController.setAvoidEvasion(value.toBoolean())
```

### 8. 차종 설정

> 설정 값은 재실행 시에도 유지되며, 앱 삭제 시 초기화됩니다.

```kotlin
// 차종 값 조회 (default: TYPE_1)
val curType = INaviController.getCarType()

// 차종 제목: "1종(소형차)"
val carTypeTitle = CARTYPE.getTitle(curType)

// 차종 설명: "일반승용, 승합16인 이하, 화물2.5톤 미만"
val carTypeDescription = CARTYPE.getDescription(curType)

// 차종 설정
INaviController.setCarType(CARTYPE.TYPE_1)

// int → CARTYPE 변환 후 설정 (위와 동일한 결과)
INaviController.setCarType(CARTYPE.from(0))
```

### 9. 지도 여백(Padding) 적용

> `RectF` 파라미터 순서: `left, top, right, bottom`
> 사용자가 설정한 패딩 값은 주행 시작 시 초기화될 수 있습니다.

```kotlin
// 경로선택 화면 지도에 하단 60dp 패딩 추가
INaviController.routeZoomMapWithPadding(
    rIdList, selectedRid, android.graphics.RectF(0f, 0f, 0f, 60f)
)

// 기본지도에 패딩 적용 (경로안내/모의주행이 아닐 때)
INaviController.setMapPadding(android.graphics.RectF(0f, 0f, 0f, 60f))

// 패딩 초기화
INaviController.clearMapPadding()
```

### 10. TurnView 배경색 변경

> 설정 값은 재실행 시에도 유지되며, 앱 삭제 시 초기화됩니다.

```kotlin
// 현재 적용된 차기 TurnView 배경색 조회
val strCurTurnBg = INaviController.getTurnViewBackgroundColor()

/*
 * 사용 가능한 색상명:
 * red, blue, green, black, white, gray, cyan, magenta, yellow,
 * lightgray, darkgray, grey, lightgrey, darkgrey,
 * aqua, fuchsia, lime, maroon, navy, olive, purple, silver, teal
 */

// 차기 TurnView — 색상명으로 설정
INaviController.setTurnViewBackgroundColor("black")

// 차차기 TurnView — #AARRGGBB 형식으로 설정
INaviController.setNextTurnViewBackgroundColor("#f21e55f7")

// 목적지 도착 View — #RRGGBB 형식으로 설정
INaviController.setArrivalViewBackgroundColor("#1e55f7")

// 배경색 기본값으로 초기화
INaviController.resetTurnViewsBackgroundColor()
```

### 11. 확대도 표출

> 설정 값은 재실행 시에도 유지되며, 앱 삭제 시 초기화됩니다.

```kotlin
// 현재 확대도 표출 여부 조회 (default: false)
val bIsShowExtendView = INaviController.getVisibleExtendView()

// 확대도 표출 설정
INaviController.setVisibleExtendView(true)
```

<figure style="text-align: center; margin: 24px 0;">
    <img
        src="/images/extendview_horizontal.png"
        alt="확대도 표출 사양 가로모드"
        style="display: block; width: min(100%, 720px); height: auto; margin: 0 auto;"
    />
    <figcaption style="margin-top: 8px; font-size: 14px; color: #666; text-align: center;">
        확대도 표출 사양 (가로모드)
    </figcaption>
</figure>

<figure style="text-align: center; margin: 24px 0;">
    <img
        src="/images/extendview_vertical.png"
        alt="확대도 표출 사양 세로모드"
        style="display: block; width: min(100%, 360px); height: auto; margin: 0 auto;"
    />
    <figcaption style="margin-top: 8px; font-size: 14px; color: #666; text-align: center;">
        확대도 표출 사양 (세로모드)
    </figcaption>
</figure>


### 12. 현위치에서 진행방향으로 탐색열 가장 가까운 좌표 얻기

```kotlin
// 엔진 미초기화 또는 경로 미설정 시 -1 반환
val routeLinePoints = INaviController.getRouteLinePoints(strRouteId)
val nIdx = INaviController.getNearestVertexIndex()
```

### 13. 백그라운드에서 TBT 표출하기

`AndroidManifest.xml`에 권한을 추가합니다.

```xml
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```

```kotlin
// Foreground ↔ Background 전환 시 SDK에 상태 업데이트
override fun onResume() {
    super.onResume()
    INaviController.setApplicatonStatus(APPSTATUS.FOREGROUND)
}

override fun onStop() {
    super.onStop()
    INaviController.setApplicatonStatus(APPSTATUS.BACKGROUND)
}

// initalizeNavi 이후에 호출
private fun initBackgroundTBT() {
    // 1. '다른 앱 위에 그리기' 권한 요청
    if (!Settings.canDrawOverlays(applicationContext)) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            "package:$packageName".toUri()
        )
        startActivityForResult(intent, REQUEST_CODE_TBT_PERMISSION)
    }

    // 2. Background TBT Draw 설정 (권한 미획득 시 그리지 않음, runGuidance 상태에서만 동작)
    INaviController.setDrawBackgroundTBT(true)

    // 3. Background TBT 클릭 시 돌아올 Activity 설정
    INaviController.setBringToFrontListener(object : OnBringToFrontListener {
        override fun getBringToFrontPendingIntent(context: Context?): PendingIntent? {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            var flags = PendingIntent.FLAG_UPDATE_CURRENT
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                flags = flags or PendingIntent.FLAG_IMMUTABLE
            }
            return PendingIntent.getActivity(context, 0, intent, flags)
        }
    })
}
```

<figure style="text-align: center; margin: 24px 0;">
    <img
        src="/images/bg_tbt.png"
        alt="Background TBT 표출 예시"
        style="display: block; width: min(100%, 360px); height: auto; margin: 0 auto;"
    />
    <figcaption style="margin-top: 8px; font-size: 14px; color: #666; text-align: center;">
        Background TBT 표출 예시
    </figcaption>
</figure>

## INaviController Reference

### 초기화

#### `loadNaviLibrary`

```kotlin
fun loadNaviLibrary()
```

SDK에서 사용하는 native lib를 로딩합니다.

- Application 초기화 단계에서 호출 필요
- `initalizeNavi` 호출 전에 호출 필요

| | |
|---|---|
| **Param** | void |
| **Return** | void |

---

#### `initLayout`

```kotlin
fun initLayout(acty: Activity, mapViewID: Int, guideViewID: Int): Boolean
```

지도/주행 뷰를 초기화합니다.

- Root Activity `onCreate`에서 호출 가능
- `initalizeNavi` 호출 전에 호출 필요

| 파라미터 | 설명 |
|---|---|
| `acty` | 지도/주행 뷰를 포함하는 Activity |
| `mapViewID` | xml에 추가한 지도 리소스 ID |
| `guideViewID` | xml에 추가한 주행화면 리소스 ID |
| **Return** | `Boolean` |

---

#### `initLayout` (Fragment)

```kotlin
fun initLayout(acty: Activity, parentView: View, mapViewID: Int, guideViewID: Int): Boolean
```

지도/주행 뷰를 초기화합니다. (Fragment 지원)

- Root Activity `onCreate` / Fragment `onCreateView`에서 호출 가능
- `initalizeNavi` 호출 전에 호출 필요

| 파라미터 | 설명 |
|---|---|
| `acty` | View(Fragment)를 포함하는 Activity |
| `parentView` | 지도/주행 뷰의 부모 뷰 |
| `mapViewID` | xml에 추가한 지도 리소스 ID |
| `guideViewID` | xml에 추가한 주행화면 리소스 ID |
| **Return** | `Boolean` |

---

#### `setMapStyle` <Badge type="danger" text="deprecated v0.9.0" />

```kotlin
@deprecated
fun setMapStyle(style: MAPSTYLE)
```

지도 Style을 변경합니다.

| 파라미터 | 설명 |
|---|---|
| `style` | 지도 style 타입 (`MAPSTYLE`) |
| **Return** | void |

---

#### `setAudioStreamType`

```kotlin
fun setAudioStreamType(streamType: Int)
```

내비게이션 음성 Stream Type을 변경합니다.

- `initalizeNavi` 호출 전에 호출 필요
- 미호출 시 `STREAM_MUSIC` 사용

| 파라미터 | 설명 |
|---|---|
| `streamType` | `AudioManager.STREAM_MUSIC` 또는 `AudioManager.STREAM_NOTIFICATION` |
| **Return** | void |

---

#### `setEmulatorMode`

```kotlin
fun setEmulatorMode(emulator: Boolean)
```

타겟 단말 타입을 설정합니다.

| 파라미터 | 설명 |
|---|---|
| `emulator` | `true`: 에뮬레이터 / `false`: 실제 단말기 |
| **Return** | void |

---

#### `initalizeNavi`

```kotlin
fun initalizeNavi(context: Context, rootPath: String, uniqueID: String, listener: OnNaviInitListener)
```

SDK 엔진을 초기화합니다.

- 초기화 단계 I/F(`loadNaviLibrary`, `initLayout`)를 제외한 모든 I/F는 이후 호출 가능
- 결과가 성공인 경우에만 SDK가 정상 동작

| 파라미터 | 설명 |
|---|---|
| `context` | Application Context |
| `rootPath` | Application 폴더 경로 |
| `uniqueID` | 유일 식별자 (필수, 20자 이하 Alphanumeric) |
| `listener` | `OnNaviInitListener` — 성공/실패 Callback |
| **Return** | void |

---

### 지도 컨트롤

#### `setMapClickListener`

```kotlin
fun setMapClickListener(listener: OnMapClickListener)
```

지도 롱 클릭 Listener를 등록합니다.

| 파라미터 | 설명 |
|---|---|
| `listener` | `OnMapClickListener` — 롱 클릭 위치 좌표 [WGS84] 반환 |
| **Return** | void |

---

#### `setCarCurrentPosition`

```kotlin
fun setCarCurrentPosition()
```

지도와 자차를 GPS 위치로 이동합니다.

---

#### `setCarPosition`

```kotlin
fun setCarPosition(lat: Double, lon: Double, angle: Double)
```

지도와 자차를 특정 위치로 이동합니다.

| 파라미터 | 설명 |
|---|---|
| `lat` | 위도 [WGS84] |
| `lon` | 경도 [WGS84] |
| `angle` | 각도 |
| **Return** | void |

---

#### `setMapPosition`

```kotlin
fun setMapPosition(lat: Double, lon: Double, angle: Double)
```

지도를 특정 위치로 이동합니다.

| 파라미터 | 설명 |
|---|---|
| `lat` | 위도 [WGS84] |
| `lon` | 경도 [WGS84] |
| `angle` | 각도 |
| **Return** | void |

---

#### `zoomIn`

```kotlin
fun zoomIn()
```

현재 레벨에서 한 단계 확대합니다.

---

#### `zoomOut`

```kotlin
fun zoomOut()
```

현재 레벨에서 한 단계 축소합니다.

---

#### `setMapViewMode`

```kotlin
fun setMapViewMode(mode: MAPVIEWMODE)
```

지도 뷰 모드를 변경합니다.

| 파라미터 | 설명 |
|---|---|
| `mode` | 지도 뷰 모드 (`MAPVIEWMODE`) |
| **Return** | void |

---

#### `getMapViewMode`

```kotlin
fun getMapViewMode(): MAPVIEWMODE
```

현재 지도 뷰 모드를 반환합니다.

| | |
|---|---|
| **Return** | `MAPVIEWMODE` |

---

#### `visibleTrafficLine`

```kotlin
fun visibleTrafficLine(show: Boolean)
```

지도에 교통정보 라인 표출 여부를 설정합니다.

| 파라미터 | 설명 |
|---|---|
| `show` | `true`: 표출 / `false`: 미표출 |
| **Return** | void |

---

#### `createMapOverlay`

```kotlin
fun createMapOverlay(): MapOverlay?
```

지도 아이콘(`MapIcon`)을 관리하는 Overlay를 생성하고 반환합니다.

| | |
|---|---|
| **Return** | `MapOverlay` |

---

#### `createMapIcon`

```kotlin
fun createMapIcon(
    lat: Double, lon: Double,
    normalResID: Int, selectedResID: Int,
    gravity: ICONGRAVITY,
    minLevel: Int, maxLevel: Int,
    clickable: Boolean
): MapIcon?
```

지도 아이콘(`MapIcon`)을 생성하고 반환합니다.

| 파라미터 | 설명 |
|---|---|
| `lat` | 위도 [WGS84] |
| `lon` | 경도 [WGS84] |
| `normalResID` | 기본 리소스 ID |
| `selectedResID` | 선택 상태 리소스 ID |
| `gravity` | 아이콘 표출 위치 |
| `minLevel` | 표출될 최소 지도 레벨 |
| `maxLevel` | 표출될 최대 지도 레벨 |
| `clickable` | 클릭 이벤트 동작 여부 |
| **Return** | `MapIcon` |

---

#### `createMapIcon` (간략형)

```kotlin
fun createMapIcon(lat: Double, lon: Double, normalResID: Int, gravity: ICONGRAVITY): MapIcon?
```

| 파라미터 | 설명 |
|---|---|
| `lat` | 위도 [WGS84] |
| `lon` | 경도 [WGS84] |
| `normalResID` | 기본 리소스 ID |
| `gravity` | 아이콘 표출 위치 |
| **Return** | `MapIcon` |

---

#### `addMapIcon`

```kotlin
fun addMapIcon(overlay: MapOverlay, mapIcon: MapIcon): MapIcon?
```

`MapOverlay`에 지도 아이콘을 추가합니다.

| 파라미터 | 설명 |
|---|---|
| `overlay` | 아이콘이 추가될 `MapOverlay` |
| `mapIcon` | 추가할 `MapIcon` |
| **Return** | 정상 추가된 `MapIcon` |

---

#### `removeMapIcon`

```kotlin
fun removeMapIcon(overlay: MapOverlay, mapIcon: MapIcon)
```

`MapOverlay`에서 지도 아이콘을 제거합니다.

---

#### `removeMapIconAll`

```kotlin
fun removeMapIconALL(overlay: MapOverlay)
```

`MapOverlay`에 포함된 모든 지도 아이콘을 제거합니다.

---

#### `routeZoomMap` <Badge type="danger" text="deprecated 20/01/16" />

```kotlin
@deprecated
fun routeZoomMap(rid: String)
```

---

#### `routeZoomMap`

```kotlin
fun routeZoomMap(ridList: ArrayList<String>?, selectedRID: String)
```

탐색 경로 비율에 맞게 지도 스케일을 조절합니다.

- `OnRouteMultiListener` / `OnRouteSingleListener` 성공 후 호출 가능

| 파라미터 | 설명 |
|---|---|
| `ridList` | 탐색결과 경로 ID 배열 |
| `selectedRID` | 활성화할 경로 ID |
| **Return** | void |

---

#### `routeZoomMapWithPadding`

```kotlin
fun routeZoomMapWithPadding(
    ridList: ArrayList<String>?,
    selectedRID: String,
    fPaddingRect: android.graphics.RectF
)
```

패딩 값을 적용한 지도 스케일을 조절합니다.

- `fPaddingRect`의 `left, top, right, bottom` 값은 DP 단위

| 파라미터 | 설명 |
|---|---|
| `ridList` | 탐색결과 경로 ID 배열 |
| `selectedRID` | 활성화할 경로 ID |
| `fPaddingRect` | 상하좌우 여백값 |
| **Return** | void |

---

#### `setMapPadding`

```kotlin
fun setMapPadding(fPaddingRect: android.graphics.RectF)
```

지도에 Padding 값을 적용합니다.

- `fPaddingRect`의 값은 DP 단위
- 경로안내/모의주행 시 초기화

| 파라미터 | 설명 |
|---|---|
| `fPaddingRect` | 상하좌우 여백값 |
| **Return** | void |

---

#### `clearMapPadding`

```kotlin
fun clearMapPadding()
```

지도에 적용한 Padding 값을 초기화합니다.

---

#### `drivingRouteZoomMap`

```kotlin
fun drivingRouteZoomMap()
```

주행 경로 비율에 맞게 지도 스케일을 조절합니다.

- 안내 시작 이후에만 호출 가능
- 약 7초 후 자동 현위치 복귀 (사용자 인터랙션 발생 시 7초 Reset)

---

#### `setMapLevel`

```kotlin
fun setMapLevel(level: Int): Boolean
```

지도 레벨을 변경합니다.

| 파라미터 | 설명 |
|---|---|
| `level` | 변경할 지도 레벨 (1 ~ 13) |
| **Return** | `Boolean` |

---

#### `setMapFontSizeType`

```kotlin
fun setMapFontSizeType(type: MAPFONTSIZETYPE)
```

지도 주기 폰트 사이즈 타입을 변경합니다.

| 파라미터 | 설명 |
|---|---|
| `type` | `MAPFONTSIZETYPE` |
| **Return** | void |

---

#### `getMapFontSizeType`

```kotlin
fun getMapFontSizeType(): MAPFONTSIZETYPE
```

지도 주기 폰트 사이즈 타입을 반환합니다.

---

#### `setMapDayNightMode`

```kotlin
fun setMapDayNigthMode(mode: MAPDAYNIGHTMODE)
```

지도 주/야 타입을 변경합니다.

---

#### `getMapDayNightMode`

```kotlin
fun getMapDayNightMode(): MAPDAYNIGHTMODE
```

지도 주/야 타입을 반환합니다.

---

#### `setMapEnableRotate`

```kotlin
fun setMapEnableRotate(enable: Boolean)
```

지도 회전 가능 여부를 설정합니다.

| 파라미터 | 설명 |
|---|---|
| `enable` | `true`: 회전 가능 / `false`: 회전 불가 |
| **Return** | void |

---

#### `setMapMoveChangedListener`

```kotlin
fun setMapMoveChangedListener(listener: OnMapMoveChangedListener)
```

지도 제스처를 통한 지도 이동 이벤트를 등록합니다.

| 파라미터 | 설명 |
|---|---|
| `listener` | `OnMapMoveChangedListener` — 이동 시작/종료 Callback |
| **Return** | void |

---

#### `forceHideMapComponent` <Badge type="danger" text="deprecated 20/10/14" />

```kotlin
@deprecated
fun forceHideMapComponent()
```

---

#### `forceHideMapComponent`

```kotlin
fun forceHideMapComponent(componentID: MAPCOMPONENT, hide: Boolean)
```

주행 지도 컴포넌트를 강제로 숨기거나 표출합니다.

| 파라미터 | 설명 |
|---|---|
| `componentID` | 지도 컴포넌트 타입 |
| `hide` | `true`: 미표출 / `false`: 표출 |
| **Return** | void |

---

#### `setTurnViewTaxiIcon`

```kotlin
fun setTurnViewTaxiIcon(showTaxiIcon: Boolean)
```

주행화면 TBT 아이콘을 변경합니다.

| 파라미터 | 설명 |
|---|---|
| `showTaxiIcon` | `true`: 탑승/하차 아이콘 / `false`: 출발/도착 아이콘 |
| **Return** | void |

---

#### `setMapRouteColor`

```kotlin
fun setMapRouteColor(color: MAPROUTECOLOR)
```

주행화면 경로선 색상을 변경합니다.

---

#### `setAptPoiCollisionIgnored`

```kotlin
fun setAptPoiCollisionIgnored(bEnable: Boolean)
```

아파트 동/호/명칭 충돌 처리 대상 제외를 설정합니다. (Default: `false`)

| 파라미터 | 설명 |
|---|---|
| `bEnable` | `true`: 충돌 시 모두 표출 / `false`: 충돌 처리 |
| **Return** | void |

---

### 검색

#### `runSearch`

```kotlin
fun runSearch(query: String, lat: Double, lon: Double, listener: OnSearchListener)
```

통합검색 결과를 반환합니다.

| 파라미터 | 설명 |
|---|---|
| `query` | 검색어 |
| `lat` | 기준 좌표 위도 |
| `lon` | 기준 좌표 경도 |
| `listener` | `OnSearchListener` — 성공 여부 및 결과 반환 |
| **Return** | void |

---

#### `runRecommendWord`

```kotlin
fun runRecommendWord(query: String, listener: OnRecommendWordListener)
```

추천어 검색 결과를 반환합니다.

---

#### `getJibunAddr`

```kotlin
fun getJibunAddr(lat: Double, lon: Double): String
```

축약형 지번주소를 반환합니다.

---

#### `getFullAddr`

```kotlin
fun getFullAddr(lat: Double, lon: Double, roadAddrType: Boolean): String
```

전체 주소를 반환합니다.

- 도로명 주소 요청 시 도로명 주소가 없으면 지번주소 반환

| 파라미터 | 설명 |
|---|---|
| `lat` | 위도 [WGS84] |
| `lon` | 경도 [WGS84] |
| `roadAddrType` | `true`: 도로명 주소 / `false`: 지번 주소 |
| **Return** | `String` — 전체 주소 |

---

#### `getCurrentRoadName`

```kotlin
fun getCurrentRoadName(): String
```

현재 위치 도로명을 반환합니다.

- 경로가 존재하고 현재위치(자차)가 경로 위에 있는 경우에만 반환

---

### 길찾기

#### `runRoute`

```kotlin
fun runRoute(
    start: RoutePtItem,
    goal: RoutePtItem,
    viaList: List<RoutePtItem>?,
    optionType: List<ROUTEOPTIONTYPE>?,
    extraInfo: RouteExtraInfo?,
    listener: OnRouteMultiListener
)
```

멀티 경로를 탐색합니다.

| 파라미터 | 설명 |
|---|---|
| `start` | 출발지 정보 |
| `goal` | 목적지 정보 |
| `viaList` | 경유지 리스트 (최대 100개) |
| `optionType` | 탐색 옵션 (`null` 입력 시 Default 옵션 사용) |
| `extraInfo` | 부가정보 (`null` 입력 가능) |
| `listener` | `OnRouteMultiListener` — 탐색 결과(경로 ID 리스트) 반환 |
| **Return** | void |

---

#### `runSingleRoute`

```kotlin
fun runSingleRoute(
    start: RoutePtItem,
    goal: RoutePtItem,
    viaList: List<RoutePtItem>?,
    option: ROUTEOPTIONTYPE,
    extraInfo: RouteExtraInfo?,
    listener: OnRouteSingleListener
)
```

단일 경로를 탐색합니다.

---

#### `runReRouteChangeVia`

```kotlin
fun runReRouteChangeVia(viaList: List<RoutePtItem>?, listener: OnReRouteResultListener)
```

주행 중 경유지를 변경하여 재탐색합니다.

- 기존 안내하던 경로가 존재해야 함
- 기존 경유지는 삭제되고 새로운 경유지로 안내

---

#### `makeRouteSumInfo`

```kotlin
fun makeRouteSumInfo(routeID: String): RouteSumInfo
```

경로의 간단한 요약 정보를 반환합니다.

---

#### `getRoutePtInfo`

```kotlin
fun getRoutePtInfo(routeID: String, ptType: ROUTEPTTYPE): RoutePtItem
```

경로의 출발지 / 목적지 정보를 반환합니다.

---

#### `getRoutePtViaInfo`

```kotlin
fun getRoutePtViaInfo(routeID: String, viaIndex: Int): RoutePtItem
```

경로 내 특정 경유지 정보를 반환합니다.

---

#### `getRoutePtRemainViaInfo`

```kotlin
fun getRoutePtRemainViaInfo(): List<RoutePtItem>?
```

주행 중인 경로에서 아직 지나가지 않은 경유지 정보를 반환합니다.

---

#### `getRouteLinePoints`

```kotlin
fun getRouteLinePoints(routeID: String): JSONArray
```

경로 Line 좌표열을 반환합니다.

---

#### `cancelRoute`

```kotlin
fun cancelRoute()
```

경로를 취소합니다.

---

#### `onOptimalRoute`

```kotlin
fun onOptimalRoute(
    start: RoutePtItem,
    goal: RoutePtItem,
    viaList: List<RoutePtItem>?,
    optionType: List<ROUTEOPTIONTYPE>?,
    extraInfo: RouteExtraInfo?,
    listener: OnRouteMultiListener
)
```

멀티 경로 최적지점 탐색입니다.

설정한 목적지의 최적지점을 탐색하여 최적 입구점이 있는 경우 해당 위치로 변경하여 경로탐색합니다.

---

### 주행

#### `runGuidance`

```kotlin
fun runGuidance(routeID: String): Int
```

주행을 시작합니다.

- 선택된 경로 ID를 제외한 나머지 경로는 삭제됨

| 파라미터 | 설명 |
|---|---|
| `routeID` | 경로 ID |
| **Return** | `Int` — `0`: 성공 / 그 외: 실패 |

---

#### `isSimulation`

```kotlin
fun isSimulation(): Boolean
```

모의 주행 여부를 반환합니다.

---

#### `startSimulation`

```kotlin
fun startSimulation(routeID: String): Int
```

모의 주행을 시작합니다.

---

#### `finishSimulation`

```kotlin
fun finishSimulation(): Int
```

모의 주행을 종료합니다.

---

#### `getRemainRouteInfo`

```kotlin
fun getRemainRouteInfo(): RouteRemainInfo
```

주행 중 남은 거리/시간/요금 정보를 반환합니다.

- 안내시작/모의주행 시작 이후 호출 필요

---

#### `getRemainViaRouteInfo`

```kotlin
fun getRemainViaRouteInfo(): List<RouteViaRemainInfo>?
```

주행 중 지나가지 않은 경유지의 남은 거리/시간 정보를 반환합니다.

---

#### `getTotalRemainRouteInfo`

```kotlin
fun getTotalRemainRouteInfo(routeID: String): RouteRemainInfo
```

경로의 총 거리/시간/요금 정보를 반환합니다.

- 안내시작/모의주행 시작 전 호출 필요

---

#### `setGuidanceSettings`

```kotlin
fun setGuidanceSettings(settings: List<GUIDANCESETTING>?)
```

안전운행정보 안내 설정을 변경합니다.

- List에 존재하는 값만 On, 나머지 Off
- `null` 입력 시 아이나비 Default 옵션 적용

---

#### `getGuidanceSettings`

```kotlin
fun getGuidanceSettings(): List<GUIDANCESETTING>
```

현재 안전운행정보 안내 설정을 반환합니다.

---

### 기타

#### `destroyNavi`

```kotlin
fun destroyNavi()
```

SDK 메모리를 해제하고 종료합니다. App 종료 시 호출 필요합니다.

---

#### `getNaviVersionName` <Badge type="danger" text="deprecated 20/01/20" />

```kotlin
@deprecated
fun getNaviVersionName(): String
```

---

#### `getNaviVersionCode` <Badge type="danger" text="deprecated 20/01/20" />

```kotlin
@deprecated
fun getNaviVersionCode(): Int
```

---

#### `getNaviSDKVersion`

```kotlin
fun getNaviSDKVersion(): String
```

SDK 버전을 반환합니다.

---

#### `setApplicationStatus`

```kotlin
fun setApplicationStatus(status: APPSTATUS)
```

Application 상태를 설정합니다.

| 파라미터 | 설명 |
|---|---|
| `status` | `APPSTATUS` — App 상태 |
| **Return** | void |

---

#### `setOrientation`

```kotlin
fun setOrientation(orientation: Int)
```

화면 가로/세로 상태를 설정합니다.

| 값 | 설명 |
|---|---|
| `0` | `ORIENTATION_UNDEFINED` — 단말의 회전 상태 사용 (Default) |
| `1` | `ORIENTATION_PORTRAIT` — 세로 고정 |
| `2` | `ORIENTATION_LANDSCAPE` — 가로 고정 |

---

#### `setNaviVolume`

```kotlin
fun setNaviVolume(vol: Float)
```

내비게이션 볼륨을 설정합니다. (단말기 미디어 볼륨과 별개)

| 파라미터 | 설명 |
|---|---|
| `vol` | 볼륨 (`0.0 ~ 1.0`) |
| **Return** | void |

---

#### `getNaviVolume`

```kotlin
fun getNaviVolume(): Float
```

내비게이션 볼륨을 반환합니다.

---

#### `getCurrentPos`

```kotlin
fun getCurrenPos(): NaviLocation
```

현재 위치를 반환합니다.

---

#### `setNaviEventListener`

```kotlin
fun setNaviEventListener(listener: OnNaviEventListener)
```

내비게이션 버튼 이벤트 Callback을 등록합니다.

---

#### `setRouteChangedListener`

```kotlin
fun setRouteChangedListener(listener: OnRouteChangedListener)
```

주행 재탐색 성공 이벤트 Callback을 등록합니다.

---

#### `setDrivingStatusListener`

```kotlin
fun setDrivingStatusListener(listener: OnDrivingStatusListener, nearGoalDist: Int)
```

주행 시작/도착 이벤트 및 목적지 도착 시 요금 정보 Callback을 등록합니다.

- 목적지 도착 요금은 도착 이벤트 발생 시에만 호출
- 목적지 도착 요금은 실 주행에서만 계산 (모의주행 시 항상 0원)
- 경유지 도착 알림 인덱스는 0부터 시작, 안내 시작 시 전달된 경유지 순서와 동일
- 한번 지나간 경유지는 다시 `onArrivalVia` 이벤트가 호출되지 않음

| 파라미터 | 설명 |
|---|---|
| `listener` | `OnDrivingStatusListener` — 주행 상태, 요금, 경유지 도착 Callback |
| `nearGoalDist` | `NEARGOAL` STATUS 체크 거리 (단위: m, Default: 100m) |
| **Return** | void |

---

#### `setCarSpeedListener`

```kotlin
fun setCarSpeedListener(listener: OnCarSpeedListener)
```

차량 속도 Callback을 등록합니다.

---

#### `setCarType`

```kotlin
fun setCarType(nCarType: CARTYPE)
```

차종을 설정합니다. (요금 및 경로 안내 시 반영)

---

#### `getCarType`

```kotlin
fun getCarType(): CARTYPE
```

현재 설정된 차종을 반환합니다.

---

#### `setTurnViewBackgroundColor`

```kotlin
fun setTurnViewBackgroundColor(strColor: String): Boolean
```

차기 TurnView 배경색을 변경합니다.

| 파라미터 | 설명 |
|---|---|
| `strColor` | 16진수 컬러값 또는 색상명 (`#RRGGBB`, `#AARRGGBB`, `"red"`, `"black"` 등) |
| **Return** | 배경색 적용 여부 |

---

#### `getTurnViewBackgroundColor`

```kotlin
fun getTurnViewBackgroundColor(): String
```

현재 적용된 차기 TurnView 배경색 문자열을 반환합니다.

---

#### `setNextTurnViewBackgroundColor`

```kotlin
fun setNextTurnViewBackgroundColor(strColor: String): Boolean
```

차차기 TurnView 배경색을 변경합니다.

---

#### `getNextTurnViewBackgroundColor`

```kotlin
fun getNextTurnViewBackgroundColor(): String
```

현재 적용된 차차기 TurnView 배경색 문자열을 반환합니다.

---

#### `setArrivalViewBackgroundColor`

```kotlin
fun setArrivalViewBackgroundColor(strColor: String): Boolean
```

목적지 도착 View 배경색을 변경합니다.

---

#### `getArrivalViewBackgroundColor`

```kotlin
fun getArrivalViewBackgroundColor(): String
```

목적지 도착 View 배경색 문자열을 반환합니다.

---

#### `resetTurnViewsBackgroundColor`

```kotlin
fun resetTurnViewsBackgroundColor()
```

차기/차차기/목적지 도착 View 배경색을 기본값으로 초기화합니다.

기본값:
- `TURN`: `#f21e55f7`
- `NEXT_TURN`: `#f2122672`
- `ARRIVAL`: `#f21e55f7`

---

#### `setVisibleExtendView`

```kotlin
fun setVisibleExtendView(bIsShow: Boolean)
```

확대도 표출 여부를 설정합니다.

- 확대도는 모의주행/안내시작 후 표출

| 파라미터 | 설명 |
|---|---|
| `bIsShow` | `true`: 표출 / `false`: 미표출 (Default) |
| **Return** | void |

---

#### `getVisibleExtendView`

```kotlin
fun getVisibleExtendView(): Boolean
```

확대도 표출 여부를 반환합니다.

---

#### `setDrawBackgroundTBT`

```kotlin
fun setDrawBackgroundTBT(bisDrawBackgroundTBT: Boolean)
```

백그라운드 TBT View 표출 여부를 설정합니다.

- '안내시작' 상태일 때만 표출

| 파라미터 | 설명 |
|---|---|
| `bisDrawBackgroundTBT` | `true`: 표출 / `false`: 미표출 (Default) |
| **Return** | void |

---

#### `getDrawBackgroundTBT`

```kotlin
fun getDrawBackgroundTBT(): Boolean
```

백그라운드 TBT View 표출 설정 여부를 반환합니다.

---

#### `forceHideBackgroundTBT`

```kotlin
fun forceHideBackgroundTBT()
```

백그라운드 TBT를 강제로 숨깁니다.

---

#### `setBringToFrontListener`

```kotlin
fun setBringToFrontListener(listener: OnBringToFrontListener)
```

백그라운드 TBT 클릭 이벤트 리스너를 설정합니다.

---

#### `setLowResolution`

```kotlin
fun setLowResolution(bIsUseLowResolution: Boolean)
```

저해상도 단말 강제 설정 여부를 지정합니다.

| 파라미터 | 설명 |
|---|---|
| `bIsUseLowResolution` | `true`: 저해상도 모드 강제 적용 / `false`: 적용 안함 (Default) |
| **Return** | void |

---

### 화물차 옵션

#### `setCarHeight`

```kotlin
fun setCarHeight(nHeight: Int): Boolean
```

차량 높이를 설정합니다. (cm, 0 ~ 1000)

설정 시 높이제한구역 회피 안내가 적용됩니다.

---

#### `getCarHeight`

```kotlin
fun getCarHeight(): Int
```

현재 설정된 차량 높이값을 반환합니다. (default: 0)

---

#### `setCarWeight`

```kotlin
fun setCarWeight(nWeight: Int): Boolean
```

차량 중량을 설정합니다. (kg, 0 ~ 60000)

---

#### `getCarWeight`

```kotlin
fun getCarWeight(): Int
```

현재 설정된 차량 중량값을 반환합니다. (default: 0)

---

#### `setAvoidUturn`

```kotlin
fun setAvoidUturn(nAvoidUturn: Int): Boolean
```

왕복 n차선 이하 유턴 회피를 설정합니다.

---

#### `getAvoidUturn`

```kotlin
fun getAvoidUturn(): Int
```

현재 설정된 유턴 회피 차선을 반환합니다. (default: 0)

---

#### `setAvoidNarrowRoad`

```kotlin
fun setAvoidNarrowRoad(nAvoidNarrowRoad: Int): Boolean
```

왕복 n차선 이하 도로 회피 여부를 설정합니다.

---

#### `getAvoidNarrowRoad`

```kotlin
fun getAvoidNarrowRoad(): Int
```

현재 설정된 n차선 이하 도로 회피 차선을 반환합니다. (default: 0)

---

#### `setAvoidRestriction`

```kotlin
fun setAvoidRestriction(bIsAvoidRestriction: Boolean)
```

화물차 통행제한구간 회피 여부를 설정합니다.

| 파라미터 | 설명 |
|---|---|
| `bIsAvoidRestriction` | `true`: 구간 회피 / `false`: 회피 안함 |

---

#### `getAvoidRestriction`

```kotlin
fun getAvoidRestriction(): Boolean
```

현재 설정된 화물차 통행제한구간 회피 여부를 반환합니다. (default: `false`)

---

#### `getNearestVertexIndex`

```kotlin
fun getNearestVertexIndex(): Int
```

현위치에서 진행방향으로 탐색 좌표 리스트 내 가장 가까운 좌표 인덱스를 반환합니다.

---

#### `getZoneLimit`

```kotlin
fun getZoneLimit(): Boolean
```

현재 설정된 상수원보호구역 탐색 회피 여부를 반환합니다. (default: `false`)

---

#### `setZoneLimit`

```kotlin
fun setZoneLimit(bIsAvoidZoneLimit: Boolean)
```

상수원보호구역 회피 여부를 설정합니다.

---

#### `getAvoidEvasion`

```kotlin
fun getAvoidEvasion(): Boolean
```

현재 설정된 위수지역 회피 여부를 반환합니다. (default: `false`)

---

#### `setAvoidEvasion`

```kotlin
fun setAvoidEvasion(bIsSetEvasion: Boolean)
```

위수지역 회피 여부를 설정합니다.

---

## SDK Interface Reference

### `OnNaviInitListener`

```kotlin
interface OnNaviInitListener {
    fun onSuccess()
    fun onFail(errCode: Int, errMsg: String)
}
```

### `OnNaviEventListener`

```kotlin
interface OnNaviEventListener {
    fun onEvent(e: NaviEvent)
}
```

### `OnMapClickListener`

```kotlin
interface OnMapClickListener {
    fun onMapLongClick(lat: Double, lon: Double)
}
```

### `OnMapMoveChangedListener`

```kotlin
interface OnMapMoveChangedListener {
    fun onStart()
    fun onStop()
}
```

### `OnSearchListener`

```kotlin
interface OnSearchListener {
    fun onSuccess(result: SearchResult)
    fun onFail(errCode: Int, errMsg: String)
}
```

### `OnRecommendWordListener`

```kotlin
interface OnRecommendWordListener {
    fun onSuccess(result: ArrayList<RecommendWord>)
    fun onFail(errCode: Int, errMsg: String)
}
```

### `OnRouteMultiListener`

```kotlin
interface OnRouteMultiListener {
    fun onSuccess(result: ArrayList<String>, same: Boolean)
    fun onFail(errCode: Int, msg: String)
}
```

### `OnRouteSingleListener`

```kotlin
interface OnRouteSingleListener {
    fun onSuccess(result: String)
    fun onFail(errCode: Int, msg: String)
}
```

### `OnReRouteResultListener`

```kotlin
interface OnReRouteResultListener {
    fun onSuccess(result: String)
    fun onFail(errCode: Int, msg: String)
}
```

### `OnRouteChangedListener`

```kotlin
interface OnRouteChangedListener {
    fun onChanged(routeID: String)
}
```

### `OnDrivingStatusListener`

```kotlin
interface OnDrivingStatusListener {
    fun onStatus(status: DRIVINGSTATUS)
    fun onArrivalFee(fee: Int)
    fun onArrivalVia(nIndex: Int) // v0.0.29 부터 추가
}
```

### `OnCarSpeedListener`

```kotlin
interface OnCarSpeedListener {
    fun onSpeed(speed: Int)
}
```

### `OnBringToFrontListener`

```kotlin
interface OnBringToFrontListener {
    fun getBringToFrontPendingIntent(context: Context?): PendingIntent?
}
```

---

## SDK Structure Reference

### `NaviLocation`

```kotlin
class NaviLocation {
    var lat: Double    // 위도
    var lon: Double    // 경도
    var angle: Double  // 정북 기준 각도 (0.0 ~ 360.0) — v0.0.20부터 Double, 이전은 Int
    var speed: Double  // 속도
}
```

### `SearchResult`

```kotlin
class SearchResult {
    var query: String               // 검색어
    var items: ArrayList<SearchItem>? // 검색 결과 리스트
}
```

### `SearchItem`

```kotlin
class SearchItem {
    var dpLat: Double    // 지도 위치보기 위도
    var dpLon: Double    // 지도 위치보기 경도
    var rpLat: Double    // 길 찾기 위도
    var rpLon: Double    // 길 찾기 경도
    var mainTitle: String // 검색 결과 타이틀
    var addrJibun: String // 지번 주소
    var addrRoad: String  // 도로명 주소
    var distane: Int      // 기준좌표와의 거리
}
```

### `RecommendWord`

```kotlin
class RecommendWord {
    var recommendWord: String // 추천어
    var frequency: Int        // 추천어 조회 빈도 수
}
```

### `RoutePtItem`

```kotlin
class RoutePtItem {
    var name: String   // 지점 명칭
    var rpLat: Double  // 길 찾기 위도
    var rpLon: Double  // 길 찾기 경도
    var dpLat: Double  // 지도 위치보기 위도
    var dpLon: Double  // 지도 위치보기 경도
    var angle: Int     // 길 찾기 각도 (0 ~ 359)
}
```

### `RouteSumInfo`

```kotlin
class RouteSumInfo {
    var optionName: String // 길찾기 시 사용된 옵션 명칭
    var dist: String       // 경로 총 거리
    var time: String       // 목적지 도착 예정 시간
    var fee: String        // 총 톨게이트 요금
}
```

### `NaviEvent`

```kotlin
class NaviEvent {
    var eventType: NAVIEVENTTYPE // 이벤트 타입
}
```

### `RouteRemainInfo`

```kotlin
class RouteRemainInfo {
    var remainTime: Int  // 남은 시간 (초 단위)
    var remainDist: Int  // 남은 거리 (m 단위)
    var remainFee: Int   // 남은 요금 (원 단위), 계산 실패 시 -1
}
```

### `RouteViaRemainInfo`

```kotlin
class RouteViaRemainInfo {
    var remainTime: Int  // 경유지까지 남은 시간 (초 단위)
    var remainDist: Int  // 경유지까지 남은 거리 (m 단위)
}
```

### `RouteExtraInfo`

```kotlin
class RouteExtraInfo {
    var baseURL: String // 부가정보 외부연동 URL
}
```

---

## SDK Define Reference

### `MAPVIEWMODE`

```kotlin
enum class MAPVIEWMODE {
    VIEWMODE_3D,     // 3D 뷰
    VIEWMODE_2D,     // 2D 회전 뷰
    VIEWMODE_2D_FIX  // 2D 고정 뷰 (정북 고정)
}
```

### `ICONGRAVITY`

```kotlin
enum class ICONGRAVITY {
    CENTER,        // 이미지 정중앙이 좌표 위치
    CENTER_TOP,    // 이미지 정중앙 하단이 좌표 위치 (이미지 상단 이동)
    LEFT_TOP,      // 이미지 우측하단이 좌표 위치 (이미지 좌측 상단 이동)
    RIGHT_TOP,     // 이미지 좌측하단이 좌표 위치 (이미지 우측 상단 이동)
    LEFT_BOTTOM,   // 이미지 우측상단이 좌표 위치 (이미지 좌측 하단 이동)
    RIGHT_BOTTOM   // 이미지 좌측상단이 좌표 위치 (이미지 우측 하단 이동)
}
```

### `MAPFONTSIZETYPE`

```kotlin
enum class MAPFONTSIZETYPE {
    NORMAL, // 기본 사이즈
    LARGE   // 확대 사이즈
}
```

### `MAPDAYNIGHTMODE`

```kotlin
enum class MAPDAYNIGHTMODE {
    AUTO,        // 시간대별 주/야 자동 변경
    ALWAYSDAY,   // 항상 주간
    ALWAYSNIGHT  // 항상 야간
}
```

### `ROUTEPTTYPE`

```kotlin
enum class ROUTEPTTYPE {
    START, // 출발지
    GOAL   // 목적지
}
```

### `APPSTATUS`

```kotlin
enum class APPSTATUS {
    BACKGROUND,          // App이 Background 상태
    SCREEN_OFF_BACKGROUND, // 스크린이 꺼진 상태
    FOREGROUND           // App이 Foreground 상태
}
```

### `NAVIEVENTTYPE`

```kotlin
enum class NAVIEVENTTYPE {
    NONE,            // 없음
    MAINMENU,        // 하단바 좌측 메인메뉴 버튼
    MULTIMENU,       // 하단바 우측 멀티메뉴 버튼
    EXITAPPLICATION, // 도착 하단바 앱 종료 버튼
    VOLUMEMAPBUTTON, // 지도이동 시 볼륨 버튼
    CURRRENTPOSITION, // 지도이동 시 현위치로 버튼
    ALLROUTEVIEW     // 전체경로보기
}
```

### `DRIVINGSTATUS`

```kotlin
enum class DRIVINGSTATUS {
    START,    // 주행 시작
    NEARGOAL, // 목적지 근처 (~100m)
    ARRIVAL   // 주행 완료
}
```

### `ROUTEOPTIONTYPE`

```kotlin
enum class ROUTEOPTIONTYPE {
    TIMEPRIOTY,         // 빠른길
    RECOMMEND,          // 추천길
    FREEROAD,           // 무료길
    BEGINNER,           // 편한길
    MOTOCYCLE,          // 이륜차
    HIGHWAYPRIOTY,      // 고속도로우선
    DISTANCEPRIOTY,     // 최단거리
    // v0.0.27 화물차 전용 옵션
    TRUCK,              // 실시간 만차
    TRUCK_FREEROAD,     // 실시간 만차(무료)
    TRUCK2,             // 실시간 공차
    TRUCK2_FREEROAD,    // 실시간 공차(무료)
    TRUCK_NATIONALWAY,  // 국도 만차
    TRUCK2_NATIONALWAY  // 국도 공차
}
```

### `MAPCOMPONENT`

```kotlin
enum class MAPCOMPONENT {
    ALL_COMPONENT,              // 모든 지도 컴포넌트
    ANGLE_BTN,                  // 나침반 버튼
    CUR_ON_BTN,                 // 현위치로 버튼
    ZOOM_IN_OUT_BTN,            // 확대/축소 버튼
    BOTTOM_NORMAL_VIEW,         // 지도 하단 뷰 (경로 없는 경우)
    BOTTOM_DRIVING_VIEW,        // 지도 하단 뷰 (경로 존재하는 경우)
    TOP_ARRIVAL_VIEW,           // 목적지 도착 상단 뷰
    BOTTOM_ARRIVAL_VIEW,        // 목적지 도착 하단 뷰
    ROUTE_TRAFFIC_STATUS_BAR,   // 주행 중 우측 교통정보 바
    BOTTOM_NORMAL_SIDE_BUTTON,  // 지도 하단 뷰 버튼 (경로 없는 경우)
    BOTTOM_DRIVING_SIDE_BUTTON, // 지도 하단 뷰 버튼 (경로 존재하는 경우)
    BOTTOM_ARRIVAL_LEFT_BUTTON  // 목적지 도착 하단바 좌측 버튼
}
```

### `MAPSTYLE` <Badge type="danger" text="deprecated v0.9.0" />

```kotlin
enum class MAPSTYLE {
    DEFAULT, // 아이나비 지도
    MAPBOX   // Mapbox Style 지도
}
```

### `GUIDANCESETTING`

```kotlin
enum class GUIDANCESETTING {
    MOVE_OVERSPEED,      // 이동식 과속 단속 카메라
    MOVE_BOX_OVERSPEED,  // 박스형 과속 단속 카메라
    INTERRUPT,           // 끼어들기 단속
    OVERLOAD,            // 과적단속
    FIX_TRAFFIC,         // 교통정보 수집 카메라
    CURVE,               // 급커브 구간
    ACCIDENT,            // 사고다발 구간
    SPEED_BUMP,          // 과속방지턱
    SCHOOL_ZONE,         // 스쿨존/실버존
    WILDANIMAL,          // 야생동물 출현
    TRAIN_TRACK,         // 철도건널목
    ROAD_DROP,           // 낙석구간
    GREEN_AREA           // 녹색교통지역 진입
}
```

### `MAPROUTECOLOR`

```kotlin
enum class MAPROUTECOLOR {
    TRAFFIC, // 실시간 교통정보
    RED,     // 빨간색
    ORANGE,  // 주황색
    YELLOW,  // 노란색
    GREEN,   // 초록색
    BLUE,    // 파란색 (Default)
    INDIGO   // 군청색
}
```

### `CARTYPE`

```kotlin
enum class CARTYPE(val value: Int) {
    TYPE_1(0), // 1종(소형차) — 일반승용, 승합16인 이하, 화물2.5톤 미만
    TYPE_2(1), // 2종(중형차) — 승합32인 이하, 화물5.5톤 이하
    TYPE_3(2), // 3종(대형차) — 승합33인 이상, 화물10톤 미만
    TYPE_4(3), // 4종(대형화물차) — 화물20톤 미만
    TYPE_5(4), // 5종(특수화물차) — 화물20톤 이상
    TYPE_6(5), // 경차 — 1종: 통행료 할인 적용
    TYPE_7(6); // 이륜차

    companion object {
        fun from(findValue: Int): CARTYPE = values().first { it.value == findValue }

        fun getTitle(value: CARTYPE): String = when (value) {
            TYPE_1 -> "1종(소형차)"
            TYPE_2 -> "2종(중형차)"
            TYPE_3 -> "3종(대형차)"
            TYPE_4 -> "4종(대형화물차)"
            TYPE_5 -> "5종(특수화물차)"
            TYPE_6 -> "경차"
            TYPE_7 -> "이륜차"
        }

        fun getDescription(value: CARTYPE): String = when (value) {
            TYPE_1 -> "일반승용, 승합16인 이하, 화물2.5톤 미만"
            TYPE_2 -> "승합32인 이하, 화물5.5톤 이하"
            TYPE_3 -> "승합33인 이상, 화물10톤 미만"
            TYPE_4 -> "화물20톤 미만"
            TYPE_5 -> "화물20톤 이상"
            TYPE_6 -> "1종: 통행료 할인 적용"
            TYPE_7 -> ""
        }
    }
}
```

---

## 코드 표

| 코드 값 | 설명 |
|---|---|
| 0 | 성공 |
| 1 | 길찾기 실패 |
| 3 | 목적지가 물리적 섬 도로 |
| 4 | 유효하지 않은 출발지 |
| 5 | 유효하지 않은 목적지 |
| 9 | 요청 파라미터 오류 |
| 11 | 메모리 할당 실패 |
| 15 | 파일 오픈 실패 |
| 17 | 메모리 할당 실패 |
| 19 | 파일 읽기 실패 |
| 20 | 파일 쓰기 실패 |
| 21 | 유효하지 않은 지도 데이터 |
| 22 | 요청 파라미터 오류 |
| 23 | Projection 실패 |
| 25 | DB 오류 |
| 32 | 길찾기 가능 거리 초과 |
| 35 | 두 지점이 너무 가까운 경우 |
| 36 | 목적지 오류 |
| 39 | 논리적 길찾기 오류 |
| 43 | 차량이 진입할 수 없는 도로 |
| 48 | 소켓 연결 실패 |
| 49 | 결과 생성 실패 |
| 96 | 잘못된 경로 ID |
| 97 | Checksum 오류 |
| 120 | 최적경로 (재탐색 성공했으나 더 좋은 경로가 없는 경우) |
| 121 | 목적지 부근으로 재탐색이 필요 없는 경우 |
| 300 | 미지원 서비스 |
| 800 | 서버 파일 저장 에러 |
| 900 | 인증 실패 |
| 901 | 인증 정보가 변경되어 앱 재시작 필요 |
| 902 | 입력 파라미터 오류 |
| 903 | 조회결과 없음 |
| 904 | 존재하지 않는 파일 요청 |
| 905 | DB 연동 에러 |
| 907 | 경로 ID 오류 |
| 931 | 유효하지 않은 좌표 |
| 933 | 유효하지 않은 검색어 |
| 1000 | 인증 데이터 오류 |
| 1001 | 인증 데이터 오류 |
| 1002 | 잘못된 요청 형식으로 인증 |
| 1003 | 인증 데이터가 존재하지 않음 |
| 1004 | 유효하지 않은 Unique Key (Null 또는 Empty) |
| 1005 | 유효하지 않은 Unique Key (20자 초과) |
| 2000 | 처리되지 않은 예외 발생 |
| 2001 | 유효하지 않은 출발지 |
| 2003 | 유효하지 않은 목적지 |
| 2006 | 서버 응답 오류 |
| 2007 | 버퍼 NULL |
| 2008 | 데이터 파싱 실패 |
| 2010 | 패킷 생성 실패 |
| 2011 | 패킷 생성 실패 |
| 2012 | 다운로드 실패 |
| 2013 | 유효하지 않은 파라미터 |
| 3000 | 알 수 없는 인증 오류 |
| 3001 | 초기 지도 다운로드 실패 |
| 3002 | 초기 지도 다운로드 실패 |
| 3003 | 지도 URL 오류 |
| 3004 | 안전운행 URL 오류 |
| 3005 | 안전운행 URL 오류 |
| 3006 | 안전운행 다운로드 실패 |
| 3007 | 인증 실패 |
| 3008 | 인증 업데이트 실패 |
| 3050 | 지도 엔진 초기화 실패 |
| 3051 | SMR 엔진 초기화 실패 |
| 3052 | 맵매칭 엔진 초기화 실패 (위치권한 미획득 시에도 발생 가능) |
| 3053 | 안내 엔진 초기화 실패 |
| 3054 | 기타 엔진 초기화 실패 |
| 4000 | 검색결과 없음 |
| 4001 | 유효하지 않은 검색어 |
| 5000 | 출발지 오류 |
| 5001 | 목적지 오류 |
| 5002 | 출발지가 유효 범위를 벗어난 경우 |
| 5003 | 목적지가 유효 범위를 벗어난 경우 |
| 5004 | 출발지와 목적지가 가까워 길찾기 불가 |
| 5005 | 안내 중인 경로가 유효하지 않은 경우 |
| 6000 | 검색어 오류 |
