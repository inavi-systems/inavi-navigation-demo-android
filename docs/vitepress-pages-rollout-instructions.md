# VitePress + GitHub Pages 개발자 가이드 확장 작업지시서

## 목적

현재 `inavi-navigation-demo-android` 저장소에 적용된 VitePress 기반 개발자 가이드와 GitHub Pages 자동 배포 구성을 다른 SDK 샘플 저장소에도 반복 적용하기 위한 작업지시서다.

이 문서는 두 가지 용도로 작성한다.

- 사람이 저장소별 사전 설정과 검토 포인트를 이해한다.
- LLM이 다른 저장소에서 같은 구성을 빠르게 재현할 수 있도록 입력값, 파일 구조, 검증 기준을 명확히 제공한다.

## 현재 Android 저장소 기준 최종 구성

현재 저장소는 GitHub `master` 브랜치에 머지되면 GitHub Actions가 VitePress 문서를 빌드하고 GitHub Pages에 배포한다.

문서 관련 파일 구조:

```text
README.md
package.json
package-lock.json
.gitignore
.github/
  workflows/
    deploy-docs.yml
docs/
  index.md
  vitepress-pages-rollout-instructions.md
  .vitepress/
    config.mts
  public/
    images/
      bg_tbt.png
      extendview_horizontal.png
      extendview_vertical.png
```

핵심 역할:

- `docs/index.md`: VitePress 사이트의 실제 개발자 가이드 본문
- `README.md`: 저장소 소개, 배포 문서 링크, 샘플 실행 요약
- `docs/.vitepress/config.mts`: 사이트 제목, GitHub Pages base path, 출력 경로, nav/social link 설정
- `.github/workflows/deploy-docs.yml`: GitHub Pages 배포 자동화
- `package.json`, `package-lock.json`: VitePress 실행 환경 고정
- `docs/public/images`: VitePress 정적 이미지 리소스

## 현재 저장소의 주요 설정값

`package.json`:

```json
{
  "name": "inavi-navigation-demo-android",
  "private": true,
  "scripts": {
    "docs:dev": "vitepress dev docs",
    "docs:build": "vitepress build docs",
    "docs:preview": "vitepress preview docs"
  },
  "devDependencies": {
    "vitepress": "^1.6.4"
  }
}
```

`docs/.vitepress/config.mts` 핵심 설정:

```ts
import { defineConfig } from 'vitepress'

export default defineConfig({
  title: 'iNavi Navigation SDK for Android',
  description: 'iNavi Android Navigation SDK Developer Guide',
  base: '/inavi-navigation-demo-android/android-developer-guide/',
  outDir: '.vitepress/dist/android-developer-guide',
  srcExclude: [
    'plans/**'
  ],
  themeConfig: {
    nav: [
      { text: 'Home', link: '/' }
    ],
    outline: {
      level: 2
    },
    socialLinks: [
      { icon: 'github', link: 'https://github.com/inavi-systems/inavi-navigation-demo-android' }
    ]
  }
})
```

`.github/workflows/deploy-docs.yml` 동작:

- 트리거: `master` 브랜치 push, `workflow_dispatch`
- 권한: `contents: read`, `pages: write`, `id-token: write`
- Node.js: 20
- 의존성 설치: `npm ci`
- 문서 빌드: `npm run docs:build`
- Pages artifact 업로드 경로: `docs/.vitepress/dist`
- 배포 액션: `actions/deploy-pages@v4`

현재 배포 URL:

```text
https://inavi-systems.github.io/inavi-navigation-demo-android/android-developer-guide/
```

## 다른 저장소에 적용할 때 사람이 먼저 결정할 항목

작업 전에 저장소별로 아래 값을 확정한다.

| 항목 | 설명 | 예시 |
| --- | --- | --- |
| 기본 브랜치 | Pages 배포를 트리거할 브랜치 | `master`, `main` |
| GitHub 저장소 URL | VitePress social link와 README 링크에 사용 | `https://github.com/inavi-systems/inavi-navigation-demo-ios` |
| Pages base path | GitHub Pages에서 문서가 서비스될 경로 | `/repo-name/`, `/repo-name/developer-guide/` |
| VitePress outDir | 여러 문서를 한 Pages artifact 아래에 둘 때 필요한 출력 위치 | `.vitepress/dist`, `.vitepress/dist/android-developer-guide` |
| 문서 정본 위치 | 개발자 가이드 본문을 어디에 둘지 | `docs/index.md` 권장 |
| README 역할 | 전체 가이드를 둘지, 배포 링크와 요약만 둘지 | 요약 + 링크 권장 |
| 문서 이미지 공개 가능 여부 | 샘플 캡처, UI 이미지, SDK 화면 이미지의 공개 가능성 | `docs/public/images` |
| Node 패키지 매니저 | 현재 구성은 npm 기준 | `npm` |
| GitHub Pages Source | 저장소 Settings에서 Pages 소스를 GitHub Actions로 설정 | GitHub Actions |

