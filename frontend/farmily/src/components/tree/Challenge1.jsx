// Challenge.jsx

import React, { useState } from 'react';
import ChallengeModal from './ChallengeModal';
import challengeImage from '../../assets/images/challenge_flower1.png';

export default function Challenge({ data, handleChange }) {
  const [isModalOpened, setIsModalOpened] = useState(false);

  const openModal = () => setIsModalOpened(true);
  const closeModal = () => setIsModalOpened(false);

  return (
    <div className={`relative ${data ? '' : 'hidden'}`}>
      <img
        className="mb-20 cursor-pointer w-28 h-28 ml-5"
        src={challengeImage}
        alt="challenge_flower"
        onClick={openModal}
      />
      {isModalOpened && (
        <ChallengeModal challengeData={data} isOpen={isModalOpened} onClose={closeModal} handleChange={handleChange} />
      )}
    </div>
  );
}
