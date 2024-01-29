import { useState, useEffect } from 'react';

import mainboard from '../../assets/images/mainboard.png';

export default function Board() {
  return (
    <>
      <img className="relative mb-16 mr-28" src={mainboard} alt="main_board" />
    </>
  );
}
