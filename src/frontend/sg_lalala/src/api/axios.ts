import axios, { AxiosInstance } from "axios";
// import { getNewRefreshToken } from "./refresh";

const baseURL = "http://localhost";

interface ServiceEndpoints {
  [key: string]: number;
}

const CreateServiceApi = (service: string): AxiosInstance => {
  const portMap: ServiceEndpoints = {
    user: 34000,
    chat: 18000,
    // stream:0,
  };

  const servicePort = portMap[service];
  const serviceEndpoint = `${baseURL}:${servicePort}`;
  const api = axios.create({
    baseURL: serviceEndpoint,
  });

  api.interceptors.request.use((config) => {
    const accessToken = localStorage.getItem("access");
    config.headers.Authorization = `${accessToken}`;
    return config;
  });

  return api;
};

interface ClientAPI {
  [key: string]: AxiosInstance;
}

const apiHook: ClientAPI = {
  user: CreateServiceApi("user"),
  chat: CreateServiceApi("chat"),
  // notification: CreateServiceApi("stream"),
};

export default apiHook;