특히 `base`는 배포 성공 후 CSS/JS 경로가 깨지는 가장 흔한 원인이다. GitHub Pages URL이 `https://ORG.github.io/REPO/PATH/`라면 `base`는 `/REPO/PATH/`로 맞춘다.

## 적용 원칙

1. 문서 인프라는 샘플 앱 코드와 느슨하게 분리한다.
2. `package-lock.json`을 커밋하고 CI에서는 `npm ci`를 사용한다.
3. VitePress 빌드 산출물과 캐시는 커밋하지 않는다.
4. 개발자 가이드 본문은 저장소별 SDK와 샘플 코드의 실제 상태를 기준으로 작성한다.
5. AppKey, 인증 토큰, 내부 서버 URL, 로컬 경로, 서명 정보는 문서와 샘플 코드에 포함하지 않는다.
6. GitHub README와 VitePress 렌더링은 Markdown 처리 방식이 다를 수 있으므로 최종 문서는 `npm run docs:build`로 검증한다.

## 사람 작업 체크리스트

저장소 준비:

- GitHub 저장소의 기본 브랜치를 확인한다.
- GitHub 저장소 Settings > Pages > Build and deployment > Source를 `GitHub Actions`로 설정한다.
- Actions 실행 권한과 Pages 배포 권한이 조직 정책에 막혀 있지 않은지 확인한다.
- 문서 공개 범위와 이미지 공개 가능 여부를 확인한다.

문서 내용 준비:

- SDK 이름, 플랫폼, 지원 버전, 설치 방법을 확정한다.
- AppKey 또는 인증 설정 방법을 placeholder 기준으로 문서화한다.
- 샘플 앱 실행 절차를 실제 저장소 기준으로 정리한다.
- 주요 샘플 코드 파일 위치를 확인한다.
- SDK artifact 좌표, CocoaPods pod 이름, Flutter plugin package 등 플랫폼별 의존성 값을 검증한다.

릴리즈 전 확인:

- GitHub Actions가 기본 브랜치 push에서 실행되는지 확인한다.
- Pages 배포 URL에서 CSS/JS와 이미지가 깨지지 않는지 확인한다.
- README의 배포 문서 링크가 실제 URL과 일치하는지 확인한다.
- 민감정보가 문서와 diff에 포함되지 않았는지 검색한다.

## LLM 작업 지시 템플릿

다른 저장소에 적용할 때 LLM에게 아래 지시문을 그대로 주고, 대괄호 값만 저장소에 맞게 바꾼다.

```text
현재 저장소에 VitePress 기반 개발자 가이드와 GitHub Pages 자동 배포 구성을 추가해줘.

목표:
- 기본 브랜치 [master 또는 main]에 머지되면 GitHub Actions가 문서를 빌드하고 GitHub Pages에 배포한다.
- 문서 사이트는 VitePress를 사용한다.
- 문서 본문은 docs/index.md에 작성한다.
- README.md에는 저장소 요약, 배포 문서 링크, 최소 실행 방법만 둔다.
- 샘플 앱 코드나 SDK 구현 코드는 문서 작업에 필요한 경우가 아니면 변경하지 않는다.

저장소별 입력값:
- 저장소명: [repo-name]
- GitHub 저장소 URL: [https://github.com/org/repo-name]
- Pages 배포 URL: [https://org.github.io/repo-name/developer-guide/]
- VitePress title: [SDK 또는 샘플 이름]
- VitePress description: [개발자 가이드 설명]
- VitePress base: [/repo-name/developer-guide/]
- VitePress outDir: [.vitepress/dist/developer-guide]
- 배포 트리거 브랜치: [master 또는 main]
- 문서 package name: [repo-name 또는 repo-name-developer-guide]

추가할 파일:
- package.json
- package-lock.json
- docs/index.md
- docs/.vitepress/config.mts
- .github/workflows/deploy-docs.yml

필요하면 수정할 파일:
- README.md
- .gitignore

package.json 요구사항:
- private: true
- scripts:
  - docs:dev: vitepress dev docs
  - docs:build: vitepress build docs
  - docs:preview: vitepress preview docs
- devDependencies:
  - vitepress: ^1.6.4

GitHub Actions 요구사항:
- workflow 파일명은 .github/workflows/deploy-docs.yml
- on.push.branches는 [master 또는 main]
- workflow_dispatch를 포함한다.
- permissions는 contents: read, pages: write, id-token: write
- Node.js 20을 사용한다.
- actions/setup-node@v4에서 npm cache를 사용한다.
- npm ci로 설치한다.
- npm run docs:build로 빌드한다.
- actions/upload-pages-artifact@v3로 docs/.vitepress/dist를 업로드한다.
- actions/deploy-pages@v4로 배포한다.

.gitignore 요구사항:
- node_modules
- docs/.vitepress/cache
- docs/.vitepress/dist
- 내부 계획 문서를 docs/plans에 둘 경우 docs/plans도 제외한다.

검증:
- npm run docs:build를 실행해서 성공 여부를 확인한다.
- 가능하면 git diff --stat와 문서 관련 diff를 요약한다.
- 민감정보가 포함되지 않았는지 appkey, token, password, secret, keyPassword, storePassword 키워드로 검색한다.

주의:
- 기존 사용자 변경을 되돌리지 않는다.
- 샘플 앱 코드 변경은 요청 범위 밖이면 하지 않는다.
- base와 outDir은 Pages URL과 일치해야 한다.
- 문서 이미지가 필요하면 docs/public/images에 둔다.
```

