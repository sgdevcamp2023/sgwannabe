import logo from "../../assets/lalala_logo.png";
import { IoSearch } from "react-icons/io5";
import { FaUserCircle } from "react-icons/fa";
import { colors } from "../../styles";

function Header() {
  return (
    <div className="flex flex-row items-center justify-between mt-5 mb-5">
      <div className="flex flex-row items-center">
        <img src={logo} className="w-20 mr-4" />
        <div className="flex flex-row items-center p-2 border-[3px] cursor-pointer border-primary rounded-3xl w-96">
          <input
            className="flex-1 ml-3 focus:outline-none"
            placeholder="태연의 맑고 힘 있는 보컬이 어우러지는 꿈"
          />
          <IoSearch color={colors.primary} size={20} />
        </div>
      </div>
      <div className="flex flex-row items-center cursor-pointer">
        <FaUserCircle size="30" color={colors.gray} />
        <div className="ml-3 text-textGray">로그인</div>
      </div>
    </div>
  );
}

export default Header;