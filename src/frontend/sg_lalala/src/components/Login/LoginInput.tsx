interface loginInputprops {
  type: string;
  placeholder: string;
  value: string;
}

function LoginInput(props: loginInputprops) {
  return (
    <div className={`w-[400px] p-4 flex justify-center border border-gray`}>
      <input
        type={props.type}
        placeholder={props.placeholder}
        className="flex-1 focus:outline-none"
      />
    </div>
  );
}

export default LoginInput;
