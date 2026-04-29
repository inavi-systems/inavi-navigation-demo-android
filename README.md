# iNaviNavigation SDK for Android Demo

Android 플랫폼에서 아이나비 에어 내비게이션 SDK를 연동하기 위한 데모 프로젝트입니다.

## 개발 가이드

배포 문서: https://inavi-systems.github.io/inavi-navigation-sdk-developer-guide-android/


### SDK 저장소

루트 `build.gradle`의 `allprojects.repositories`에 아이나비 SDK 저장소가 포함되어 있어야 합니다.

```groovy
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri('https://repo.inavi.com/artifactory/navigation/') }
    }
}
```

### SDK 의존성

앱 모듈 `app/build.gradle`에 SDK와 SDK가 사용하는 라이브러리를 추가합니다.

```groovy
dependencies {
    implementation 'com.inavisys.navisdk:inavi-navigation-sdk:0.9.0'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.google.android.gms:play-services-location:21.0.1'
}
```

### AndroidManifest 설정

`app/src/main/AndroidManifest.xml`에 SDK 사용에 필요한 설정을 추가합니다.

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

    <application
        android:usesCleartextTraffic="true">

        <uses-library
            android:name="org.apache.http.legacy"
            android:required="false" />

        <meta-data
            android:name="com.inaviair.sdk.appkey"
            android:value="Input AuthKey" />

    </application>
</manifest>
```

`com.inaviair.sdk.appkey`의 값은 발급받은 AppKey로 교체해야 합니다.

### 샘플 앱 실행

1. Android Studio에서 프로젝트를 엽니다.
2. `app/src/main/AndroidManifest.xml`의 `com.inaviair.sdk.appkey` 값을 실제 AppKey로 변경합니다.
3. Gradle Sync를 실행합니다.
4. Android 기기 또는 에뮬레이터에서 `app` 모듈을 실행합니다.

명령줄에서 디버그 APK를 빌드하려면 다음 명령을 사용합니다.

```bash
./gradlew :app:assembleDebug
```

### 주요 샘플 코드

- `app/src/main/java/com/inavi/airlibsample/MainActivity.kt`: 샘플 앱 진입점
- `app/src/main/java/com/inavi/airlibsample/NaviViewPagerAdapter.kt`: 샘플 페이지 어댑터
- `app/src/main/java/com/inavi/airlibsample/adapter/PageMapAdapter.kt`: 지도 관련 샘플
- `app/src/main/java/com/inavi/airlibsample/adapter/PageRouteAdapter.kt`: 경로 관련 샘플
- `app/src/main/java/com/inavi/airlibsample/adapter/PageSearchAdapter.kt`: 검색 관련 샘플
- `app/src/main/java/com/inavi/airlibsample/adapter/PageTruckAdapter.kt`: 화물차 옵션 관련 샘플

## License
Copyright © 2019 iNavi Systems

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
