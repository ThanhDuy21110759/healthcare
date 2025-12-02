"use client";
import { ButtonHTMLAttributes } from "react";

type Variant = "primary" | "secondary" | "ghost";

export type CustomButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  variant?: Variant;
  loading?: boolean;
};

const base =
  "inline-flex items-center justify-center rounded-md px-4 py-2 text-sm font-medium transition-colors focus:outline-none focus:ring-2 focus:ring-offset-2 disabled:opacity-60 disabled:cursor-not-allowed";

const variants: Record<Variant, string> = {
  primary:
    "bg-black text-white hover:bg-zinc-800 focus:ring-zinc-700 dark:bg-white dark:text-black dark:hover:bg-zinc-200",
  secondary:
    "bg-zinc-100 text-zinc-900 hover:bg-zinc-200 focus:ring-zinc-300 dark:bg-zinc-800 dark:text-zinc-100 dark:hover:bg-zinc-700",
  ghost:
    "bg-transparent text-zinc-900 hover:bg-zinc-100 focus:ring-zinc-300 dark:text-zinc-100 dark:hover:bg-zinc-800",
};

export function CustomButton({
  variant = "primary",
  loading = false,
  children,
  className = "",
  ...props
}: CustomButtonProps) {
  return (
    <button className={`${base} ${variants[variant]} ${className}`} {...props}>
      {loading ? "Loading..." : children}
    </button>
  );
}
