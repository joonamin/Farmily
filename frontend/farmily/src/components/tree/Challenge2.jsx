// Challenge1.jsx, Challenge2.jsx, Challenge3.jsx

import React, { useState } from 'react';
import ChallengeModal from './ChallengeModal';
import challengeImage from '../../assets/images/challenge_flower2.png';

export default function Challenge({ data, handleChange }) {
  const [isModalOpened, setIsModalOpened] = useState(false);

  const openModal = () => {
    setIsModalOpened(true);
  };

  const closeModal = () => {
    setIsModalOpened(false);
  };

  // 데이터 유무에 따라 visibility 스타일 결정
  const visibilityStyle = data ? {} : { visibility: 'hidden' };

  return (
    <div className="relative" style={visibilityStyle}>
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
