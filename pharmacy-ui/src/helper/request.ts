import { AxiosRequestConfig } from "axios";
import { httpClient } from "./httpClient";

// Lightweight typed wrappers that return response data (not AxiosResponse)
export async function getJSON<T>(
  url: string,
  config?: AxiosRequestConfig
): Promise<T> {
  const res = await httpClient.get<T>(url, config);
  return res.data as T;
}

export async function postJSON<T, B = unknown>(
  url: string,
  body?: B,
  config?: AxiosRequestConfig
): Promise<T> {
  const res = await httpClient.post<T>(url, body, config);
  return res.data as T;
}

export async function putJSON<T, B = unknown>(
  url: string,
  body?: B,
  config?: AxiosRequestConfig
): Promise<T> {
  const res = await httpClient.put<T>(url, body, config);
  return res.data as T;
}

export async function deleteJSON<T>(
  url: string,
  config?: AxiosRequestConfig
): Promise<T> {
  const res = await httpClient.delete<T>(url, config);
  return res.data as T;
}