## LLM이 수행할 구체 작업 순서

1. 저장소 상태 확인
   - `git status --short --branch`
   - `rg --hidden --files -g '!**/.git/**'`
   - 기존 `.github`, `docs`, `README.md`, `package.json`, `.gitignore` 존재 여부 확인

2. 기존 문서/빌드 설정 분석
   - 이미 VitePress, Docusaurus, MkDocs 등 문서 도구가 있는지 확인
   - 기존 Pages workflow가 있으면 덮어쓰기 전에 목적과 트리거를 파악
   - 저장소 기본 브랜치와 원격 URL 확인

3. 파일 추가/수정
   - `package.json`에 VitePress scripts와 dev dependency 추가
   - `npm install`로 `package-lock.json` 생성
   - `docs/index.md` 작성
   - `docs/.vitepress/config.mts` 작성
   - `.github/workflows/deploy-docs.yml` 작성
   - `.gitignore`에 Node/VitePress 산출물 추가
   - `README.md`에 배포 문서 링크와 실행 요약 추가

4. 로컬 검증
   - `npm run docs:build`
   - 실패 시 Markdown, VitePress config, 이미지 경로, Vue 보간식 충돌을 우선 확인

5. 결과 보고
   - 추가/수정 파일 목록
   - 배포 URL
   - 로컬 빌드 결과
   - 사람이 GitHub Settings에서 해야 하는 작업
   - 남은 리스크

## 저장소별 치환 예시

Android 현재 저장소:

```text
repo-name: inavi-navigation-demo-android
GitHub URL: https://github.com/inavi-systems/inavi-navigation-demo-android
Pages URL: https://inavi-systems.github.io/inavi-navigation-demo-android/android-developer-guide/
base: /inavi-navigation-demo-android/android-developer-guide/
outDir: .vitepress/dist/android-developer-guide
branch: master
title: iNavi Navigation SDK for Android
description: iNavi Android Navigation SDK Developer Guide
```

iOS 샘플 저장소 예시:

```text
repo-name: inavi-navigation-demo-ios
GitHub URL: https://github.com/inavi-systems/inavi-navigation-demo-ios
Pages URL: https://inavi-systems.github.io/inavi-navigation-demo-ios/ios-developer-guide/
base: /inavi-navigation-demo-ios/ios-developer-guide/
outDir: .vitepress/dist/ios-developer-guide
branch: master
title: iNavi Navigation SDK for iOS
description: iNavi iOS Navigation SDK Developer Guide
```

Flutter plugin 저장소 예시:

```text
repo-name: inavi-navisdk-flutter-plugin
GitHub URL: https://github.com/inavi-systems/inavi-navisdk-flutter-plugin
Pages URL: https://inavi-systems.github.io/inavi-navisdk-flutter-plugin/flutter-developer-guide/
base: /inavi-navisdk-flutter-plugin/flutter-developer-guide/
outDir: .vitepress/dist/flutter-developer-guide
branch: master
title: iNavi NaviSDK Flutter Plugin
description: iNavi NaviSDK Flutter Plugin Developer Guide
```

## VitePress 설정 작성 기준

기본 템플릿:

```ts
import { defineConfig } from 'vitepress'

export default defineConfig({
  title: '[TITLE]',
  description: '[DESCRIPTION]',
  base: '/[REPO]/[GUIDE_PATH]/',
  outDir: '.vitepress/dist/[GUIDE_PATH]',
  srcExclude: [
    'plans/**'
  ],
  themeConfig: {
    nav: [
      { text: 'Home', link: '/' }
    ],
    outline: {
      level: 2
    },
    socialLinks: [
      { icon: 'github', link: '[GITHUB_URL]' }
    ]
  }
})
```

