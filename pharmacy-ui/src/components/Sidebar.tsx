"use client";
import Image from "next/image";
import { useState } from "react";
import Link from "next/link";
import { MdViewList, MdWarehouse, MdSell, MdPerson } from "react-icons/md";

type MenuSection = {
  title: string;
  items: { label: string; href?: string }[];
};

const sections: MenuSection[] = [
  {
    title: "Danh mục",
    items: [{ label: "Quản lý danh mục hàng hóa", href: "/product" }],
  },
  {
    title: "Quản lý kho",
    items: [
      { label: "Nhập kho hàng hóa", href: "/nhap-kho" },
      { label: "Nhập hàng trả lại", href: "/nhap-tra-lai" },
      { label: "Kiểm kê hàng hóa", href: "/kiem-ke" },
    ],
  },
  {
    title: "Xuất kho/bán hàng",
    items: [{ label: "Kê đơn bán thuốc", href: "/ke-don" }],
  },
];

export function Sidebar() {
  const [open, setOpen] = useState<Record<string, boolean>>({});

  const toggle = (title: string) =>
    setOpen((prev) => ({ ...prev, [title]: !prev[title] }));

  return (
    <aside className="flex h-screen w-72 flex-col border-r border-zinc-200 bg-[--color-surface]">
      <div className="flex items-center gap-3 px-4 py-4">
        <Link
          href="/"
          aria-label="Go to home"
          className="inline-flex items-center"
        >
          <Image
            src="/logo-navbar.png"
            alt="Pharmacy logo"
            width={40}
            height={28}
            priority
          />
        </Link>
        <Link href="/" className="text-lg tracking-wide hover:opacity-90">
          Pharmacy
        </Link>
      </div>
      <nav className="flex-1 overflow-y-auto px-2 py-2">
        {sections.map((section) => (
          <div key={section.title} className="mb-2">
            <button
              className="flex w-full items-center justify-between rounded-md px-3 py-2 text-left text-sm  hover:bg-[--color-surface-muted]"
              onClick={() => toggle(section.title)}
            >
              <span className="flex items-center gap-2">
                {section.title === "Danh mục" && (
                  <MdViewList size={16} aria-hidden="true" />
                )}
                {section.title === "Quản lý kho" && (
                  <MdWarehouse size={16} aria-hidden="true" />
                )}
                {section.title === "Xuất kho/bán hàng" && (
                  <MdSell size={16} aria-hidden="true" />
                )}
                <span className="tracking-wide">{section.title}</span>
              </span>
              <span className="text-xs">{open[section.title] ? "−" : "+"}</span>
            </button>
            {open[section.title] && (
              <ul className="mt-1 space-y-1">
                {section.items.map((item) => (
                  <li key={item.label}>
                    {item.href ? (
                      <Link
                        href={item.href}
                        className="block rounded-md px-4 py-2 text-sm hover:bg-[--color-surface-muted]"
                      >
                        {item.label}
                      </Link>
                    ) : (
                      <span className="block rounded-md px-4 py-2 text-sm">
                        {item.label}
                      </span>
                    )}
                  </li>
                ))}
              </ul>
            )}
          </div>
        ))}
      </nav>
      <div className="border-t border-zinc-200 px-2 py-3">
        <details className="rounded-md px-3 py-2">
          <summary className="cursor-pointer text-sm flex items-center gap-2">
            <MdPerson size={16} aria-hidden="true" />
            <span className="tracking-wide">Tài khoản</span>
          </summary>
          <ul className="mt-2">
            <li>
              <Link
                href="/profile"
                className="block rounded-md px-3 py-2 text-sm hover:bg-[--color-surface-muted]"
              >
                Hồ sơ cá nhân
              </Link>
            </li>
            <li>
              <button className="w-full rounded-md px-3 py-2 text-left text-sm hover:bg-[--color-surface-muted]">
                Đăng xuất
              </button>
            </li>
          </ul>
        </details>
      </div>
    </aside>
  );
}
