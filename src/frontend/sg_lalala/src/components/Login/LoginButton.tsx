interface loginButtonprops {
  bgColor: string;
  text: string;
}

function LoginButton(props: loginButtonprops) {
  return (
    <button className={`w-full p-4 ${props.bgColor} flex justify-center mt-5`}>
      <div className="text-black font-700">{props.text}</div>
    </button>
  );
}

export default LoginButton;
