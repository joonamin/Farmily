// Board.js
import React, { useState } from 'react';
import BoardModal from './BoardModal';
import mainboard from '../../assets/images/mainboard.png';

export default function Board({ title, handleChange }) {
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
          className="relative mb-24 mr-24 ml-12 "
          src={mainboard}
          alt="main_board"
        />
        <p className="text-white text-4xl absolute top-20 left-12 pl-12">{title}</p>
      </div>

      <BoardModal isOpen={isModalOpen} closeModal={closeModal} handleChange={handleChange} />
    </>
  );
}
