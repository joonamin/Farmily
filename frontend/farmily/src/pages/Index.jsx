import { Outlet } from 'react-router-dom';

export default function IndexLayout() {
  return (
    <main className="absolute flex h-screen w-screen bg-main bg-cover bg-center mx-0">
      <Outlet />
    </main>
  )
}