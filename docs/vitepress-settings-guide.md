🚀 VitePress 기반 SDK 문서 사이트 구축 가이드
이 가이드는 VitePress를 사용하여 마크다운 문서를 고성능 웹사이트로 배포하고, GitHub Pages를 통해 자동 퍼블리싱하는 전 과정을 담고 있습니다.

1. 프로젝트 초기화 및 설치
먼저 문서용 레포지토리를 생성하거나 기존 프로젝트 폴더에서 시작합니다.

Bash
# 1. 폴더 생성 및 이동
:TODO 현재폴더 경로 삽입

# 2. VitePress 초기화 (질문에 따라 설정 선택)
# Root: ./docs 추천
npx vitepress init

# 3. 의존성 패키지 설치
npm install
2. VitePress 환경 설정 (docs/.vitepress/config.mts)
가장 중요한 설정 파일입니다. 특히 GitHub Pages 배포 시 base 경로 설정이 핵심입니다.

TypeScript
import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  title: "iNaviSDK",
  description: "iNavi Android Navigation SDK Developer Guide",
  base: '/navisdk-docs/',
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: '/' },
    ],
  }
})


3. GitHub Actions 자동 배포 설정 (.github/workflows/deploy.yml)
로컬에서 Push하면 자동으로 빌드 및 배포되는 CI/CD 스크립트입니다.

YAML
name: Deploy VitePress site to Pages

on:
  push:
    branches: [main] # 배포를 트리거할 브랜치

permissions:
  contents: read
  pages: write
  id-token: write

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4
        
      - name: Install Node.js
        uses: actions/setup-node@v4
        with:
          node-version: 20
          cache: 'npm'
          
      - name: Install dependencies
        run: npm install
        
      - name: Build with VitePress
        run: npm run docs:build
        
      - name: Upload artifact
        uses: actions/upload-pages-artifact@v3
        with:
          path: docs/.vitepress/dist # 빌드 결과물 경로

  deploy:
    environment:
      name: github-pages
      url: ${{ steps.deployment.outputs.page_url }}
    needs: build
    runs-on: ubuntu-latest
    steps:
      - name: Deploy to GitHub Pages
        id: deployment
        uses: actions/deploy-pages@v4
        
4. 로컬 실행 및 빌드 명령어
Bash
# 로컬 개발 서버 실행 (실시간 수정 확인)
npm run docs:dev

# 정적 사이트 빌드 (배포 전 테스트)
npm run docs:build

# 빌드된 결과물 미리보기
npm run docs:preview
5. 주요 팁 및 트러블슈팅
① PDF 추출 최적화 (CSS)
비개발자가 브라우저에서 Ctrl + P로 PDF를 뽑을 때 메뉴를 숨기려면 docs/.vitepress/theme/custom.css에 추가하세요.

CSS
@media print {
  .VPNav, .VPSidebar, .VPDocFooter, .VPLocalNav {
    display: none !important;
  }
  .VPContent {
    padding: 0 !important;
    width: 100% !important;
  }
}
② GitHub Pages 설정 변경
GitHub 레포지토리 Settings > Pages 이동

Build and deployment > Source를 GitHub Actions로 변경

③ 이미지 경로 주의
이미지는 docs/public/images/ 폴더에 넣고, 마크다운에서는 /images/example.png와 같이 절대 경로로 참조합니다.