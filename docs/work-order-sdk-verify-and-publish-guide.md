---
title: "작업지시서 — SDK 신 버전 샘플앱 검증 & 개발자 가이드 배포"
type: work-order
repo: inavi-navigation-demo-android
description: "신규 배포된 iNavi Navigation SDK 버전을 샘플앱에서 검증하고, VitePress 개발자 가이드를 갱신·배포하는 반복 작업 절차. context-free LLM이 이 문서만으로 수행 가능하도록 작성됨."
audience: context-free-llm
related:
  - repo: navisdk_aos
    path: docs/workflow-dev_sdk_module-to-develop.md
    note: 선행 작업 — dev_sdk_module→develop 머지 및 SDK 버전 배포(jfrog) 절차
  - repo: navisdk_aos
    path: docs/2026-07-15-flutter-to-develop-cherry-pick.md
    note: 2026-07-15 flutter 반영·2.0.0 배포 실행 기록
---

# 작업지시서 — SDK 신 버전 샘플앱 검증 & 개발자 가이드 배포

> **이 문서만으로 수행하는 것을 전제로 한다(context-free).** 배경지식 없이도 아래 경로·명령·통과기준을 그대로 따르면 완료된다.
> **선행 조건**: iNavi Navigation SDK 신 버전이 이미 jfrog(`repo.inavi.com`)에 발행되어 있어야 한다. 발행 절차는 `navisdk_aos` repo의 `docs/workflow-dev_sdk_module-to-develop.md` 참조(별도 저장소).

## 대상 저장소
- **이 작업 저장소**: `inavi-navigation-demo-android` (github `inavi-systems/inavi-navigation-demo-android`)
  - 로컬 경로 예: `~/Workspace/Projects/inavi-navigation-demo-android`
- 샘플앱: `app/` 모듈
- 개발자 가이드: VitePress. 본문 `docs/index.md`, 설정 `docs/.vitepress/config.mts`
- 배포: `master` 머지 → GitHub Actions(`.github/workflows/deploy-docs.yml`) → GitHub Pages
- 라이브 URL: https://inavi-systems.github.io/inavi-navigation-demo-android/android-developer-guide/

## SDK 좌표 (고정값)
- groupId: `com.inavisys.navisdk`
- artifactId: `inavi-navigation-sdk`
- artifactory 베이스: `https://repo.inavi.com/artifactory/navigation/`
- 의존성 표기: `com.inavisys.navisdk:inavi-navigation-sdk:<VERSION>`

---

## 절차

### 0. 대상 버전 자동 조회
artifactory 메이븐 메타데이터에서 최신 버전을 얻는다.
```bash
curl -s https://repo.inavi.com/artifactory/navigation/com/inavisys/navisdk/inavi-navigation-sdk/maven-metadata.xml
```
- `<latest>` 값 또는 `<version>` 목록 중 최고 semver를 **`<VERSION>`** 으로 사용.
- **실패 시 조치**: 응답이 없거나 파싱 불가하면 사용자에게 "검증할 대상 버전"을 직접 물어본다. (자동조회 실패는 진행 중단 사유 아님 — 사용자 지정으로 대체)

### 1. 최신 버전 반영 (다운로드 확인)
`app/build.gradle`의 SDK 의존성 라인을 `<VERSION>`으로 교체.
```groovy
implementation 'com.inavisys.navisdk:inavi-navigation-sdk:<VERSION>'
```
- jfrog에서 실제 aar가 내려받아지는지 확인:
```bash
./gradlew :app:assembleDebug --refresh-dependencies
```
- **통과기준**: 의존성 resolve 성공(Could not resolve 없음).
- **실패 시**: 버전이 repo에 없으면 0단계 재확인. `SDK location not found` 이면 아래 사전조건 처리.

> **사전조건 — Android SDK 경로**: 빌드 전 `local.properties`에 `sdk.dir`이 있어야 한다. 없으면 생성(macOS 기본):
> ```bash
> [ -f local.properties ] || echo "sdk.dir=$HOME/Library/Android/sdk" > local.properties
> ```
> (`local.properties`는 gitignore 대상 — 커밋하지 않는다)

### 2. 빌드 + 에뮬레이터 구동 검증
1. 빌드:
   ```bash
   ./gradlew :app:assembleDebug
   ```
   통과기준: `BUILD SUCCESSFUL`.
2. 에뮬레이터 확보:
   ```bash
   adb devices          # 연결된 emulator/device 있으면 그대로 사용
   emulator -list-avds  # 없으면 AVD 목록 확인 후 기동
   # emulator -avd <AVD_NAME> -no-snapshot -no-boot-anim &
   adb wait-for-device
   until [ "$(adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" = "1" ]; do sleep 2; done
   ```
   - **에뮬레이터/AVD가 전혀 없으면**: 사용자에게 알리고, 최소 "빌드 성공"까지만 완료 처리 후 구동 검증은 보류로 명시.
