// Board.js
import React, { useState } from 'react';
import BoardModal from './BoardModal';
import mainboard from '../../assets/images/mainboard.png';

export default function Board({ title }) {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <div onClick={openModal} className="relative hover:cursor-pointer">
        <img
          className="relative mb-16 mr-28 "
          src={mainboard}
          alt="main_board"
        />
        <p className="text-white text-4xl absolute top-20 left-12">{title}</p>
      </div>

      <BoardModal isOpen={isModalOpen} closeModal={closeModal} />
    </>
  );
}
