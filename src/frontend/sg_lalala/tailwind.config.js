/** @type {import('tailwindcss').Config} */

module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    colors: {
      white: "#FFFFFF",
      primary: "#00CD3C",
      primaryLight: "#1EE13C",
      primaryDark: "#01B925",
      up: "#F86A0E",
      down: "#86BCFE",
      kakao: "#FAE100",
      gray: "#C2CAD4",
      black: "#2F2F37",
      whiteDark: "#F8F8F8",
      textBlack: "#1E1E1E",
      textGray: "#AAAAAA",
    },
    fontSize: {
      xxl: "1.875rem",
      xl: "1.625rem",
      lg: "1.125rem",
      md: "1.0625rem",
      sm: "1rem",
      xs: ".9375rem",
      xxs: ".875rem",
    },
    fontWeight: {
      700: "700",
      600: "600",
      500: "500",
      400: "400",
      300: "300",
    },
  },
  plugins: [],
};
