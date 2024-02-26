import { useNavigate } from "react-router-dom";
import logo from "../assets/lalala_logo.png";
import LoginInput from "../components/Login/LoginInput";
import { useState } from "react";
import userApi from "../api/userApi";
import { useRecoilState } from "recoil";
import { userInfo } from "../state";

function LoginPage() {
  const navigate = useNavigate();
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [user, setUser] = useRecoilState(userInfo);

  const getUser = async () => {
    try {
      const response = await userApi.getUserInfo();
      setUser({
        id: response.data.data.id,
        nickName: response.data.data.nickname,
        profile: response.data.data.profile,
      });
    } catch (error) {
      console.error("로그인 에러", error);
    }
  };

  const postLogin = async () => {
    try {
      const response = await userApi.login({
        email: email,
        password: password,
      });
      console.log(response);
      if (response.status === 200) {
        const { access_token, refresh_token } = response.data.data;
        localStorage.setItem("access", access_token);
        localStorage.setItem("refresh", refresh_token);
        getUser();
        navigate("/");
      }
    } catch (error) {
      console.error("로그인 에러", error);
    }
  };

  return (
    <div className="flex flex-col items-center w-[400px]">
      <img src={logo} className="w-3/12 m-14" />
      <LoginInput
        type="text"
        placeholder="이메일"
        value={email}
        setValue={setEmail}
      />
      <LoginInput
        type="password"
        placeholder="비밀번호"
        value={password}
        setValue={setPassword}
      />
      <div
        className="flex justify-center w-full p-4 mt-5 text-black bg-primary/70 font-700"
        onClick={() => postLogin()}
      >
        라라라 아이디 로그인
      </div>
      <button
        className="mt-5 text-black text-md font-500"
        onClick={() => navigate("/signup")}
      >
        회원가입
      </button>
    </div>
  );
}

export default LoginPage;
