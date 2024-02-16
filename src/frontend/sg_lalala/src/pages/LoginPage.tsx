import { useNavigate } from "react-router-dom";
import logo from "../assets/lalala_logo.png";
import LoginButton from "../components/Login/LoginButton";
import LoginInput from "../components/Login/LoginInput";

function LoginPage() {
  const navigate = useNavigate();
  return (
    <div className="flex flex-col items-center w-[400px]">
      <img src={logo} className="w-3/12 m-14" />
      <LoginInput type="text" placeholder="아이디" value={""} />
      <LoginInput type="password" placeholder="비밀번호" value={""} />
      <LoginButton bgColor="bg-primary/70" text="라라라 아이디 로그인" />
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
