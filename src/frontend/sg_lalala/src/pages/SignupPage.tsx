import SignUpInput from "../components/SignUp/SignUpInput";
import logo from "../assets/lalala_logo.png";
import LoginInput from "../components/Login/LoginInput";
import { useState } from "react";
import Timer from "../utils/Timer";

// function SignupPage() {
//   return (
//     <div className="flex flex-col items-center w-full mt-10">
//       <div className="w-[698px] rounded-3xl bg-whiteDark py-[67.5px] items-center flex flex-col">
//         <div className="text-xl text-black font-700">회원가입</div>
//         <SignUpInput value="nickname" />
//         <SignUpInput value="email" />
//         <Timer />
//         <SignUpInput value="password" />
//         <SignUpInput value="password" />
//         <button className="w-[391px] text-whiteDark text-xl rounded-2xl p-3 bg-primaryDark/50 font-800">
//           회원가입
//         </button>
//       </div>
//     </div>
//   );
// }

function SignupPage() {
  const [isEmailClicked, setIsEmailClicked] = useState<boolean>(false);

  return (
    <div className="flex flex-col items-center w-[400px]">
      <img src={logo} className="w-3/12 m-14" />
      {/* <LoginInput type="text" placeholder="아이디" value={""} />
      <LoginInput type="password" placeholder="이메일" value={""} /> */}
      <button
        onClick={() => setIsEmailClicked(true)}
        className="p-1 my-1 text-black border-2 rounded-full border-primary"
      >
        이메일 인증
      </button>
      {isEmailClicked && (
        <div className="flex flex-col items-center mb-2">
          <div className="flex flex-row justify-evenly">
            <input
              className="p-2 mr-2 border rounded-full border-gray"
              placeholder="인증번호"
            />
            <button className="p-1 my-1 text-black border-2 rounded-full border-primary">
              인증번호 확인
            </button>
          </div>
          <Timer />
        </div>
      )}
      {/* <LoginInput type="password" placeholder="비밀번호" value={""} />
      <LoginInput type="password" placeholder="비밀번호 확인" value={""} /> */}
      <button className="w-full p-3 mt-5 rounded-full bg-primary/50">
        회원가입
      </button>
    </div>
  );
}

export default SignupPage;
