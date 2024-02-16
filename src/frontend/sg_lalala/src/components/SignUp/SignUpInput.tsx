interface signupTypes {
  value: string;
}

function SignUpInput(props: signupTypes) {
  return (
    <div className="w-[575px] text-lg my-3 text-black font-500">
      <div className="p-3 bg-white border-2 border-gray rounded-2xl">
        <input
          className="w-full bordr focus:outline-none"
          placeholder={props.value}
        />
      </div>
    </div>
  );
}

export default SignUpInput;
