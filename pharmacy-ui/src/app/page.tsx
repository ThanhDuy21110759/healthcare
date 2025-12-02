"use client";
import Image from "next/image";

export default function Home() {
  return (
    <main className="flex min-h-screen items-center justify-center p-8">
      <div className="max-w-5xl">
        <Image
          src="/homepage-banner.png"
          alt="Homepage banner"
          width={600}
          height={600}
          className="rounded-lg"
          priority
        />
      </div>
    </main>
  );
}
