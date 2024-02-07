import { Outlet } from 'react-router-dom';

export default function IndexLayout() {
  return (
    <main className="absolute flex items-end justify-between h-screen w-screen bg-main bg-cover bg-bottom mx-0 font-['DungGeunMo']">
      <Outlet />
    </main>
  );
}
