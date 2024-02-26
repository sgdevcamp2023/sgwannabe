import { setState } from "../../@types/react";

interface loginInputprops {
  type: string;
  placeholder: string;
  value: string;
  setValue: setState<string>;
}

function LoginInput({ type, placeholder, value, setValue }: loginInputprops) {
  return (
    <div className={`w-[400px] p-4 flex justify-center border border-gray`}>
      <input
        type={type}
        placeholder={placeholder}
        className="flex-1 focus:outline-none"
        value={value}
        onChange={(e) => setValue(e.target.value)}
      />
    </div>
  );
}

export default LoginInput;
