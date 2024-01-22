import { useState, useEffect } from 'react'

import MainTree from '../components/MainTree.jsx'
import Challenge from '../components/Challenge.jsx'
import Board from '../components/Board.jsx'
import SideBar from '../components/SideBar.jsx'

export default function MainPage() {

  return (
    <main className="absolute flex  items-end justify-between h-screen w-screen bg-main bg-cover bg-center mx-0">
      <SideBar />
      <Challenge />
      <MainTree />
      <Board />
    </main>
  )
}