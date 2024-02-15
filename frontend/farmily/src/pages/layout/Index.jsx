import { Outlet } from 'react-router-dom';

// bg-main animate-bgScroll bg-cover bg-bottom
export default function IndexLayout() {
  return (
    <main className="relative flex items-end justify-between bg-main animate-bgScroll bg-cover bg-repeat-x bg-bottom h-screen w-screen mx-0 font-['DungGeunMo']">
      <Outlet />
      <div className="absolute bottom-0 bg-mainBottom w-full h-28 bg-cover z-10"></div>
    </main>
  );
}
