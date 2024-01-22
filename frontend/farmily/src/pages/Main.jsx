import SideBar from '../components/SideBar.jsx'
import { Outlet } from 'react-router-dom';

export default function MainLayout() {
  return (
    <main className="absolute flex items-end justify-between h-screen w-screen bg-main bg-cover bg-center mx-0">
      <SideBar />
      <Outlet />
    </main>
  );
}