"use client";
import {
  createContext,
  useCallback,
  useContext,
  useMemo,
  useState,
} from "react";
import type { GlobalLoadingContextValue } from "@/interface";

const GlobalLoadingContext = createContext<GlobalLoadingContextValue | null>(
  null
);

export function GlobalLoadingProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const [isLoading, setIsLoading] = useState(false);

  const start = useCallback(() => setIsLoading(true), []);
  const stop = useCallback(() => setIsLoading(false), []);
  const set = useCallback((v: boolean) => setIsLoading(v), []);

  const withLoading = useCallback(async <T,>(action: () => Promise<T>) => {
    setIsLoading(true);
    try {
      return await action();
    } finally {
      setIsLoading(false);
    }
  }, []);

  const value = useMemo<GlobalLoadingContextValue>(
    () => ({ isLoading, start, stop, set, withLoading }),
    [isLoading, start, stop, set, withLoading]
  );

  return (
    <GlobalLoadingContext.Provider value={value}>
      {children}
      {isLoading && <FullScreenOverlay />}
    </GlobalLoadingContext.Provider>
  );
}

export function useGlobalLoading() {
  const ctx = useContext(GlobalLoadingContext);
  if (!ctx)
    throw new Error(
      "useGlobalLoading must be used within GlobalLoadingProvider"
    );
  return ctx;
}

function FullScreenOverlay() {
  return (
    <div
      aria-live="polite"
      aria-busy="true"
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-sm"
    >
      <div className="flex flex-col items-center gap-3">
        <span className="h-10 w-10 animate-spin rounded-full border-4 border-white/60 border-t-transparent" />
        <p className="text-sm text-white/90">Loading</p>
      </div>
    </div>
  );
}
