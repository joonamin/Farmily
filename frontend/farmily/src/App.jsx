import { useState, useEffect } from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import MainLayout from './pages/layout/Main.jsx';
import FamilyLayout from './pages/layout/Family.jsx';
import IndexLayout from './pages/layout/Index.jsx';

import MainPage from './pages/MainPage.jsx';
import RecordPage from './pages/RecordPage.jsx';
import RecordCreatePage from './pages/RecordCreatePage.jsx';
import EventCreatePage from './pages/EventCreatePage.jsx';
import DailyCreatePage from './pages/DailyCreatePage.jsx';
import ChallengeCreatePage from './pages/ChallengeCreatePage.jsx';
import EventDetailPage from './pages/EventDetailPage.jsx';
import DailyDetailPage from './pages/DailyDetailPage.jsx';
import ChallengeDetailPage from './pages/ChallengeDetailPage.jsx';
import MemoryPage from './pages/MemoryPage.jsx';
import CalendarPage from './pages/CalendarPage.jsx';
import CommunityPage from './pages/CommunityPage.jsx';
import CommunityWritePage from './pages/CommunityWritePage.jsx';
import AchievementPage from './pages/AchievementPage.jsx';
import ContactPage from './pages/ContactPage.jsx';
import SettingPage from './pages/SettingPage.jsx';
import IndexPage from './pages/IndexPage.jsx';
import LoginPage from './pages/LoginPage.jsx';
import CommunityDetailPage from './pages/CommunityDetailPage.jsx';
import TreeCreatePage from './pages/TreeCreatePage.jsx';
import EnterPage from './pages/EnterPage.jsx';
import FamilyCreatePage from './pages/FamilyCreatePage.jsx';
import WelcomePage from './pages/WelcomePage.jsx';
import VideoRoomWrapper from './components/contact/VideoRoomWrapper.jsx';
import TreeDecoratePage from './pages/TreeDecoratePage.jsx';
const router = createBrowserRouter([
  {
    path: '/',
    element: <IndexLayout />,
    children: [
      { path: '/', element: <IndexPage /> },
      { path: '/login', element: <LoginPage /> },
      { path: '/welcome', element: <WelcomePage /> },
      { path: 'createtree', element: <TreeCreatePage /> },
      { path: 'createtree/enter', element: <EnterPage /> },
      { path: 'createtree/family', element: <FamilyCreatePage /> },
    ],
  },
  {
    path: '/tree/:familyId',
    element: <MainLayout />,
    children: [
      { path: '', element: <MainPage /> },
      { path: 'decorate', element: <TreeDecoratePage /> },
    ],
  },
  {
    path: '/family',
    element: <FamilyLayout />,
    children: [
      { path: 'record/:sprintId', element: <RecordPage /> },
      { path: 'record/create', element: <RecordCreatePage /> },
      { path: 'record/create/event', element: <EventCreatePage /> },
      { path: 'record/create/daily', element: <DailyCreatePage /> },
      { path: 'record/create/challenge', element: <ChallengeCreatePage /> },
      { path: 'record/event/:recordId', element: <EventDetailPage /> },
      { path: 'record/daily/:recordId', element: <DailyDetailPage /> },
      {
        path: 'record/challenge/:recordId',
        element: <ChallengeDetailPage />,
      },
      { path: 'memory', element: <MemoryPage /> },
      { path: 'calendar', element: <CalendarPage /> },
      { path: 'community', element: <CommunityPage /> },
      { path: 'community/write', element: <CommunityWritePage /> },
      { path: 'community/:communityId', element: <CommunityDetailPage /> },
      { path: 'achievement', element: <AchievementPage /> },
      { path: 'contact', element: <ContactPage /> },
      { path: 'setting', element: <SettingPage /> },
    ],
  },
  {
    path: '/openvidutest/:familyId',
    element: <MainLayout />,
    children: [{ path: '', element: <VideoRoomWrapper /> }], // VideoRoomComponent 대신 VideoRoomWrapper 사용
  },
]);

export default function App() {
  return <RouterProvider router={router} />;
}
