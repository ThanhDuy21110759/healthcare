import Link from "next/link";

export default function NotFound() {
  return (
    <main className="flex min-h-screen items-center justify-center bg-[--color-surface] px-6">
      <div className="max-w-lg text-center">
        <p className="text-5xl font-semibold text-[--color-brand]">404</p>
        <h1 className="mt-2 text-2xl font-bold tracking-tight text-[--color-foreground]">
          Page not found
        </h1>
        <p className="mt-2 text-sm text-[--color-muted-foreground]">
          Sorry, the page you are looking for doesn’t exist or was moved.
        </p>
        <div className="mt-6 flex items-center justify-center gap-3">
          <Link
            href="/"
            className="inline-flex items-center rounded-md border border-[--color-border] bg-[--color-surface] px-4 py-2 text-sm font-medium text-[--color-foreground] hover:bg-[--color-muted]"
          >
            Back to home
          </Link>
        </div>
      </div>
    </main>
  );
}
