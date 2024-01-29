import { useState, useEffect } from 'react';

import MainTree from '../components/tree/MainTree.jsx';
import Challenge from '../components/tree/Challenge.jsx';
import Board from '../components/tree/Board.jsx';

export default function MainPage() {
  return (
    <>
      <Challenge />
      <MainTree />
      <Board />
    </>
  );
}
