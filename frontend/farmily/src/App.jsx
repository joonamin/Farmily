import { useState, useEffect } from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import MainLayout from './pages/Main.jsx';
import FamilyLayout from './pages/Family.jsx';
import IndexLayout from './pages/Index.jsx';

import MainPage from './pages/MainPage.jsx';
import RecordPage from './pages/RecordPage.jsx';
import MemoryPage from './pages/MemoryPage.jsx';
import CalendarPage from './pages/CalendarPage.jsx';
import CommunityPage from './pages/CommunityPage.jsx';
import AchievementPage from './pages/AchievementPage.jsx';
import ContactPage from './pages/ContactPage.jsx';
import IndexPage from './pages/IndexPage.jsx';
import LoginPage from './pages/LoginPage.jsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <IndexLayout />,
    children: [
      { path: '/', element: <IndexPage /> },
      { path: '/login', element: <LoginPage /> },
    ],
  },
  {
    path: '/tree',
    element: <MainLayout />,
    children: [{ path: '', element: <MainPage /> }],
  },
  {
    path: '/family',
    element: <FamilyLayout />,
    children: [
      { path: 'record', element: <RecordPage />},
      { path: 'memory', element: <MemoryPage />},
      { path: 'calendar', element: <CalendarPage />},
      { path: 'community', element: <CommunityPage />},
      { path: 'achievement', element: <AchievementPage />},
      { path: 'contact', element: <ContactPage />},
    ]
  },
]);

export default function App() {
  return <RouterProvider router={router} />;
}