export type GlobalLoadingContextValue = {
  isLoading: boolean;
  start: () => void;
  stop: () => void;
  set: (value: boolean) => void;
  withLoading: <T>(action: () => Promise<T>) => Promise<T>;
};
