/** @type {import('tailwindcss').Config} */

export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    backgroundImage: {
      main: "url('./assets/images/background.png')",
      mainCover: "url('./assets/images/backgroundcover.png')",
    },
  },
  // eslint-disable-next-line no-undef
  plugins: [require('tailwindcss-animated')],
};