`outDir` 선택 기준:

- 저장소 전체 Pages에 문서 사이트 하나만 배포하면 `.vitepress/dist`로 충분하다.
- 같은 저장소 Pages 아래에 여러 문서를 경로별로 둘 계획이면 `.vitepress/dist/[guide-path]`를 사용한다.
- workflow의 artifact path는 상위 폴더인 `docs/.vitepress/dist`를 유지하면 여러 guide path를 함께 담을 수 있다.

## GitHub Actions 템플릿

```yaml
name: Deploy VitePress site to Pages

on:
  push:
    branches: [master]
  workflow_dispatch:

permissions:
  contents: read
  pages: write
  id-token: write

concurrency:
  group: pages
  cancel-in-progress: false

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Setup Node.js
        uses: actions/setup-node@v4
        with:
          node-version: 20
          cache: npm

      - name: Install dependencies
        run: npm ci

      - name: Build with VitePress
        run: npm run docs:build

      - name: Upload artifact
        uses: actions/upload-pages-artifact@v3
        with:
          path: docs/.vitepress/dist

  deploy:
    environment:
      name: github-pages
      url: $&#123;&#123; steps.deployment.outputs.page_url &#125;&#125;
    needs: build
    runs-on: ubuntu-latest
    steps:
      - name: Deploy to GitHub Pages
        id: deployment
        uses: actions/deploy-pages@v4
```

`main` 브랜치를 쓰는 저장소는 `branches: [main]`으로 바꾼다.

## 검증 명령

로컬 문서 빌드:

```bash
npm run docs:build
```

문서 개발 서버:

```bash
npm run docs:dev
```

빌드 결과 미리보기:

```bash
npm run docs:preview
```

민감정보 검색:

```bash
rg -n -i "(appkey|token|password|secret|keyPassword|storePassword|authkey|api_key|apikey)"
```

문서 관련 변경 확인:

```bash
git diff --stat
git diff -- README.md package.json docs/.vitepress/config.mts .github/workflows/deploy-docs.yml
```

## 자주 발생하는 문제와 확인 지점

CSS/JS가 404로 깨지는 경우:

- `docs/.vitepress/config.mts`의 `base`가 실제 Pages URL 경로와 일치하는지 확인한다.
- `base`는 반드시 앞뒤 `/`를 포함한다.

빌드는 성공했지만 Pages에 빈 화면이 보이는 경우:

- artifact path가 `docs/.vitepress/dist`인지 확인한다.
- `outDir`을 하위 경로로 지정했다면 해당 하위 경로가 Pages URL과 맞는지 확인한다.

GitHub Actions에서 `npm ci`가 실패하는 경우:

- `package-lock.json`이 커밋되어 있는지 확인한다.
- `package.json`과 `package-lock.json`이 서로 맞는지 확인한다.
- 로컬에서 `npm install`을 다시 실행해 lockfile을 갱신한다.

VitePress 빌드 중 Markdown이 실패하는 경우:

- Markdown 코드블록 안의 GitHub Actions 표현식이 Vue 보간식으로 해석되는지 확인한다.
- 예시 YAML에 `$&#123;&#123; ... &#125;&#125;`가 들어가면 VitePress에서 Vue 보간식으로 해석되지 않도록 이스케이프한다.
- 내부 계획 문서가 빌드 대상에 들어가 문제를 만들면 `srcExclude`에 제외 패턴을 추가한다.

이미지가 깨지는 경우:

- VitePress 정적 이미지는 `docs/public/images`에 둔다.
- Markdown에서는 `/images/example.png`처럼 참조한다.
- GitHub README에서도 같은 이미지를 보여야 한다면 README용 상대 경로와 VitePress용 절대 경로를 구분해서 검토한다.

## 완료 기준

다른 저장소에 적용 완료로 판단하려면 아래 조건을 모두 만족해야 한다.

- `npm run docs:build`가 성공한다.
- `.github/workflows/deploy-docs.yml`이 기본 브랜치 push와 수동 실행을 지원한다.
- GitHub Settings에서 Pages Source가 GitHub Actions로 설정되어 있다.
- 기본 브랜치 머지 후 Actions 배포가 성공한다.
- README의 배포 문서 링크가 실제 Pages URL로 연결된다.
- VitePress 페이지에서 CSS, JS, 이미지가 정상 로드된다.
- 문서와 diff에 민감정보가 없다.
- 샘플 앱 코드에 불필요한 변경이 없다.
