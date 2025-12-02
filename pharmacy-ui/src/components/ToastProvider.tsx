"use client";
import { Toaster } from "react-hot-toast";

export function ToastProvider() {
  return (
    <Toaster
      position="top-right"
      toastOptions={{
        duration: 4000,
        style: {
          background: "#111",
          color: "#fff",
        },
        success: {
          style: { background: "#0a7", color: "#fff" },
        },
        error: {
          style: { background: "#b00020", color: "#fff" },
        },
      }}
    />
  );
}
