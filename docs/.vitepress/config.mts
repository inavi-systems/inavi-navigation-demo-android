import { defineConfig } from 'vitepress'

export default defineConfig({
  title: 'iNavi Navigation SDK for Android',
  description: 'iNavi Android Navigation SDK Developer Guide',
  base: '/inavi-navigation-demo-android/',
  srcExclude: [
    'sample-modernization-plan.md',
    'vitepress-settings-guide.md',
    'readme-vitepress-pages-plan.md'
  ],
  themeConfig: {
    nav: [
      { text: 'Home', link: '/' }
    ],
    outline: {
      level: [2, 3]
    },
    socialLinks: [
      { icon: 'github', link: 'https://github.com/inavi-systems/inavi-navigation-demo-android' }
    ]
  }
})
