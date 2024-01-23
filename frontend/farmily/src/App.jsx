import { useState, useEffect } from 'react'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import MainPage from './pages/MainPage'


const router = createBrowserRouter([
  { path: '/', element: <MainPage />},

]);
export default function App() {
  return <RouterProvider router={router} />;
}