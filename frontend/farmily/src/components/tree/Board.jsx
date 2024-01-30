// Board.js
import React, { useState } from "react";
import BoardModal from "./BoardModal";
import mainboard from "../../assets/images/mainboard.png";

export default function Board() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <div onClick={openModal}>
        <img
          className="relative mb-16 mr-28"
          src={mainboard}
          alt="main_board"
        />
      </div>

      <BoardModal isOpen={isModalOpen} closeModal={closeModal} />
    </>
  );
}
