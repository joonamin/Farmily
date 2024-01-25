import React, { useState, useRef, useEffect } from "react";
import { createPortal } from "react-dom"; // createPortal을 import합니다.
import ChallengeModal from "./ChallengeModal"; // 모달 컴포넌트 import
import challenge from "../assets/images/challenge_flower.png";

export default function Challenge() {
  const [isModalOpened, setIsModalOpened] = useState(false);

  const modalRef = useRef(null);

  const closeModal = () => {
    // 모달을 닫는 함수
    setIsModalOpened(false);
  };

  useEffect(() => {
    const handleOutsideClick = (e) => {
      if (
        isModalOpened &&
        modalRef.current &&
        !modalRef.current.contains(e.target)
      ) {
        // 모달이 열려 있고, 모달 영역 외의 클릭이 발생한 경우 모달을 닫습니다.
        closeModal();
      }
    };

    // document에 클릭 이벤트 리스너를 추가합니다.
    document.addEventListener("mousedown", handleOutsideClick);

    return () => {
      document.removeEventListener("mousedown", handleOutsideClick);
    };
  }, [isModalOpened]);

  const modalContent = isModalOpened ? (
    <ChallengeModal
      isOpen={isModalOpened}
      onClose={closeModal}
      modalRef={modalRef}
    />
  ) : null;

  return (
    <div className="relative">
      {/* 부모 컨테이너에 relative 적용 */}
      <img
        className="mb-20 cursor-pointer"
        src={challenge}
        alt="challenge_flower"
        onClick={() => setIsModalOpened(true)}
      />

      {/* 모달을 최상단에 두기 위한 createPortal 사용 */}
      {createPortal(modalContent, document.body)}
    </div>
  );
}