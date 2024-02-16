import axios, { AxiosInstance } from "axios";

const baseURL = "http://localhost";

interface ServiceEndpoints {
  [key: string]: number;
}

const CreateServiceApi = (service: string): AxiosInstance => {
  const portMap: ServiceEndpoints = {
    login: 34000,
    chat: 18000,
    // stream:0,
  };

  const servicePort = portMap[service];
  const serviceEndpoint = `${baseURL}:${servicePort}`;
  const api = axios.create({
    baseURL: serviceEndpoint,
  });

  // Interceptor를 사용하여 accessToken을 요청에 추가
  // const { authToken } = useAuthStore();
  // const accessToken = localStorage.getItem('accessToken');

  api.interceptors.request.use((config) => {
    config.headers.Authorization = "Bearer accessToken";
    return config;
  });
  return api;
};

interface ClientAPI {
  [key: string]: AxiosInstance;
}

const apiHook: ClientAPI = {
  login: CreateServiceApi("login"),
  chat: CreateServiceApi("chat"),
  // notification: CreateServiceApi("stream"),
};

export default apiHook;
