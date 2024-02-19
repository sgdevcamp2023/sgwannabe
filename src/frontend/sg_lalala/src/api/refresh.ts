import axios from "axios";

export const getNewRefreshToken = async () => {
  const accessToken = localStorage.getItem("access");
  const refreshToken = localStorage.getItem("refesh");
  const result = await axios.post(
    "auth/token-refresh",
    { refreshToken },
    {
      headers: {
        Authorization: accessToken,
      },
    }
  );
  return result.data;
};
