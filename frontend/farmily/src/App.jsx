import { useState, useEffect } from 'react'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import MainPage from './pages/MainPage.jsx'
import RecordPage from './pages/RecordPage.jsx'
import MemoryPage from './pages/MemoryPage.jsx'
import CalendarPage from './pages/CalendarPage.jsx'
import CommunityPage from './pages/CommunityPage.jsx'
import AchievementPage from './pages/AchievementPage.jsx'
import ContactPage from './pages/ContactPage.jsx'
import MainLayout from './pages/Main.jsx'
import IndexLayout from './pages/Index.jsx'
import IndexPage from './pages/IndexPage.jsx'
import LoginPage from './pages/LoginPage.jsx'

const router = createBrowserRouter([
  { 
    path: '/',
    element: <IndexLayout />, 
    children: [
      { path: '/', element: <IndexPage />},
      { path: '/login', element: <LoginPage />}
    ]
  },
  { 
    path: '/tree',
    element: <MainLayout />, 
    children: [
      { path: '', element: <MainPage />},
      { path: '/tree/record', element: <RecordPage />},
      { path: '/tree/memory', element: <MemoryPage />},
      { path: '/tree/calendar', element: <CalendarPage />},
      { path: '/tree/community', element: <CommunityPage />},
      { path: '/tree/achievement', element: <AchievementPage />},
      { path: '/tree/contact', element: <ContactPage />},
    ]
  },

]);
export default function App() {
  return <RouterProvider router={router} />;
}