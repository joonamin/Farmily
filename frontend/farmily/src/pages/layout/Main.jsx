import SideBar from '../../components/SideBar.jsx';
import { Outlet } from 'react-router-dom';

export default function MainLayout() {
  return (
    <main className="relative flex items-end justify-between bg-main animate-bgScroll bg-cover bg-repeat-x bg-bottom h-screen w-screen mx-0 font-['DungGeunMo']">
      <SideBar />
      <div className="w-full-side flex z-20">
        <Outlet />
      </div>
      <div className="absolute bottom-0 bg-mainBottom w-full h-28 bg-cover z-10"></div>
    </main>
  );
}
