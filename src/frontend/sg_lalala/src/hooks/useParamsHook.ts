import { useEffect } from "react";
import { useParams } from "react-router-dom";

interface UseParamsHookOption<T extends Record<string, any>> {
  defaultParams?: T;
  validateParams: (value: T | undefined) => boolean;
  onInValidParams?: VoidFunction;
}

function isValidParams<T extends Record<string, any>>(
  value: T,
  validateParams: UseParamsHookOption<T>["validateParams"]
): value is T {
  return validateParams(value);
}

export const useParamsHook = <T extends Record<string, any>>(
  options: UseParamsHookOption<T>
) => {
  const params = useParams();

  useEffect(() => {
    // console.log(params, isValidParams(params as T, options.validateParams));
    if (
      !isValidParams(
        (params as T) ?? options.defaultParams,
        options.validateParams
      )
    ) {
      options.onInValidParams?.();
    }
  }, []);

  return params as T;
};
