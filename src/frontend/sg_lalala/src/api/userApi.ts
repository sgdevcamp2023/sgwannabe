import apiHook from "./axios";

const headers = {
  Authorization: "Bearer accessToken",
};

const userApi = {
  login: async ({ email, password }: { email: string; password: string }) => {
    try {
      const response = await apiHook.login.post(
        `auth/signin`,
        { email, password },
        {
          headers: headers,
        }
      );
      console.log(response);
    } catch (error) {
      console.log("login error", error);
      throw error;
    }
  },
};

export default userApi;
