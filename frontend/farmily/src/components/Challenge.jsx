import React, { useState } from "react";
import ChallengeModal from "./ChallengeModal";
import challenge from "../assets/images/challenge_flower.png";

export default function Challenge() {
  const [isModalOpened, setIsModalOpened] = useState(false);

  const openModal = () => {
    setIsModalOpened(true);
  };

  const closeModal = () => {
    setIsModalOpened(false);
  };

  return (
    <div className="relative">
      {/* 부모 컨테이너에 relative 적용 */}
      <img
        className="mb-20 cursor-pointer"
        src={challenge}
        alt="challenge_flower"
        onClick={openModal}
      />

      {/* 모달을 최상단에 두기 위한 createPortal 사용 */}
      {isModalOpened && (
        <ChallengeModal isOpen={isModalOpened} onClose={closeModal} />
      )}
    </div>
  );
}
