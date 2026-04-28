import { defineConfig } from 'vitepress'

export default defineConfig({
  title: 'iNavi Navigation SDK for Android',
  description: 'iNavi Android Navigation SDK Developer Guide',
  base: '/inavi-navigation-demo-android/',
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
