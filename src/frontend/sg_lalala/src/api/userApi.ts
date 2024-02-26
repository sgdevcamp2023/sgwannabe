import apiHook from "./axios";

const userApi = {
  login: async ({ email, password }: { email: string; password: string }) => {
    try {
      const response = await apiHook.user.post(`auth/signin`, {
        email,
        password,
      });
      return response;
    } catch (error) {
      console.log("login error", error);
      throw error;
    }
  },
  getUserInfo: async () => {
    try {
      const response = await apiHook.user.get(`user/users/info`);
      return response;
    } catch (error) {
      console.log("유저조회 error", error);
      throw error;
    }
  },
};

export default userApi;
