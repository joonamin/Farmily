/** @type {import('tailwindcss').Config} */

export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    backgroundImage: {
      main: "url('./assets/images/bg.png')",
      mainCover: "url('./assets/images/backgroundcover.png')",
      mainBottom: "url('./assets/images/bgBottom.png')",
    },
    extend: {
      animation: {
        bgScroll: 'bgScroll 150s linear infinite',
      },
      keyframes: {
        bgScroll: {
          '0%': { backgroundPosition: '0% 0' },
          '100%': { backgroundPosition: '100% 0' },
        },
      },
    },
  },
  // eslint-disable-next-line no-undef
  plugins: [
    function ({ addUtilities }) {
      const newUtilities = {
        '.motto-div': {
          left: 'calc(50% - 2rem)',
        },
      };
      addUtilities(newUtilities, ['responsive', 'hover']);
    },
    require('tailwindcss-animated'),
  ],
};
