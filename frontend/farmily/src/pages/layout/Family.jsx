import { Outlet } from 'react-router-dom';
import SideBar from '../../components/SideBar.jsx';

import BackButton from '../../components/button/BackButton.jsx';
export default function MainLayout() {
  return (
    <main className="absolute flex items-end justify-between h-screen w-screen bg-mainCover bg-cover bg-bottom mx-0 font-['DungGeunMo']">
      <SideBar />
      <div className="h-screen text-center align-middle w-full-side pt-4 pb-16 px-8">
        <BackButton />
        <div className="border-8 border-black bg-white h-full rounded-xl mx-8">
          <Outlet />
        </div>
      </div>
    </main>
  );
}
