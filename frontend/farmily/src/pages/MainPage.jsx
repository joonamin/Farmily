import { useState, useEffect } from 'react';

import MainTree from '../components/MainTree.jsx'
import Challenge from '../components/Challenge.jsx'
import Board from '../components/Board.jsx'


export default function MainPage() {
  return (
    <>
      <Challenge />
      <MainTree />
      <Board />
    </>
  )
}