3. 설치 + 실행:
   ```bash
   ./gradlew :app:installDebug
   # 패키지/런처 액티비티 확인 후 실행
   PKG=$(adb shell cmd package list packages | grep -i inavi | head -1 | sed 's/package://; s/\r//')
   adb shell monkey -p "$PKG" -c android.intent.category.LAUNCHER 1
   ```
4. 크래시/ANR 관찰:
   ```bash
   sleep 8
   adb logcat -d | grep -E "FATAL EXCEPTION|ANR in|E AndroidRuntime" | grep -i "$PKG"
   ```
   - **통과기준**: 위 grep 결과 **비어있음**(크래시/ANR/FATAL 없음).
   - 증빙으로 logcat 요약을 결과 보고에 첨부.
- **실패 시**: 크래시 로그의 스택트레이스를 사용자에게 보고하고 중단(신 버전 회귀 가능성).

### 3. 버전 업 문서 작성 — ⚠️ 사용자 컨펌 게이트
갱신 대상 파일:
| 파일 | 내용 |
|---|---|
| `app/build.gradle` | SDK 버전(1단계에서 이미 반영) |
| `README.md` | 의존성 예시 버전 문자열 → `<VERSION>` |
| `docs/index.md` | 의존성 예시 버전 문자열 → `<VERSION>` + **릴리스노트 섹션 추가** |

릴리스노트 작성 절차:
1. **주요 변경 내역은 사용자에게 반드시 물어본다.** (context-free LLM은 SDK 내부 변경을 알 수 없음 — 추측 금지)
   - 질문 예: "`<VERSION>` 릴리스노트에 넣을 주요 변경/신규 기능을 알려주세요."
2. 받은 내용으로 릴리스노트 초안 작성(버전·날짜·주요 변경 목록).
3. **초안 전문을 사용자에게 보여주고 컨펌/수정 요청.**
4. **사용자 승인 전에는 4단계(커밋)로 넘어가지 않는다.**

### 4. 커밋 + 푸시 (master 직접)
> PR 생략, master 직접 머지가 기본(gh-pages 배포 트리거).
```bash
git checkout master && git pull --ff-only
git add app/build.gradle README.md docs/index.md
git commit -m "chore: SDK <VERSION> 검증 및 개발자 가이드 갱신"
git push origin master
```
- **통과기준**: push 성공, `master` origin 동기화.

### 5. gh-pages 반영 확인
1. Actions 빌드 성공 확인:
   ```bash
   gh run list --workflow deploy-docs.yml -L 1
   gh run watch $(gh run list --workflow deploy-docs.yml -L 1 --json databaseId -q '.[0].databaseId')
   ```
   통과기준: 최신 run `completed / success`.
2. 라이브 페이지에 새 버전 노출 확인:
   ```bash
   curl -s https://inavi-systems.github.io/inavi-navigation-demo-android/android-developer-guide/ | grep -o "<VERSION>" | head -1
   ```
   - **통과기준**: `<VERSION>` 문자열이 검색됨.
   - **주의**: GitHub Pages 캐시로 즉시 반영 안 될 수 있음 → Actions 성공 후 1~2분 대기 후 재확인.
- **실패 시**: Actions 로그(`gh run view --log-failed`) 확인 후 사용자 보고.

---

## 완료 체크리스트
- [ ] 0. `<VERSION>` 확정(artifactory 자동조회 or 사용자 지정)
- [ ] 1. `app/build.gradle` 버전 교체 + 의존성 resolve 성공
- [ ] 2. `assembleDebug` 성공 + 에뮬레이터 구동, 크래시/ANR 없음
- [ ] 3. README·docs/index.md 버전 갱신 + 릴리스노트 **사용자 컨펌 완료**
- [ ] 4. master 커밋 + push
- [ ] 5. Actions success + 라이브 URL에 `<VERSION>` 노출 확인

## 반복 사용
새 SDK 버전 배포 시 이 문서를 처음부터 다시 수행한다. 버전 하드코딩 없음(`<VERSION>` = 매 실행 조회값).

## 관련 문서 (별도 저장소 `navisdk_aos`)
- 선행 배포 절차: `navisdk_aos/docs/workflow-dev_sdk_module-to-develop.md`
- 실행 기록(2.0.0): `navisdk_aos/docs/2026-07-15-flutter-to-develop-cherry-pick.md`
