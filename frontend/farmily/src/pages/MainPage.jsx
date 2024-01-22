import { useState, useEffect } from 'react'

import MainTree from '../components/MainTree'
import Challenge from '../components/Challenge'
import Board from '../components/Board'

export default function MainPage() {

  return (
    <main className="absolute flex flex-row-reverse items-end justify-start h-screen w-screen bg-main bg-cover bg-center">
      <Board />
      <MainTree />
      <Challenge />
    </main>
  )
}