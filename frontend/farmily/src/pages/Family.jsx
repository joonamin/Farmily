import { Outlet } from 'react-router-dom';
import SideBar from '../components/SideBar.jsx';

export default function MainLayout() {
  return (
    <main className="absolute flex items-end justify-between h-screen w-screen bg-mainCover bg-cover bg-bottom mx-0 font-['DungGeunMo']">
      <SideBar />
      <div className="h-screen text-center align-middle w-full-side py-16 px-24">
        <div className="border-8 border-black bg-white h-full rounded-xl">
          <Outlet />
        </div>
      </div>
    </main>
  );
}
