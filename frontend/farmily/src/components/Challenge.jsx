import { useState, useEffect } from 'react'

import challenge from '../assets/images/challenge_flower.png'

export default function Challenge() {

  return (
    <>
      <img className="relative mb-16"
        src={challenge} alt="challenge_flower"
      />
    </>
  )
}