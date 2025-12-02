"use client";
import axios, { AxiosError, AxiosResponse } from "axios";
import toast from "react-hot-toast";
import type { ApiErrorBody } from "@/interface";

const HOST_API = process.env.NEXT_PUBLIC_HOST_API || "";
const API_KEY_SUPABASE = process.env.NEXT_PUBLIC_API_KEY_SUPABASE || "";

export const httpClient = axios.create({
  baseURL: HOST_API || undefined,
  timeout: 30000,
  headers: {
    "Content-Type": "application/json",
    "Accept-Language": "en-US",
    ...(API_KEY_SUPABASE ? { apiKey: API_KEY_SUPABASE } : {}),
  },
});

httpClient.interceptors.response.use(
  (response: AxiosResponse<unknown>) => {
    return response;
  },
  (error: AxiosError<ApiErrorBody>) => {
    let errorMessage = "Uncategorized error occurred.";

    if (error.response) {
      const { status, data } = error.response;
      const errMsg =
        (typeof data?.message === "string" ? data.message : undefined) ??
        (typeof data?.msg === "string" ? data.msg : undefined) ??
        "";
      switch (status) {
        case 400:
          errorMessage = errMsg || "Bad request. Please check your input.";
          break;
        case 401:
          errorMessage =
            errMsg || "You are not authorized to access this resource.";
          break;
        case 403:
          errorMessage =
            errMsg || "You are forbidden from accessing this resource.";
          break;
        case 404:
          errorMessage =
            errMsg || "The resource you are looking for is not found.";
          break;
        case 500:
          errorMessage =
            errMsg || "An error occurred while processing your request.";
          break;
        default:
          errorMessage = errMsg || errorMessage;
      }
    } else if (error.request) {
      errorMessage =
        "No response received from the server. Please check your network connection.";
    }

    if (typeof window !== "undefined") {
      toast.error(errorMessage);
    }
    return Promise.reject(error);
  }
);

export type HttpClient = typeof httpClient;
